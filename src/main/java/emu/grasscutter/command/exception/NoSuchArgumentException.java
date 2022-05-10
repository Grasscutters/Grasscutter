package emu.grasscutter.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static emu.grasscutter.utils.Language.translate;

@AllArgsConstructor
public class NoSuchArgumentException extends RuntimeException {
    @Getter
    private final String name;
    @Getter
    private final Class<?> type;

    @Override
    public String getMessage() {
        return translate("command.generic.required_parameter_missing", name);
    }
}
