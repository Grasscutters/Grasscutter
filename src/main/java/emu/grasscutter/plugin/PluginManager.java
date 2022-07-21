package emu.grasscutter.plugin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.event.*;
import emu.grasscutter.utils.Utils;
import lombok.*;

import javax.annotation.Nullable;

import static emu.grasscutter.config.Configuration.PLUGIN;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.util.jar.*;

/**
 * Manages the server's plugins and the event system.
 */
public final class PluginManager {
    /* All loaded plugins. */
    private final Map<String, Plugin> plugins = new LinkedHashMap<>();
    /* All currently registered listeners per plugin. */
    private final Map<Plugin, List<EventHandler<? extends Event>>> listeners = new LinkedHashMap<>();

    public PluginManager() {
        this.loadPlugins(); // Load all plugins from the plugins directory.
    }

    /* Data about an unloaded plugin. */
    @AllArgsConstructor @Getter
    static class PluginData {
        private Plugin plugin;
        private PluginIdentifier identifier;
        private URLClassLoader classLoader;
        private String[] dependencies;
    }

    /**
     * Loads plugins from the config-specified directory.
     */
    private void loadPlugins() {
        File pluginsDir = new File(Utils.toFilePath(PLUGIN()));
        if (!pluginsDir.exists() && !pluginsDir.mkdirs()) {
            Grasscutter.getLogger().error("Failed to create plugins directory: " + pluginsDir.getAbsolutePath());
            return;
        }

        File[] files = pluginsDir.listFiles();
        if (files == null) {
            // The directory is empty, there aren't any plugins to load.
            return;
        }

        List<File> plugins = Arrays.stream(files)
            .filter(file -> file.getName().endsWith(".jar"))
            .toList();

        URL[] pluginNames = new URL[plugins.size()];
        plugins.forEach(plugin -> {
            try {
                pluginNames[plugins.indexOf(plugin)] = plugin.toURI().toURL();
            } catch (MalformedURLException exception) {
                Grasscutter.getLogger().warn("Unable to load plugin.", exception);
            }
        });

        // Create a class loader for the plugins.
        URLClassLoader classLoader = new URLClassLoader(pluginNames);
        // Create a list of plugins that require dependencies.
        List<PluginData> dependencies = new ArrayList<>();

        // Initialize all plugins.
        for (var plugin : plugins) {
            try {
                URL url = plugin.toURI().toURL();
                try (URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
                    // Find the plugin.json file for each plugin.
                    URL configFile = loader.findResource("plugin.json");
                    // Open the config file for reading.
                    InputStreamReader fileReader = new InputStreamReader(configFile.openStream());

                    // Create a plugin config instance from the config file.
                    PluginConfig pluginConfig = Grasscutter.getGsonFactory().fromJson(fileReader, PluginConfig.class);
                    // Check if the plugin config is valid.
                    if (!pluginConfig.validate()) {
                        Utils.logObject(pluginConfig);
                        Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " has an invalid config file.");
                        return;
                    }

                    // Create a JAR file instance from the plugin's URL.
                    JarFile jarFile = new JarFile(plugin);
                    // Load all class files from the JAR file.
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.isDirectory() || !entry.getName().endsWith(".class") || entry.getName().contains("module-info"))
                            continue;
                        String className = entry.getName().replace(".class", "").replace("/", ".");
                        classLoader.loadClass(className); // Use the same class loader for ALL plugins.
                    }

                    // Create a plugin instance.
                    Class<?> pluginClass = classLoader.loadClass(pluginConfig.mainClass);
                    Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                    // Close the file reader.
                    fileReader.close();

                    // Check if the plugin has alternate dependencies.
                    if (pluginConfig.loadAfter != null && pluginConfig.loadAfter.length > 0) {
                        // Add the plugin to a "load later" list.
                        dependencies.add(new PluginData(
                            pluginInstance, PluginIdentifier.fromPluginConfig(pluginConfig),
                            loader, pluginConfig.loadAfter));
                        continue;
                    }

                    // Load the plugin.
                    this.loadPlugin(pluginInstance, PluginIdentifier.fromPluginConfig(pluginConfig), loader);
                } catch (ClassNotFoundException ignored) {
                    Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " has an invalid main class.");
                } catch (FileNotFoundException ignored) {
                    Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " lacks a valid config file.");
                }
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to load plugin: " + plugin.getName(), exception);
            }
        }

        // Load plugins with dependencies.
        int depth = 0; final int maxDepth = 30;
        while (!dependencies.isEmpty()) {
            // Check if the depth is too high.
            if (depth >= maxDepth) {
                Grasscutter.getLogger().error("Failed to load plugins with dependencies.");
                break;
            }

            try {
                // Get the next plugin to load.
                var pluginData = dependencies.get(0);

                // Check if the plugin's dependencies are loaded.
                if (!this.plugins.keySet().containsAll(List.of(pluginData.getDependencies()))) {
                    depth++; // Increase depth counter.
                    continue; // Continue to next plugin.
                }

                // Remove the plugin from the list of dependencies.
                dependencies.remove(pluginData);

                // Load the plugin.
                this.loadPlugin(pluginData.getPlugin(), pluginData.getIdentifier(), pluginData.getClassLoader());
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to load a plugin.", exception); depth++;
            }
        }
    }

    /**
     * Load the specified plugin.
     *
     * @param plugin The plugin instance.
     */
    private void loadPlugin(Plugin plugin, PluginIdentifier identifier, URLClassLoader classLoader) {
        Grasscutter.getLogger().info("Loading plugin: " + identifier.name);

        // Add the plugin's identifier.
        try {
            Class<Plugin> pluginClass = Plugin.class;
            Method method = pluginClass.getDeclaredMethod("initializePlugin", PluginIdentifier.class, URLClassLoader.class);
            method.setAccessible(true);
            method.invoke(plugin, identifier, classLoader);
            method.setAccessible(false);
        } catch (Exception ignored) {
            Grasscutter.getLogger().warn("Failed to add plugin identifier: " + identifier.name);
        }

        // Add the plugin to the list of loaded plugins.
        this.plugins.put(identifier.name, plugin);
        // Create a collection for the plugin's listeners.
        this.listeners.put(plugin, new LinkedList<>());

        // Call the plugin's onLoad method.
        try {
            plugin.onLoad();
        } catch (Throwable exception) {
            Grasscutter.getLogger().error("Failed to load plugin: " + identifier.name, exception);
        }
    }

    /**
     * Enables all registered plugins.
     */
    public void enablePlugins() {
        this.plugins.forEach((name, plugin) -> {
            Grasscutter.getLogger().info("Enabling plugin: " + name);
            try {
                plugin.onEnable();
            } catch (Throwable exception) {
                Grasscutter.getLogger().error("Failed to enable plugin: " + name, exception);
            }
        });
    }

    /**
     * Disables all registered plugins.
     */
    public void disablePlugins() {
        this.plugins.forEach((name, plugin) -> {
            Grasscutter.getLogger().info("Disabling plugin: " + name);
            try {
                plugin.onDisable();
            } catch (Throwable exception) {
                Grasscutter.getLogger().error("Failed to disable plugin: " + name, exception);
            }
        });
    }

    /**
     * Registers a plugin's event listener.
     *
     * @param plugin The plugin registering the listener.
     * @param listener The event listener.
     */
    public void registerListener(Plugin plugin, EventHandler<? extends Event> listener) {
        this.listeners.get(plugin).add(listener);
    }

    /**
     * Invoke the provided event on all registered event listeners.
     *
     * @param event The event to invoke.
     */
    public void invokeEvent(Event event) {
        EnumSet.allOf(HandlerPriority.class)
            .forEach(priority -> this.checkAndFilter(event, priority));
    }

    /**
     * Check an event to handlers for the priority.
     *
     * @param event    The event being called.
     * @param priority The priority to call for.
     */
    private void checkAndFilter(Event event, HandlerPriority priority) {
        // Create a collection of listeners.
        List<EventHandler<? extends Event>> listeners = new LinkedList<>();

        // Add all listeners from every plugin.
        this.listeners.values().forEach(listeners::addAll);

        listeners.stream()
            // Filter the listeners by priority.
            .filter(handler -> handler.handles().isInstance(event))
            .filter(handler -> handler.getPriority() == priority)
            // Invoke the event.
            .toList().forEach(handler -> this.invokeHandler(event, handler));
    }

    /**
     * Gets a plugin's instance by its name.
     *
     * @param name The name of the plugin.
     * @return Either null, or the plugin's instance.
     */
    @Nullable
    public Plugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    /**
     * Enables a plugin.
     *
     * @param plugin The plugin to enable.
     */
    public void enablePlugin(Plugin plugin) {
        try {
            // Call the plugin's onEnable method.
            plugin.onEnable();
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to enable plugin: " + plugin.getName(), exception);
        }
    }

    /**
     * Disables a plugin.
     *
     * @param plugin The plugin to disable.
     */
    public void disablePlugin(Plugin plugin) {
        try {
            // Call the plugin's onDisable method.
            plugin.onDisable();
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to disable plugin: " + plugin.getName(), exception);
        }

        // Un-register all listeners.
        this.listeners.remove(plugin);
    }

    /**
     * Performs logic checks then invokes the provided event handler.
     *
     * @param event   The event passed through to the handler.
     * @param handler The handler to invoke.
     */
    @SuppressWarnings("unchecked")
    private <T extends Event> void invokeHandler(Event event, EventHandler<T> handler) {
        if (!event.isCanceled() ||
            (event.isCanceled() && handler.ignoresCanceled())
        ) handler.getCallback().consume((T) event);
    }
}
