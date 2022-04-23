package emu.grasscutter.plugin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.game.GameServer;

/**
 * The base class for all plugins to extend.
 */
public abstract class Plugin {
    private final PluginIdentifier identifier;

    /**
     * Empty constructor for developers.
     * Should not be called by users.
     */
    public Plugin() {
        this(new PluginIdentifier("", "", "", new String[]{}));
    }
    
    /**
     * Constructor for plugins.
     * @param identifier The plugin's identifier.
     */
    public Plugin(PluginIdentifier identifier) {
        this.identifier = identifier;
    }

    /**
     * The plugin's identifier instance.
     * @return An instance of {@link PluginIdentifier}.
     */
    public final PluginIdentifier getIdentifier(){
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
        return Grasscutter.getGameServer();
    }
    
    /* Called when the plugin is first loaded. */
    public void onLoad() { }
    /* Called after (most of) the server enables. */
    public void onEnable() { }
    /* Called before the server disables. */
    public void onDisable() { }
}
