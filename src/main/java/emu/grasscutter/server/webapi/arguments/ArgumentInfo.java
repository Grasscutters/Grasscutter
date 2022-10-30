package emu.grasscutter.server.webapi.arguments;


public class ArgumentInfo
{
    private String argumentName;
    private String argumentType;
    private String description;
    private String defaultValue;

    public ArgumentInfo(String argumentName, String argumentType)
    {
        this.argumentName = argumentName;
        this.argumentType = argumentType;
    }

    public String getArgumentName()
    {
        return argumentName;
    }

    public String getArgumentType()
    {
        return argumentType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }
}
