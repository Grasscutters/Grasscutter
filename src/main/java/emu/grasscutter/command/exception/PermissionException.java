package emu.grasscutter.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionException extends RuntimeException {
    private final String requiredPermission;
}
