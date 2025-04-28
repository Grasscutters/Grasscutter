package emu.grasscutter.data.binout;

import lombok.Getter;

@Getter
public class AbilityEmbryoEntry {
    private String name;
    private String[] abilities;

    public AbilityEmbryoEntry() {}

    public AbilityEmbryoEntry(String avatarName, String[] array) {
        this.name = avatarName;
        this.abilities = array;
    }
}
