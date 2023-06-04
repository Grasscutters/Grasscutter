package emu.grasscutter.utils.objects;

import lombok.Getter;

@Getter
public final class DropType {
    private final Object raw;
    private final int value;

    public DropType(int value) {
        this.raw = value;
        this.value = value;
    }

    public DropType(String value) {
        this.raw = value;
        this.value =
                switch (value) {
                    default -> Integer.parseInt(value);
                    case "ForceDrop" -> 2;
                };
    }

    /**
     * @return Whether the drop type value is a string.
     */
    public boolean isString() {
        return this.raw instanceof String;
    }

    /**
     * @return The drop type value as a string.
     */
    public String getAsString() {
        if (this.raw instanceof String) return (String) this.raw;
        throw new UnsupportedOperationException();
    }

    /**
     * @return The drop type value as an integer.
     */
    public int getAsInt() {
        if (this.raw instanceof Integer) return (int) this.raw;
        throw new UnsupportedOperationException();
    }
}
