package emu.grasscutter.plugin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.api.ServerHook;
import emu.grasscutter.server.game.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static emu.grasscutter.config.Configuration.*;

import java.io.File;
import java.io.InputStream;
import java.net.URLClassLoader;

/**
 * The base class for all plugins to extend.
 */
public abstract class Plugin {
    private final ServerHook server = ServerHook.getInstance();

    private PluginIdentifier identifier;
    private URLClassLoader classLoader;
    private File dataFolder;
    private Logger logger;

    /**
     * This method is reflected into.
     *
     * Set plugin variables.
     * @param identifier The plugin's identifier.
     */
    private void initializePlugin(PluginIdentifier identifier, URLClassLoader classLoader) {
        if (this.identifier != null) {
            Grasscutter.getLogger().warn(this.identifier.name + " had a reinitialization attempt.");
            return;
        }

        this.identifier = identifier;
        this.classLoader = classLoader;
        this.dataFolder = new File(PLUGIN(), identifier.name);
        this.logger = LoggerFactory.getLogger(identifier.name);

        if (!this.dataFolder.exists() && !this.dataFolder.mkdirs()) {
            Grasscutter.getLogger().warn("Failed to create plugin data folder for " + this.identifier.name);
            return;
        }
    }

    /**
     * The plugin's identifier instance.
     * @return An instance of {@link PluginIdentifier}.
     */
    public final PluginIdentifier getIdentifier() {
        return this.identifier;
    }

    /**
     * Get the plugin's name.
     */
    public final String getName() {
        return this.identifier.name;
    }

    /**
     * Get the plugin's description.
     */
    public final String getDescription() {
        return this.identifier.description;
    }

    /**
     * Get the plugin's version.
     */
    public final String getVersion() {
        return this.identifier.version;
    }

    /**
     * Returns the server that initialized the plugin.
     * @return A server instance.
     */
    public final GameServer getServer() {
        return this.server.getGameServer();
    }

    /**
     * Returns an input stream for a resource in the JAR file.
     * @param resourceName The name of the resource.
     * @return An input stream.
     */
    public final InputStream getResource(String resourceName) {
        return this.classLoader.getResourceAsStream(resourceName);
    }

    /**
     * Returns a directory where plugins can store data files.
     * @return A directory on the file system.
     */
    public final File getDataFolder() {
        return this.dataFolder;
    }

    /**
     * Returns the server hook.
     * @return A server hook singleton.
     */
    public final ServerHook getHandle() {
        return this.server;
    }

    /**
     * Returns the plugin's logger.
     * @return A SLF4J logger.
     */
    public final Logger getLogger() {
        return this.logger;
    }

    /* Called when the plugin is first loaded. */
    public void onLoad() { }
    /* Called after (most of) the server enables. */
    public void onEnable() { }
    /* Called before the server disables. */
    public void onDisable() { }
}
