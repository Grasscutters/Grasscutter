package emu.grasscutter.utils.text;

import com.github.davidmoten.guavamini.Lists;
import com.mojang.brigadier.Message;
import emu.grasscutter.utils.AtomicString;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Text extends Message {
    List<UnityTextFormatting> getFormatting();

    String content();

    List<Text> getSiblings();

    @Override
    default String getString() {
        var builder = new StringBuilder();
        this.visitOnlyContent(builder::append);
        return builder.toString();
    }

    default String getFormattedString() {
        var builder = new StringBuilder();
        this.visitFormatting((formatting, s) -> {
            if (s.isEmpty()) {
                return;
            }

            var formatter = new AtomicString("%s");
            for (var tag : formatting) {
                tag.appendTagTo(formatter);
            }
            builder.append(formatter.formatted(s));
        }, List.of());
        return builder.toString();
    }

    default void visitOnlyContent(Consumer<String> consumer) {
        consumer.accept(this.content());
        this.getSiblings().forEach(text -> text.visitOnlyContent(consumer));
    }

    default void visitFormatting(BiConsumer<List<UnityTextFormatting>, String> biConsumer, List<UnityTextFormatting> formatting) {
        var additionalFormatting = Lists.newArrayList(formatting);
        additionalFormatting.addAll(this.getFormatting());
        biConsumer.accept(additionalFormatting, this.content());
        this.getSiblings().forEach(text -> text.visitFormatting(biConsumer, additionalFormatting));
    }

    static MutableText empty() {
        return new MutableText("");
    }

    static MutableText literal(String msg) {
        return new MutableText(msg);
    }

    static TranslatableContent translatable(String key, Object... args) {
        return new TranslatableContent(key, args);
    }
}
