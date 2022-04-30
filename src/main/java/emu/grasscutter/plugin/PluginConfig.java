package emu.grasscutter.plugin;

/**
 * The data contained in the plugin's `plugin.json` file.
 */
public final class PluginConfig {
    public String name, description, version;
    public String mainClass;
    public String[] authors;

    /**
     * Attempts to validate this config instance.
     * @return True if the config is valid, false otherwise.
     */
    public boolean validate() {
        return name != null && description != null && mainClass != null;
    }
}
