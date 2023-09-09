package io.grasscutter;

import emu.grasscutter.utils.Utils;
import emu.grasscutter.utils.objects.Returnable;

public interface TestUtils {
    /**
     * Waits for a condition to be met.
     *
     * @param condition The condition.
     */
    static void waitFor(Returnable<Boolean> condition) {
        while (!condition.invoke()) {
            Utils.sleep(100);
        }
    }
}
