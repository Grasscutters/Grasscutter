package emu.grasscutter.command.parser.tree;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;

@Data
@Builder
class Node {
    private final HashMap<String, Node> leaves = new HashMap<>();
    private final Object commandInstance;
    private final Method commandHandler;
    private final String permission;

    void addChild(@NotNull Node child, String name) {
        if (leaves.containsKey(name)) {
            throw new RuntimeException("Duplicated command prefix.");
        }
        leaves.put(name, child);
    }

    void addChild(@NotNull Node child, String[] names) {
        for (String name: names) {
            addChild(child, name);
        }
    }
}
