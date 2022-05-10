package emu.grasscutter.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidArgumentException extends RuntimeException {
    private final String raw;
    private final Class<?> expectedType;
}
