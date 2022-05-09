package emu.grasscutter.command.parser.exception;

import emu.grasscutter.command.parser.annotation.Origin;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OriginException extends RuntimeException {
    private final Origin allowedOrigin;
}
