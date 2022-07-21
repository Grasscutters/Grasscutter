package emu.grasscutter.utils;

public class ByteHelper {
    public static byte[] changeBytes(byte[] a) {
        byte[] b = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[a.length - i - 1];
        }
        return b;
    }

    public static byte[] longToBytes(long x) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (x >> 56);
        bytes[1] = (byte) (x >> 48);
        bytes[2] = (byte) (x >> 40);
        bytes[3] = (byte) (x >> 32);
        bytes[4] = (byte) (x >> 24);
        bytes[5] = (byte) (x >> 16);
        bytes[6] = (byte) (x >> 8);
        bytes[7] = (byte) (x);
        return bytes;
    }
}
