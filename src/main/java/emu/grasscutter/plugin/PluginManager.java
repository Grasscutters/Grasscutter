package emu.grasscutter.plugin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.Utils;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the server's plugins & the event system.
 */
public final class PluginManager {
    private final Map<String, Plugin> plugins = new HashMap<>();
    
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
                .collect(Collectors.toList());
        
        plugins.forEach(plugin -> {
            try {
                URL url = plugin.toURI().toURL();
                try (URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
                    URL configFile = loader.findResource("plugin.json");
                    InputStreamReader fileReader = new InputStreamReader(configFile.openStream());
                    
                    PluginConfig pluginConfig = Grasscutter.getGsonFactory().fromJson(fileReader, PluginConfig.class);
                    if(!pluginConfig.validate()) {
                        Utils.logObject(pluginConfig);
                        Grasscutter.getLogger().warn("Plugin " + plugin.getName() + " has an invalid config file.");
                        return;
                    }
                    
                    Class<?> pluginClass = loader.loadClass(pluginConfig.mainClass);
                    Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor(PluginIdentifier.class)
                            .newInstance(PluginIdentifier.fromPluginConfig(pluginConfig));
                    this.loadPlugin(pluginInstance);
                    
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
    private void loadPlugin(Plugin plugin) {
        Grasscutter.getLogger().info("Loading plugin: " + plugin.getName());
        
        // Add the plugin to the list of loaded plugins.
        this.plugins.put(plugin.getName(), plugin);
        // Call the plugin's onLoad method.
        plugin.onLoad();
    }

    /**
     * Enables all registered plugins.
     */
    public void enablePlugins() {
        
    }
    
    /**
     * Disables all registered plugins.
     */
    public void disablePlugins() {
        
    }
}