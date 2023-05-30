package emu.grasscutter.utils.objects;

public interface Returnable<T> {
    /**
     * @return The value.
     */
    T invoke();
}
