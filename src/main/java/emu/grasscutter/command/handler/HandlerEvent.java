package emu.grasscutter.command.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class HandlerEvent {
    @Getter
    private int collectionCode;

    @Getter
    private int methodCode;

    @Getter
    @Setter
    private HandlerContext context;
}
