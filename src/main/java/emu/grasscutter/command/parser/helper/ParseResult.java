package emu.grasscutter.command.parser.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ParseResult extends Throwable {
    @Getter
    private final String result;
}
