package emu.grasscutter.net.packet;

import java.io.*;

public class PacketWriter {
    // Little endian
    private final ByteArrayOutputStream baos;

    public PacketWriter() {
        this.baos = new ByteArrayOutputStream(128);
    }

    public byte[] build() {
        return baos.toByteArray();
    }

    // Writers

    public void writeEmpty(int i) {
        while (i > 0) {
            baos.write(0);
            i--;
        }
    }

    public void writeMax(int i) {
        while (i > 0) {
            baos.write(0xFF);
            i--;
        }
    }

    public void writeInt8(byte b) {
        baos.write(b);
    }

    public void writeInt8(int i) {
        baos.write((byte) i);
    }

    public void writeBoolean(boolean b) {
        baos.write(b ? 1 : 0);
    }

    public void writeUint8(byte b) {
        // Unsigned byte
        baos.write(b & 0xFF);
    }

    public void writeUint8(int i) {

        baos.write((byte) i & 0xFF);
    }

    public void writeUint16(int i) {
        // Unsigned short
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
    }

    public void writeUint24(int i) {
        // 24 bit integer
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
    }

    public void writeInt16(int i) {
        // Signed short
        baos.write((byte) i);
        baos.write((byte) (i >>> 8));
    }

    public void writeUint32(int i) {
        // Unsigned int
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 24) & 0xFF));
    }

    public void writeInt32(int i) {
        // Signed int
        baos.write((byte) i);
        baos.write((byte) (i >>> 8));
        baos.write((byte) (i >>> 16));
        baos.write((byte) (i >>> 24));
    }

    public void writeUint32(long i) {
        // Unsigned int (long)
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 24) & 0xFF));
    }

    public void writeFloat(float f) {
        this.writeUint32(Float.floatToRawIntBits(f));
    }

    public void writeUint64(long l) {
        baos.write((byte) (l & 0xFF));
        baos.write((byte) ((l >>> 8) & 0xFF));
        baos.write((byte) ((l >>> 16) & 0xFF));
        baos.write((byte) ((l >>> 24) & 0xFF));
        baos.write((byte) ((l >>> 32) & 0xFF));
        baos.write((byte) ((l >>> 40) & 0xFF));
        baos.write((byte) ((l >>> 48) & 0xFF));
        baos.write((byte) ((l >>> 56) & 0xFF));
    }

    public void writeDouble(double d) {
        long l = Double.doubleToLongBits(d);
        this.writeUint64(l);
    }

    public void writeString16(String s) {
        if (s == null) {
            this.writeUint16(0);
            return;
        }

        this.writeUint16(s.length() * 2);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            this.writeUint16((short) c);
        }
    }

    public void writeString8(String s) {
        if (s == null) {
            this.writeUint16(0);
            return;
        }

        this.writeUint16(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            this.writeUint8((byte) c);
        }
    }

    public void writeDirectString8(String s, int expectedSize) {
        if (s == null) {
            return;
        }

        for (int i = 0; i < expectedSize; i++) {
            char c = i < s.length() ? s.charAt(i) : 0;
            this.writeUint8((byte) c);
        }
    }

    public void writeBytes(byte[] bytes) {
        try {
            baos.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeBytes(int[] bytes) {
        byte[] b = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) b[i] = (byte) bytes[i];

        try {
            baos.write(b);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
