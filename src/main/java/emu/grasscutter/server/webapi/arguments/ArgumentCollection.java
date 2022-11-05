package emu.grasscutter.server.webapi.arguments;

import java.util.HashMap;

public class ArgumentCollection extends HashMap<String, ArgumentValue> {
    @Override
    public ArgumentValue get(Object key) {
        if(!containsKey(key)) {
            return ArgumentValue.emptyValue;
        }

        return super.get(key);
    }
}
