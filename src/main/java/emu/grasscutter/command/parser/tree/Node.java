package emu.grasscutter.command.parser.tree;

import emu.grasscutter.command.exception.*;
import emu.grasscutter.command.parser.ParseUtil;
import emu.grasscutter.command.parser.PermissionUtil;
import emu.grasscutter.command.parser.annotation.OptionalArgument;
import emu.grasscutter.command.parser.annotation.Origin;
import emu.grasscutter.command.source.BaseCommandSource;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

@Getter
@Builder
class Node {
    private final HashMap<String, Node> children = new HashMap<>();
    private final Object commandInstance;
    private final Method commandHandler;
    private final String permission;
    private final String descriptionKey;
    private final Origin allowedOrigin;
    private final Parameter[] parameters;
    private final Parameter[] optionalParameters;
    void addChild(@NotNull Node child, String name) {
        if (children.containsKey(name)) {
            throw new RuntimeException("Duplicated command prefix.");
        }
        children.put(name, child);
    }
    void addChild(@NotNull Node child, String[] names) {
        for (String name: names) {
            addChild(child, name);
        }
    }

    @SneakyThrows
    void runCommand(Queue<String> command, BaseCommandSource source) {
        String next = command.peek();
        // try to go down
        Node son = children.get(next);
        if (son == null) { // reaches dead end
            // try to invoke the handler on this node
            if (commandHandler == null) {
                throw new NoSuchCommandException();
            }
            // origin check
            if (!allowedOrigin.allows(source.getOrigin())) {
                throw new OriginException(allowedOrigin);
            }
            // permission check
            if (!PermissionUtil.hasPermission(source.getPermissions(), permission)) {
                throw new PermissionException(permission);
            }
            // parse parameters
            int missedParameter = parameters.length - command.size() - 1;
            if (missedParameter < 0) {
                throw new TooManyArgumentsException();
            }
            if (missedParameter > optionalParameters.length) {
                throw new TooFewArgumentsException();
            }
            // int canIgnoreOptionalOnParameter = parameters.length - 1 - missedParameter;
            ArrayList<Object> args = new ArrayList<>();
            args.add(source); // first argument is always command source
            for (int i = 1; i < parameters.length; ++i) {
                Parameter p = parameters[i];
                if (p.isAnnotationPresent(OptionalArgument.class) && (missedParameter--) > 0) {
                    args.add(null);
                } else {
                    args.add(ParseUtil.parseNext(command, p.getType()));
                }
            }
            // invoke!
            try {
                commandHandler.invoke(commandInstance, args.toArray());
            } catch (InvocationTargetException e) {
                // unwrap the exception
                throw e.getTargetException();
            }
        } else { // go down
            command.poll();
            son.runCommand(command, source);
        }
    }
}
