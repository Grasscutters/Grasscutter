package emu.grasscutter.net.packet;

import com.google.protobuf.GeneratedMessageV3;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.utils.Crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BasePacket {
    private static final int const1 = 17767; // 0x4567
    private static final int const2 = -30293; // 0x89ab

    private int opcode;
    private boolean shouldBuildHeader = false;

    private byte[] header;
    private byte[] data;

    // Encryption
    private boolean useDispatchKey;
    public boolean shouldEncrypt = true;

    public BasePacket(int opcode) {
        this.opcode = opcode;
    }

    public BasePacket(int opcode, int clientSequence) {
        this.opcode = opcode;
        this.buildHeader(clientSequence);
    }

    public BasePacket(int opcode, boolean buildHeader) {
        this.opcode = opcode;
        this.shouldBuildHeader = buildHeader;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public boolean useDispatchKey() {
        return this.useDispatchKey;
    }

    public void setUseDispatchKey(boolean useDispatchKey) {
        this.useDispatchKey = useDispatchKey;
    }

    public byte[] getHeader() {
        return this.header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public boolean shouldBuildHeader() {
        return this.shouldBuildHeader;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setData(GeneratedMessageV3 proto) {
        this.data = proto.toByteArray();
    }

    @SuppressWarnings("rawtypes")
    public void setData(GeneratedMessageV3.Builder proto) {
        this.data = proto.build().toByteArray();
    }

    public BasePacket buildHeader(int clientSequence) {
        if (this.getHeader() != null && clientSequence == 0) {
            return this;
        }
        this.setHeader(PacketHead.newBuilder().setClientSequenceId(clientSequence).setTimestamp(System.currentTimeMillis()).build().toByteArray());
        return this;
    }

    public byte[] build() {
        if (this.getHeader() == null) {
            this.header = new byte[0];
        }

        if (this.getData() == null) {
            this.data = new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream(2 + 2 + 2 + 4 + this.getHeader().length + this.getData().length + 2);

        this.writeUint16(baos, const1);
        this.writeUint16(baos, this.opcode);
        this.writeUint16(baos, this.header.length);
        this.writeUint32(baos, this.data.length);
        this.writeBytes(baos, this.header);
        this.writeBytes(baos, this.data);
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
        // Unsigned int (long)
        baos.write((byte) ((i >>> 24) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) (i & 0xFF));
    }

    public void writeBytes(ByteArrayOutputStream baos, byte[] bytes) {
        try {
            baos.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
