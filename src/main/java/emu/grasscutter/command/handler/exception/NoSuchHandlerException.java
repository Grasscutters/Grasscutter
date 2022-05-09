package emu.grasscutter.command.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NoSuchHandlerException extends RuntimeException {
    @Getter
    private final String key;
}
