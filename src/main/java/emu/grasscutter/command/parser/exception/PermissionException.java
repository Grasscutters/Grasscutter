package emu.grasscutter.command.parser.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionException extends RuntimeException {
    private final String requiredPermission;
}
