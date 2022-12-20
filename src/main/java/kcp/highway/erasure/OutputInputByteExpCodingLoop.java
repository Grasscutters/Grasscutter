/**
 * One specific ordering/nesting of the coding loops.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package kcp.highway.erasure;

public class OutputInputByteExpCodingLoop extends CodingLoopBase {

    @Override
    public void codeSomeShards(
            byte[][] matrixRows,
            byte[][] inputs, int inputCount,
            byte[][] outputs, int outputCount,
            int offset, int byteCount) {

        for (int iOutput = 0; iOutput < outputCount; iOutput++) {
            final byte [] outputShard = outputs[iOutput];
            final byte [] matrixRow = matrixRows[iOutput];
            {
                final int iInput = 0;
                final byte [] inputShard = inputs[iInput];
                final byte matrixByte = matrixRow[iInput];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    outputShard[iByte] = Galois.multiply(matrixByte, inputShard[iByte]);
                }
            }
            for (int iInput = 1; iInput < inputCount; iInput++) {
                final byte [] inputShard = inputs[iInput];
                final byte matrixByte = matrixRow[iInput];
                for (int iByte = offset; iByte < offset + byteCount; iByte++) {
                    outputShard[iByte] ^= Galois.multiply(matrixByte, inputShard[iByte]);
                }
            }
        }
    }

}
