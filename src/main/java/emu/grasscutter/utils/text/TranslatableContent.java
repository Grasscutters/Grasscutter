package emu.grasscutter.utils.text;

import com.mojang.brigadier.Message;
import emu.grasscutter.command.source.CommandSource;
import emu.grasscutter.utils.lang.Language;

public record TranslatableContent(String key, Object... args) implements Message {
    public MutableText translate(CommandSource source) {
        return Text.literal(Language.translate(source.player(), this.key, this.args));
    }

    @Override
    @Deprecated
    public String getString() {
        return Language.translate(this.key, this.args);
    }
}
