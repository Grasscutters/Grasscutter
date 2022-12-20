package kcp.highway.erasure.bytebuf;

import kcp.highway.erasure.Galois;
import io.netty.buffer.ByteBuf;

/**
 * Created by JinMiao
 * 2018/6/7.
 */
public abstract  class ByteBufCodingLoopBase implements ByteBufCodingLoop {

    @Override
    public boolean checkSomeShards(byte[][] matrixRows, ByteBuf[] inputs, int inputCount, byte[][] toCheck, int checkCount, int offset, int byteCount, byte[] tempBuffer)
    {
        // This is the loop structure for ByteOutputInput, which does not
        // require temporary buffers for checking.
        byte [] [] table = Galois.MULTIPLICATION_TABLE;
        for (int iByte = offset; iByte < offset + byteCount; iByte++) {
            for (int iOutput = 0; iOutput < checkCount; iOutput++) {
                byte [] matrixRow = matrixRows[iOutput];
                int value = 0;
                for (int iInput = 0; iInput < inputCount; iInput++) {
                    value ^= table[matrixRow[iInput] & 0xFF][inputs[iInput].getByte(iByte) & 0xFF];
                }
                if (toCheck[iOutput][iByte] != (byte) value) {
                    return false;
                }
            }
        }
        return true;
    }
}
