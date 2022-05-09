package emu.grasscutter.command.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NoSuchArgumentException extends RuntimeException {
    @Getter
    private final String name;
    @Getter
    private final Class<?> type;
}
