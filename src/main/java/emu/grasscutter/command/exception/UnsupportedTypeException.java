package emu.grasscutter.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnsupportedTypeException extends RuntimeException {
    private final Class<?> type;
}
