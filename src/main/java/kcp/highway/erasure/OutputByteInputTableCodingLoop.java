/**
 * One specific ordering/nesting of the coding loops.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.highway.erasure;

public class OutputByteInputTableCodingLoop extends CodingLoopBase {

    @Override
    public void codeSomeShards(
            byte[][] matrixRows,
            byte[][] inputs, int inputCount,
            byte[][] outputs, int outputCount,
            int offset, int byteCount) {

        final byte [] [] table = Galois.MULTIPLICATION_TABLE;
        for (int iOutput = 0; iOutput < outputCount; iOutput++) {
            final byte[] outputShard = outputs[iOutput];
            final byte[] matrixRow = matrixRows[iOutput];
            for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                int value = 0;
                for (int iInput = 0; iInput < inputCount; iInput++) {
                    final byte[] inputShard = inputs[iInput];
                    final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                    value ^= multTableRow[inputShard[iByte] & 0xFF];
                }
                outputShard[iByte] = (byte) value;
            }
        }
    }

}
