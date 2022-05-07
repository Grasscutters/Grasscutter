package emu.grasscutter.command.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HandlerSuccess extends Throwable {
    @Getter
    private final Object result;
}
