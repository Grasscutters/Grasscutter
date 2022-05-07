package emu.grasscutter.command.parser.helper;

import java.util.HashMap;

public class Automator {
    private AutomatorState state = AutomatorState.BEGIN;

    private void beginState(Character character, StringBuilder sb) throws ParseResult {
        if (character == null) {
            throw new ParseResult(null);
        } else if (Character.isSpaceChar(character)) {
            // skip leading spaces
        } else if (character.equals('"')) {
            state = AutomatorState.QUOTED;
        } else {
            state = AutomatorState.APPEND;
            sb.append(character);
        }
    }
}
