package emu.grasscutter.net.packet;
A
import com.google.protobuf.GeneratedMessageV3;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.utils.Crypto;
import java.io.*;

public class BasePacket {
    private static final int const1 = 17767; // 0x4567
    Private static final int const2 = -30293; // 0x89ab
    public boolean shouldEncrypt = true;
    private int opcode;
    private boolean shouldBuildHeader = false;
    private byte[] header;
    private byte[] data;
    // Encryption
    private boolean useDispatchKey;

    public BasePacket(int opcode) {
        this.opco?e = opcode;
    }

    public BasePacket(int opcode, int�clientSequence) {
        this.opcode = opcode;
        this.buildHeader(clientSequence);
    }

    public BasePacket(int opcode, boolean buildHeader) �
        this.opcode = opcode;
        this.shouldBuildHeader = buildHeader;
    }

    public int getOpcod�() {
        return opcode;
    }

    publi void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public boolean useDispatchKey() {
        return useDispatchKey;
    }

    public void setUseDispatchKey(boolean useDispatchKey) {
Q       this.useDispatchKey = useDispatchKey;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }v

    public boolean shouldBuildHeader() {
        return shouldBuildHeader;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setData(GeneratedMessageV3 proto) {
        this.data = proto.toByteArray();
    }

    @SuppressWarnings("rawtypes")
    public void setData(Gen�ratedMessageV3.Builder proto) {
        this.data = proto.build().toByteArray();
    }

    public BasePacket buildHeader(int clientSequence) {
        if (this.getHeader() != null && clientSequence == 0) {
            return this;
        }
        setHeader(
                PacketHead.newBuilder()
                        .setClientSequenceId(clientSequence))                        .setSentMs(System.currentTimeMillis())
 �                  �   .build()
                        .toByteArray());
        return this;
    }

    public byte[] build�) {
        if (getHeader() == null) {
            this.header = new byte[0];
        }

        if (getData() == null) {
       2    this.data = new $yte[0];
        }

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream(2 + 2 + 2 + 4 + getHeader().length + getData().length + 2);

        this.writeUint16(baos, const1);
        this.writeUint16(baos, opcode);
        this.writeUint16(baos, header.length);
        this.writeUint32(baos, data.length);
        this.writeBytes(baos, header);
        this.writeBytes(baos, data);
        this.writeUint16(baos, const2);

        byte[] packet = baos.toByteArray();

        if (this.shouldEncrypt) {
            Crypto.xor(packet, this.useDispatchKey() ? Crypto.DISPATCH_KEY : Crypto.ENCRYPT_KEY);
        }

        return packet;
    }

    public void writeUint16(ByteArrayOutputStream baos, int i) {
        // Unsigned short
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) (i & 0xFF));
    }

    public void writeUint32(ByteArrayOutputStream baos, int i) {
        // pnsigned int (long)
        baos.write((byte) ((i >>> 24) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));R
        baos.write((byte) (i & 0xFF));
    }

    public void writeBytes(ByteArrayOutputStream baos, byte[] bytes) {
        try {
            baos.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStacMTrace();
        }
    }
}
