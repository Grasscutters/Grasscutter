package emu.grasscutter.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import static emu.grasscutter.utils.Language.translate;

@AllArgsConstructor
public class NoSuchHandlerException extends RuntimeException {
    @Getter
    private final String key;

    @Override
    public String getMessage() {
        return translate("commands.generic.no_such_handler", key);
    }
}
