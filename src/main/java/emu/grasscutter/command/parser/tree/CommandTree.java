package emu.grasscutter.command.parser.tree;

import emu.grasscutter.command.parser.ParseUtil;
import emu.grasscutter.command.parser.PermissionUtil;
import emu.grasscutter.command.parser.annotation.*;
import emu.grasscutter.command.source.BaseCommandSource;
import lombok.SneakyThrows;

import static org.reflections.ReflectionUtils.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.regex.Pattern;

public final class CommandTree {
    private final Node root = Node.builder().build();

    @SneakyThrows
    public void addCommand(Class<?> commandClass) {
        Command metadata = commandClass.getAnnotation(Command.class);
        if (metadata == null) {
            throw new RuntimeException("%s must be annotated with @Command.".formatted(commandClass.getSimpleName()));
        }
        // get the default handler
        Set<Method> defaultHandlers = get(Methods.of(commandClass)
                .filter(withAnnotation(DefaultHandler.class)));
        // only one is permitted (if exists)
        if (defaultHandlers.size() > 1) {
            throw new RuntimeException("Cannot register %s because there is more than one DefaultHandler.");
        }
        Method defaultHandler = defaultHandlers.isEmpty() ? null : defaultHandlers.iterator().next();
        Object instance = commandClass.getDeclaredConstructor().newInstance(); // may throw if constructor inaccessible
        validateSignature(defaultHandler);

        Node commandNode = Node.builder()
                .commandInstance(instance)
                .commandHandler(defaultHandler)
                .permission(getMethodPermission(defaultHandler)) // may throw if permission syntax invalid
                .allowedOrigin(getMethodAllowedOrigin(defaultHandler))
                .descriptionKey(getMethodDescriptionKey(defaultHandler)) // may throw if key syntax invalid
                .parameters(defaultHandler != null ? defaultHandler.getParameters() : null)
                .optionalParameters(defaultHandler != null ? getOptionalParameters(defaultHandler) : null)
                .build();
        // process subcommands
        Set<Method> subHandlers = get(Methods.of(commandClass).filter(withAnnotation(SubCommandHandler.class)));
        for (Method subHandler: subHandlers) {
            validateSignature(subHandler);
            String name = subHandler.getAnnotation(SubCommandHandler.class).value();
            Node subNode = Node.builder()
                    .commandHandler(subHandler)
                    .commandInstance(instance)
                    .permission(getMethodPermission(subHandler))
                    .allowedOrigin(getMethodAllowedOrigin(subHandler))
                    .descriptionKey(getMethodDescriptionKey(subHandler))
                    .parameters(subHandler.getParameters())
                    .optionalParameters(getOptionalParameters(subHandler))
                    .build();
            commandNode.addChild(subNode, name);
        }
        // append to root if no error
        root.addChild(commandNode, metadata.literal());
        root.addChild(commandNode, metadata.aliases());
    }

    private void validateSignature(Method method) {
        if (method == null) return;
        if ((method.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) != Modifier.PUBLIC) {
            throw new RuntimeException("%s must be public and non-static.".formatted(method.getName()));
        }
        if (!method.getReturnType().equals(void.class)) {
            throw new RuntimeException("%s should NOT return.".formatted(method.getName()));
        }
        Parameter[] parameters = method.getParameters();
        boolean invalid = parameters.length < 1;
        for (int i = 0; i < parameters.length; ++i) {
            // except the first parameter, others cannot be CommandSource
            invalid |= (BaseCommandSource.class.isAssignableFrom(parameters[i].getType()) ^ (i == 0));
        }
        if (invalid) {
            throw new RuntimeException("Signature of %s must be \"void (BaseCommandSource, ...)\".");
        }
    }

    private Parameter[] getOptionalParameters(Method method) {
        return Arrays.stream(
                method.getParameters()
        ).filter(
                p -> p.isAnnotationPresent(OptionalArgument.class)
                        && !BaseCommandSource.class.isAssignableFrom(p.getType()) // source cannot be optional
        ).toList().toArray(new Parameter[] {});
    }

    private String getMethodPermission(Method method) {
        if (method == null) {
            return null;
        }
        Permission permission = method.getAnnotation(Permission.class);
        return permission != null ? PermissionUtil.ensurePermissionValid(permission.value()) : "";
    }

    private Origin getMethodAllowedOrigin(Method method) {
        if (method == null) {
            return null;
        }
        AllowedOrigin allowedOrigin = method.getAnnotation(AllowedOrigin.class);
        return allowedOrigin != null ? allowedOrigin.value() : Origin.ALL;
    }

    private static final Pattern JsonReferencePattern = Pattern.compile("^\\w+(?:[/.]\\w+)+$");
    private String getMethodDescriptionKey(Method method) {
        if (method == null) {
            return null;
        }
        Description description = method.getAnnotation(Description.class);
        String key = description != null ? description.value() : null;
        if (key == null) {
            return null;
        }
        if (JsonReferencePattern.matcher(key).matches()) {
            return key;
        }
        throw new RuntimeException("The description key is not a valid json path.");
    }

    public void run(BaseCommandSource source, String command) {
        root.runCommand(ParseUtil.spiltCommand(command), source);
    }
}
