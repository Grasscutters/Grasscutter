package emu.grasscutter.utils;

import java.io.Serial;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class AtomicString extends AtomicReference<String> {
    @Serial
    private static final long serialVersionUID = -5822325962685291223L;

    public AtomicString(String init) {
        super(init);
    }

    public AtomicString() {
        super();
    }

    public void update(UnaryOperator<String> updater) {
        this.getAndUpdate(updater);
    }

    public String formatted(Object... args) {
        return this.get().formatted(args);
    }
}
