package emu.grasscutter.game.home;

import lombok.Getter;

@Getter
public enum SpecialFurnitureType {
    NOT_SPECIAL(-1),
    FarmField(2),
    TeleportPoint(3),
    NPC(5),
    Apartment(6),
    FurnitureSuite(7),
    Paimon(8);

    private final int value;

    SpecialFurnitureType(int value) {
        this.value = value;
    }
}
