package emu.grasscutter.game.quest.enums;

public enum QuestType {
    AQ(0),
    FQ(1),
    LQ(2),
    EQ(3),
    DQ(4),
    IQ(5),
    VQ(6),
    WQ(7);

    private final int value;

    QuestType(int id) {
        this.value = id;
    }

    public int getValue() {
        return this.value;
    }
}
