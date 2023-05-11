package emu.grasscutter.utils;

/* Various methods to convert from A -> B. */
public interface ConversionUtils {
    /**
     * Converts in-game minutes to days.
     *
     * @param minutes The elapsed in-game minutes.
     * @return The elapsed in-game days.
     */
    static long gameTimeToDays(long minutes) {
        return minutes / 1440;
    }

    /**
     * Converts in-game minutes to hours.
     *
     * @param minutes The elapsed in-game minutes.
     * @return The elapsed in-game hours.
     */
    static long gameTimeToHours(long minutes) {
        return minutes / 60;
    }
}
