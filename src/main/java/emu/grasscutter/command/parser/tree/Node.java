package emu.grasscutter.command.parser.tree;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;

@Data
class Node {
    private final Object commandInstance;
    private final HashMap<String, Node> leaves = new HashMap<>();
    private final Method commandHandler;

}
