package emu.grasscutter.command.parser.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidArgumentException extends RuntimeException {
    private final String raw;
    private final Class<?> expectedType;
}
