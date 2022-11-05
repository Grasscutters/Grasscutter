package emu.grasscutter.server.webapi.tools;

import java.util.Optional;

public class ParseTools {
    private ParseTools(){
    }

    public static Optional<Byte> tryParseByte(String str) {
        try {
            return Optional.of(Byte.parseByte(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Short> tryParseShort(String str) {
        try {
            return Optional.of(Short.parseShort(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Integer> tryParseInteger(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> tryParseLong(String str) {
        try {
            return Optional.of(Long.parseLong(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Float> tryParseFloat(String str) {
        try {
            return Optional.of(Float.parseFloat(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Double> tryParseDouble(String str) {
        try {
            return Optional.of(Double.parseDouble(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Boolean> tryParseBoolean(String str) {
        try {
            return Optional.of(Boolean.parseBoolean(str));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
