package emu.grasscutter.utils.text;

import emu.grasscutter.utils.AtomicString;

import java.util.function.Consumer;

public enum UnityTextFormatting {
    RESET(s -> s.set("%s")),
    WHITE(s -> s.update(s1 -> s1.formatted("<color=#FFFFFF>%s</color>"))),
    YELLOW(s -> s.update(s1 -> s1.formatted("<color=#FFFF00>%s</color>"))),
    RED(s -> s.update(s1 -> s1.formatted("<color=#FF5555>%s</color>"))),
    GRAY(s -> s.update(s1 -> s1.formatted("<color=#AAAAAA>%s</color>"))),
    BOLD(s -> s.update(s1 -> s1.formatted("<b>%s</b>"))),
    ITALIC(s -> s.update(s1 -> s1.formatted("<i>%s</i>")));

    private final Consumer<AtomicString> formattingUpdater;

    UnityTextFormatting(Consumer<AtomicString> formattingUpdater) {
        this.formattingUpdater = formattingUpdater;
    }

    public void appendTagTo(AtomicString atomicString) {
        this.formattingUpdater.accept(atomicString);
    }
}
