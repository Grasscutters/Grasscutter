package emu.grasscutter.data;

import java.lang.annotation.*;
import java.util.List;
import java.util.stream.Stream;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceType {

    /** Names of the file that this Resource loads from */
    String[] name();

    /**
     * Load priority - dictates which order to load this resource, with "highest" being loaded first
     */
    LoadPriority loadPriority() default LoadPriority.NORMAL;

    enum LoadPriority {
        HIGHEST(4),
        HIGH(3),
        NORMAL(2),
        LOW(1),
        LOWEST(0);

        private final int value;

        LoadPriority(int value) {
            this.value = value;
        }

        public static List<LoadPriority> getInOrder() {
            return Stream.of(LoadPriority.values()).sorted((x, y) -> y.value() - x.value()).toList();
        }

        public int value() {
            return value;
        }
    }
}
