/**
 * One specific ordering/nesting of the coding loops.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.highway.erasure;

public class ByteInputOutputTableCodingLoop extends CodingLoopBase {

    @Override
    public void codeSomeShards(
            byte[][] matrixRows,
            byte[][] inputs, int inputCount,
            byte[][] outputs, int outputCount,
            int offset, int byteCount) {

        final byte[][] table = Galois.MULTIPLICATION_TABLE;

        for (int iByte = offset; iByte < offset + byteCount; iByte++) {
            {
                final int iInput = 0;
                final byte[] inputShard = inputs[iInput];
                final byte inputByte = inputShard[iByte];
                for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                    final byte[] outputShard = outputs[iOutput];
                    final byte[] matrixRow = matrixRows[iOutput];
                    final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                    outputShard[iByte] = multTableRow[inputByte & 0xFF];
                }
            }
            for (int iInput = 1; iInput < inputCount; iInput++) {
                final byte[] inputShard = inputs[iInput];
                final byte inputByte = inputShard[iByte];
                for (int iOutput = 0; iOutput < outputCount; iOutput++) {
                    final byte[] outputShard = outputs[iOutput];
                    final byte[] matrixRow = matrixRows[iOutput];
                    final byte[] multTableRow = table[matrixRow[iInput] & 0xFF];
                    outputShard[iByte] ^= multTableRow[inputByte & 0xFF];
                }
            }
        }
    }
}
