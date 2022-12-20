/**
 * One specific ordering/nesting of the coding loops.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.highway.erasure;

public class InputByteOutputTableCodingLoop extends CodingLoopBase {

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
            for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                final byte inputByte = inputShard[iByte];
                final byte [] multTableRow = table[inputByte & 0xFF];
                for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                    final byte[] outputShard = outputs[iOutput];
                    final byte[] matrixRow = matrixRows[iOutput];
                    outputShard[iByte] = multTableRow[matrixRow[iInput] & 0xFF];
                }
            }
        }

        for (int iInput = 1; iInput < inputCount; iInput++) {
            final byte[] inputShard = inputs[iInput];
            for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                final byte inputByte = inputShard[iByte];
                final byte [] multTableRow = table[inputByte & 0xFF];
                for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                    final byte[] outputShard = outputs[iOutput];
                    final byte[] matrixRow = matrixRows[iOutput];
                    outputShard[iByte] ^= multTableRow[matrixRow[iInput] & 0xFF];
                }
            }
        }
    }

}
