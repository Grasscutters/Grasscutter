package emu.grasscutter.utils.text;

import com.github.davidmoten.guavamini.Lists;

import java.util.List;

public class MutableText implements Text {
    private final String content;
    private final List<UnityTextFormatting> formatting = Lists.newArrayList();
    private final List<Text> siblings = Lists.newArrayList();

    public MutableText(String content) {
        this.content = content;
    }

    public MutableText append(Text text) {
        this.siblings.add(text);
        return this;
    }

    public MutableText append(String str) {
        return this.append(Text.literal(str));
    }

    public MutableText withFormatting(UnityTextFormatting... formatting) {
        this.formatting.addAll(List.of(formatting));
        return this;
    }

    @Override
    public List<UnityTextFormatting> getFormatting() {
        return this.formatting;
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public List<Text> getSiblings() {
        return this.siblings;
    }
}
