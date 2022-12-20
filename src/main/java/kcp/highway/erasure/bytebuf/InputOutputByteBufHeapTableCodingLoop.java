/**
 * One specific ordering/nesting of the coding loops.
 * <p>
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.highway.erasure.bytebuf;

import kcp.highway.erasure.Galois;
import io.netty.buffer.ByteBuf;

public class InputOutputByteBufHeapTableCodingLoop extends ByteBufCodingLoopBase {

    @Override
    public void codeSomeShards(byte[][] matrixRows, ByteBuf[] inputs, int inputCount, ByteBuf[] outputs, int outputCount, int offset, int byteCount) {

        final byte[][] table = Galois.MULTIPLICATION_TABLE;
        final int count = offset + byteCount;
        final byte[] inputShard = new byte[count];
        final byte[] outputShard = new byte[count];


        {
            final int iInput = 0;
            //final byte[] inputShard = inputs[iInput];
            inputs[iInput].getBytes(0,inputShard);

            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                outputs[iOutput].getBytes(0,outputShard);
                //final byte[] outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < count; iByte++) {
                    outputShard[iByte] = multTableRow[inputShard[iByte] & 0xFF];
                }
                outputs[iOutput].setBytes(0,outputShard);
            }
        }

        for (int iInput = 1; iInput < inputCount; iInput++) {
            //final byte[] inputShard = inputs[iInput];
            inputs[iInput].getBytes(0,inputShard);
            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                outputs[iOutput].getBytes(0,outputShard);
                //final byte[] outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < count; iByte++) {
                    outputShard[iByte] ^= multTableRow[inputShard[iByte] & 0xFF];
                }
                outputs[iOutput].setBytes(0,outputShard);
            }
        }
    }

    @Override
    public boolean checkSomeShards(
            byte[][] matrixRows,
            ByteBuf[] inputs, int inputCount,
            byte[][] toCheck, int checkCount,
            int offset, int byteCount,
            byte[] tempBuffer){

        if (tempBuffer == null) {
            return super.checkSomeShards(matrixRows, inputs, inputCount, toCheck, checkCount, offset, byteCount, null);
        }
        final int count = offset + byteCount;
        final byte[] inputShard = new byte[count];

        // This is actually the code from OutputInputByteTableCodingLoop.
        // Using the loops from this class would require multiple temp
        // buffers.

        final byte[][] table = Galois.MULTIPLICATION_TABLE;
        for (int iOutput = 0; iOutput < checkCount; iOutput++) {
            final byte[] outputShard = toCheck[iOutput];
            final byte[] matrixRow = matrixRows[iOutput];
            {
                final int iInput = 0;
                inputs[iInput].getBytes(0,inputShard);
                //final byte[] inputShard = inputs[iInput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < count; iByte++) {
                    tempBuffer[iByte] = multTableRow[inputShard[iByte] & 0xFF];
                }
            }
            for (int iInput = 1; iInput < inputCount; iInput++) {
                inputs[iInput].getBytes(0,inputShard);
                //final byte[] inputShard = inputs[iInput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < count; iByte++) {
                    tempBuffer[iByte] ^= multTableRow[inputShard[iByte] & 0xFF];
                }
            }
            for (int iByte = offset; iByte < count; iByte++) {
                if (tempBuffer[iByte] != outputShard[iByte]) {
                    return false;
                }
            }
        }

        return true;
    }

}
