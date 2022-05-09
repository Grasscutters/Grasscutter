package emu.grasscutter.command.parser.annotation;

public enum Origin {
    ALL(3),
    SERVER(1),
    CLIENT(2);

    Origin(int v) {
        value = v;
    }
    private final int value;
    public boolean allows(Origin origin) {
        return (value & origin.value) != 0;
    }
}
