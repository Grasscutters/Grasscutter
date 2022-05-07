package emu.grasscutter.command.parser.tree;

import emu.grasscutter.command.parser.PermissionUtil;
import emu.grasscutter.command.parser.annotation.Command;
import emu.grasscutter.command.parser.annotation.DefaultHandler;
import emu.grasscutter.command.parser.annotation.Permission;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public final class CommandTree {
    private final Node root = new Node(null, null, "");

    @SneakyThrows
    public void addCommand(Class<?> commandClass) {
        Reflections commandRef = new Reflections(commandClass);
        Command metadata = commandClass.getAnnotation(Command.class);
        // get the default handler
        Set<Method> defaultHandlers = commandRef.getMethodsAnnotatedWith(DefaultHandler.class);
        // only one is permitted (if exists)
        if (defaultHandlers.size() > 1) {
            throw new RuntimeException("Cannot register %s because there is more than one DefaultHandler.");
        }
        Method defaultHandler = defaultHandlers.isEmpty() ? null : defaultHandlers.iterator().next();
        Object instance = commandClass.getDeclaredConstructor().newInstance(); // may throw if constructor inaccessible
        Node commandNode = Node.builder()
                .commandInstance(instance)
                .commandHandler(defaultHandler)
                .permission(getMethodPermission(defaultHandler)) // may throw if permission syntax invalid
                .build();
        // append to root node
        root.addChild(commandNode, metadata.literal());
        root.addChild(commandNode, metadata.aliases());
        //
    }

    private String getMethodPermission(Method method) {
        if (method == null) {
            return "";
        }
        Permission permission = method.getAnnotation(Permission.class);
        return permission != null ? PermissionUtil.ensurePermissionValid(permission.value()) : "";
    }
}
