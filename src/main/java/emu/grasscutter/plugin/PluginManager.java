package emu.grasscutter.plugin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.event.Event;
import emu.grasscutter.server.event.EventHandler;
import emu.grasscutter.server.event.HandlerPriority;
import emu.grasscutter.utils.Utils;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Manages the server's plugins and the event system.
 */
public final class PluginManager {
    private final Map<String, Plugin> plugins = new HashMap<>();
    private final List<EventHandler<? extends Event>> listeners = new LinkedList<>();
    
    public PluginManager() {
        this.loadPlugins(); // Load all plugins from the plugins directory.
    }

    /**
     * Loads plugins from the config-specified directory.
     */
    private void loadPlugins() {
        String directory = Grasscutter.getConfig().PLUGINS_FOLDER;
        File pluginsDir = new File(Utils.toFilePath(directory));
        if(!pluginsDir.exists() && !pluginsDir.mkdirs()) {
            Grasscutter.getLogger().error("Failed to create plugins directory: " + pluginsDir.getAbsolutePath());
            return;
        }
        
        File[] files = pluginsDir.listFiles();
        if(files == null) {
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

        URLClassLoader classLoader = new URLClassLoader(pluginNames);

        plugins.forEach(plugin -> {
            try {
                URL url = plugin.toURI().toURL();
                try (URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
                    URL configFile = loader.findResource("plugin.json"); // Find the plugin.json file for each plugin.
                    InputStreamReader fileReader = new InputStreamReader(configFile.openStream());

                    PluginConfig pluginConfig = Grasscutter.getGsonFactory().fromJson(fileReader, PluginConfig.class);
                    if(!pluginConfig.validate()) {
                        Utils.logObject(pluginConfig);
                        Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " has an invalid config file.");
                        return;
                    }

                    JarFile jarFile = new JarFile(plugin);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while(entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if(entry.isDirectory() || !entry.getName().endsWith(".class") || entry.getName().contains("module-info")) continue;
                        String className = entry.getName().replace(".class", "").replace("/", ".");
                        classLoader.loadClass(className); // Use the same class loader for ALL plugins.
                    }
                    
                    Class<?> pluginClass = classLoader.loadClass(pluginConfig.mainClass);
                    Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                    this.loadPlugin(pluginInstance, PluginIdentifier.fromPluginConfig(pluginConfig), loader);
                    
                    fileReader.close(); // Close the file reader.
                } catch (ClassNotFoundException ignored) {
                    Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " has an invalid main class.");
                }
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to load plugin: " + plugin.getName(), exception);
            }
        });
    }

    /**
     * Load the specified plugin.
     * @param plugin The plugin instance.
     */
    private void loadPlugin(Plugin plugin, PluginIdentifier identifier, URLClassLoader classLoader) {
        Grasscutter.getLogger().info("Loading plugin: " + identifier.name);
        
        // Add the plugin's identifier.
        try {
            Class<Plugin> pluginClass = Plugin.class;
            Method method = pluginClass.getDeclaredMethod("initializePlugin", PluginIdentifier.class, URLClassLoader.class);
            method.setAccessible(true); method.invoke(plugin, identifier, classLoader); method.setAccessible(false);
        } catch (Exception ignored) {
            Grasscutter.getLogger().warn("Failed to add plugin identifier: " + identifier.name);
        }
        
        // Add the plugin to the list of loaded plugins.
        this.plugins.put(identifier.name, plugin);
        // Call the plugin's onLoad method.
        plugin.onLoad();
    }

    /**
     * Enables all registered plugins.
     */
    public void enablePlugins() {
        this.plugins.forEach((name, plugin) -> {
            Grasscutter.getLogger().info("Enabling plugin: " + name);
            plugin.onEnable();
        });
    }
    
    /**
     * Disables all registered plugins.
     */
    public void disablePlugins() {
        this.plugins.forEach((name, plugin) -> {
            Grasscutter.getLogger().info("Disabling plugin: " + name);
            plugin.onDisable();
        });
    }

    /**
     * Registers a plugin's event listener.
     * @param listener The event listener.
     */
    public void registerListener(EventHandler<? extends Event> listener) {
        this.listeners.add(listener);
    }
    
    /**
     * Invoke the provided event on all registered event listeners.
     * @param event The event to invoke.
     */
    public void invokeEvent(Event event) {
        EnumSet.allOf(HandlerPriority.class)
                .forEach(priority -> this.checkAndFilter(event, priority));
    }

    /**
     * Check an event to handlers for the priority.
     * @param event The event being called.
     * @param priority The priority to call for.
     */
    private void checkAndFilter(Event event, HandlerPriority priority) {
        this.listeners.stream()
                .filter(handler -> handler.handles().isInstance(event))
                .filter(handler -> handler.getPriority() == priority)
                .toList().forEach(handler -> this.invokeHandler(event, handler));
    }

    public Plugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    /**
     * Performs logic checks then invokes the provided event handler.
     * @param event The event passed through to the handler.
     * @param handler The handler to invoke.
     */
    @SuppressWarnings("unchecked")
    private <T extends Event> void invokeHandler(Event event, EventHandler<T> handler) {
        if(!event.isCanceled() ||
                (event.isCanceled() && handler.ignoresCanceled())
        ) handler.getCallback().consume((T) event);
    }
}
