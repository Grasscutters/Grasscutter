package kcp.highway.erasure.bytebuf;

import kcp.highway.erasure.Galois;
import io.netty.buffer.ByteBuf;

/**
 * Created by JinMiao
 * 2018/6/7.
 */
public class InputOutputByteBufTableCodingLoop extends ByteBufCodingLoopBase {
    @Override
    public void codeSomeShards(byte[][] matrixRows, ByteBuf[] inputs, int inputCount, ByteBuf[] outputs, int outputCount, int offset, int byteCount) {

        final byte [] [] table = Galois.MULTIPLICATION_TABLE;

        {
            final int iInput = 0;
            final ByteBuf inputShard = inputs[iInput];
            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                final ByteBuf outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    outputShard.setByte(iByte,multTableRow[inputShard.getByte(iByte) & 0xFF]);
                    //outputShard[iByte] = multTableRow[inputShard[iByte] & 0xFF];
                }
            }
        }

        for (int iInput = 1; iInput < inputCount; iInput++) {
            final ByteBuf inputShard = inputs[iInput];
            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                final ByteBuf outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    byte temp =outputShard.getByte(iByte);
                    temp ^= multTableRow[inputShard.getByte(iByte) & 0xFF];
                    outputShard.setByte(iByte,temp);
                    //outputShard[iByte] ^= multTableRow[inputShard[iByte] & 0xFF];
                }
            }
        }
    }


    @Override
    public boolean checkSomeShards(
            byte[][] matrixRows,
            ByteBuf[] inputs, int inputCount,
            byte[][] toCheck, int checkCount,
            int offset, int byteCount,
            byte[] tempBuffer) {

        if (tempBuffer == null) {
            return super.checkSomeShards(matrixRows, inputs, inputCount, toCheck, checkCount, offset, byteCount, null);
        }

        // This is actually the code from OutputInputByteTableCodingLoop.
        // Using the loops from this class would require multiple temp
        // buffers.

        final byte [] [] table = Galois.MULTIPLICATION_TABLE;
        for (int iOutput = 0; iOutput < checkCount; iOutput++) {
            final byte [] outputShard = toCheck[iOutput];
            final byte[] matrixRow = matrixRows[iOutput];
            {
                final int iInput = 0;
                final ByteBuf inputShard = inputs[iInput];
                final byte [] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    tempBuffer[iByte] = multTableRow[inputShard.getByte(iByte) & 0xFF];
                }
            }
            for (int iInput = 1; iInput < inputCount; iInput++) {
                final ByteBuf inputShard = inputs[iInput];
                final byte [] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    tempBuffer[iByte] ^= multTableRow[inputShard.getByte(iByte) & 0xFF];
                }
            }
            for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                if (tempBuffer[iByte] != outputShard[iByte]) {
                    return false;
                }
            }
        }

        return true;
    }




}
