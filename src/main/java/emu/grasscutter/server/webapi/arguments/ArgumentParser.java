package emu.grasscutter.server.webapi.arguments;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ArgumentParser {
    HashMap<String, ArgumentInfo> arguments = new HashMap<>();
    ArgumentCollection parsedArguments;

    public ArgumentParser(ArgumentInfo... argumentInfoArray) {
        for(var argInfo : argumentInfoArray) {
            addArgument(argInfo);
        }
    }

    public void addArgument(ArgumentInfo argumentInfo) {
        if(argumentInfo == null) {
            return;
        }
        boolean invalidArg = argumentInfo.getArgumentName() == null || argumentInfo.getArgumentType() == null;
        boolean hasKey = arguments.containsKey(argumentInfo.getArgumentName());
        if(invalidArg || hasKey) {
            return;
        }

        arguments.put(argumentInfo.getArgumentName(), argumentInfo);
    }

    public String getHelp()
    {
        if(arguments.size() == 0) {
            return "This command need no argument";
        }
        StringBuilder builder = new StringBuilder();
        for (var argInfo : arguments.entrySet()) {
            builder.append(argInfo.getKey());
            builder.append(": ");
            builder.append(argInfo.getValue().getArgumentType());
            builder.append("  ");
            builder.append(argInfo.getValue().getDescription());
            if(argInfo.getValue().getDefaultValue() == null) {
                continue;
            }
            builder.append("Default: ");
            builder.append(argInfo.getValue().getDefaultValue());
        }

        return builder.toString();
    }

    public JsonObject getJsonHelp() {
        if(arguments.size() == 0) {
            JsonObject tip = new JsonObject();
            tip.addProperty("tip", "This command need no argument");
            return tip;
        }

        JsonObject argsContainerJson = new JsonObject();
        JsonArray argsJson = new JsonArray();
        argsContainerJson.add("help", argsJson);
        for(var argInfo : arguments.entrySet()) {
            JsonObject argInfoJson = new JsonObject();
            argInfoJson.addProperty("name", argInfo.getKey());
            argInfoJson.addProperty("type", argInfo.getValue().getArgumentType());
            argInfoJson.addProperty("description", argInfo.getValue().getDescription());
            if(argInfo.getValue().getDefaultValue() != null) {
                argInfoJson.addProperty("defaultValue", argInfo.getValue().getDefaultValue());
            }
        }

        return argsContainerJson;
    }

    public ArgumentCollection parseCommandLine(String[] commandline) {
        clearParsedCache();
        ArgumentCollection table = new ArgumentCollection();
        for(var arg : commandline) {
            if(!arg.startsWith("-")) {
                continue;
            }

            String realArg = arg.substring(1);
            int eqIdx = realArg.indexOf('=');
            if(eqIdx == -1 && arguments.containsKey(realArg.trim())) {
                table.put(realArg, null);
                continue;
            }

            String[] split = new String[]{realArg.substring(0, eqIdx), realArg.substring(eqIdx)};
            table.put(split[0], new ArgumentValue(split[1], null));
        }

        return putDefaultValues(table);
    }

    @Nullable
    private ArgumentCollection putDefaultValues(ArgumentCollection argumentCollection) {
        for(var item : arguments.values()) {
            if(!argumentCollection.containsKey(item.getArgumentName())) {
                if(item.getDefaultValue() == null) {
                    return null;
                }
                argumentCollection.put(item.getArgumentName(), new ArgumentValue(null, item.getDefaultValue()));
            }

        }

        return parsedArguments = argumentCollection;
    }

    public ArgumentCollection parseJson(JsonObject jsonObject) {
        clearParsedCache();
        ArgumentCollection argumentCollection = new ArgumentCollection();
        for(var arg : jsonObject.entrySet()) {
            if(arguments.containsKey(arg.getKey())) {
                argumentCollection.put(arg.getKey(), new ArgumentValue(arg.getValue(), null));
            }
        }

        return putDefaultValues(argumentCollection);
    }

    public void clearParsedCache() {
        parsedArguments = null;
    }


}
