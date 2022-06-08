/**
 * One specific ordering/nesting of the coding loops.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.erasure;

public class InputOutputByteTableCodingLoop extends CodingLoopBase {

    @Override
    public void codeSomeShards(
            byte[][] matrixRows,
            byte[][] inputs, int inputCount,
            byte[][] outputs, int outputCount,
            int offset, int byteCount) {

        final byte [] [] table = Galois.MULTIPLICATION_TABLE;

        {
            final int iInput = 0;
            final byte[] inputShard = inputs[iInput];
            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                final byte[] outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    outputShard[iByte] = multTableRow[inputShard[iByte] & 0xFF];
                }
            }
        }

        for (int iInput = 1; iInput < inputCount; iInput++) {
            final byte[] inputShard = inputs[iInput];
            for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                final byte[] outputShard = outputs[iOutput];
                final byte[] matrixRow = matrixRows[iOutput];
                final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    outputShard[iByte] ^= multTableRow[inputShard[iByte] & 0xFF];
                }
            }
        }
    }

    @Override
    public boolean checkSomeShards(
            byte[][] matrixRows,
            byte[][] inputs, int inputCount,
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
                final byte [] inputShard = inputs[iInput];
                final byte [] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    tempBuffer[iByte] = multTableRow[inputShard[iByte] & 0xFF];
                }
            }
            for (int iInput = 1; iInput < inputCount; iInput++) {
                final byte [] inputShard = inputs[iInput];
                final byte [] multTableRow = table[matrixRow[iInput] & 0xFF];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    tempBuffer[iByte] ^= multTableRow[inputShard[iByte] & 0xFF];
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
