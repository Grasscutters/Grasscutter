/**
 * Reed-Solomon Coding over 8-bit values.
 *
 * Copyright 2015, Backblaze, Inc.
 */

package kcp.highway.erasure;

import kcp.highway.erasure.bytebuf.ByteBufCodingLoop;
import kcp.highway.erasure.bytebuf.InputOutputByteBufHeapTableCodingLoop;
import io.netty.buffer.ByteBuf;

/**
 * Reed-Solomon Coding over 8-bit values.
 */
public class ReedSolomon {

    private final int dataShardCount;
    private final int parityShardCount;
    private final int totalShardCount;
    private final Matrix matrix;
    private final CodingLoop codingLoop;

    /**
     * Rows from the matrix for encoding parity, each one as its own
     * byte array to allow for efficient access while encoding.
     */
    private final byte [] [] parityRows;

    /**
     * Creates a ReedSolomon codec with the default coding loop.
     */
    public static ReedSolomon create(int dataShardCount, int parityShardCount) {
        return new ReedSolomon(dataShardCount, parityShardCount, new InputOutputByteTableCodingLoop());
    }

    /**
     * Initializes a new encoder/decoder, with a chosen coding loop.
     */
    public ReedSolomon(int dataShardCount, int parityShardCount, CodingLoop codingLoop) {

        // We can have at most 256 shards total, as any more would
        // lead to duplicate rows in the Vandermonde matrix, which
        // would then lead to duplicate rows in the built matrix
        // below. Then any subset of the rows containing the duplicate
        // rows would be singular.
        if (256 < dataShardCount + parityShardCount) {
            throw new IllegalArgumentException("too many shards - max is 256");
        }

        this.dataShardCount = dataShardCount;
        this.parityShardCount = parityShardCount;
        this.codingLoop = codingLoop;
        this.totalShardCount = dataShardCount + parityShardCount;
        matrix = buildMatrix(dataShardCount, this.totalShardCount);
        parityRows = new byte [parityShardCount] [];
        for (int i = 0; i < parityShardCount; i++) {
            parityRows[i] = matrix.getRow(dataShardCount + i);
        }
    }

    /**
     * Returns the number of data shards.
     */
    public int getDataShardCount() {
        return dataShardCount;
    }

    /**
     * Returns the number of parity shards.
     */
    public int getParityShardCount() {
        return parityShardCount;
    }

    /**
     * Returns the total number of shards.
     */
    public int getTotalShardCount() {
        return totalShardCount;
    }

    /**
     * Encodes parity for a set of data shards.
     *
     * @param shards An array containing data shards followed by parity shards.
     *               Each shard is a byte array, and they must all be the same
     *               size.
     * @param offset The index of the first byte in each shard to encode.
     * @param byteCount The number of bytes to encode in each shard.
     *
     */
    public void encodeParity(byte[][] shards, int offset, int byteCount) {
        // Check arguments.
        checkBuffersAndSizes(shards, offset, byteCount);

        // Build the array of output buffers.
        byte [] [] outputs = new byte [parityShardCount] [];
        System.arraycopy(shards, dataShardCount, outputs, 0, parityShardCount);

        // Do the coding.
        codingLoop.codeSomeShards(
                parityRows,
                shards, dataShardCount,
                outputs, parityShardCount,
                offset, byteCount);
    }

    private static final ByteBufCodingLoop LOOP = new InputOutputByteBufHeapTableCodingLoop();


    public void encodeParity(ByteBuf[] shards, int offset, int byteCount) {
        // Check arguments.
        checkBuffersAndSizes(shards, offset, byteCount);

        // Build the array of output buffers.
        ByteBuf [] outputs = new ByteBuf [parityShardCount] ;
        System.arraycopy(shards, dataShardCount, outputs, 0, parityShardCount);

        // Do the coding.
        LOOP.codeSomeShards(
                parityRows,
                shards, dataShardCount,
                outputs, parityShardCount,
                offset, byteCount);
    }


    /**
     * Returns true if the parity shards contain the right data.
     *
     * @param shards An array containing data shards followed by parity shards.
     *               Each shard is a byte array, and they must all be the same
     *               size.
     * @param firstByte The index of the first byte in each shard to check.
     * @param byteCount The number of bytes to check in each shard.
     */
    public boolean isParityCorrect(byte[][] shards, int firstByte, int byteCount) {
        // Check arguments.
        checkBuffersAndSizes(shards, firstByte, byteCount);

        // Build the array of buffers being checked.
        byte [] [] toCheck = new byte [parityShardCount] [];
        System.arraycopy(shards, dataShardCount, toCheck, 0, parityShardCount);

        // Do the checking.
        return codingLoop.checkSomeShards(
                parityRows,
                shards, dataShardCount,
                toCheck, parityShardCount,
                firstByte, byteCount,
                null);
    }

    /**
     * Returns true if the parity shards contain the right data.
     *
     * This method may be significantly faster than the one above that does
     * not use a temporary buffer.
     *
     * @param shards An array containing data shards followed by parity shards.
     *               Each shard is a byte array, and they must all be the same
     *               size.
     * @param firstByte The index of the first byte in each shard to check.
     * @param byteCount The number of bytes to check in each shard.
     * @param tempBuffer A temporary buffer (the same size as each of the
     *                   shards) to use when computing parity.
     */
    public boolean isParityCorrect(byte[][] shards, int firstByte, int byteCount, byte [] tempBuffer) {
        // Check arguments.
        checkBuffersAndSizes(shards, firstByte, byteCount);
        if (tempBuffer.length < firstByte + byteCount) {
            throw new IllegalArgumentException("tempBuffer is not big enough");
        }

        // Build the array of buffers being checked.
        byte [] [] toCheck = new byte [parityShardCount] [];
        System.arraycopy(shards, dataShardCount, toCheck, 0, parityShardCount);

        // Do the checking.
        return codingLoop.checkSomeShards(
                parityRows,
                shards, dataShardCount,
                toCheck, parityShardCount,
                firstByte, byteCount,
                tempBuffer);
    }

    /**
     * Given a list of shards, some of which contain data, fills in the
     * ones that don't have data.
     *
     * Quickly does nothing if all of the shards are present.
     *
     * If any shards are missing (based on the flags in shardsPresent),
     * the data in those shards is recomputed and filled in.
     */
    public void decodeMissing(byte [] [] shards,
                              boolean [] shardPresent,
                              final int offset,
                              final int byteCount) {
        // Check arguments.
        checkBuffersAndSizes(shards, offset, byteCount);

        // Quick check: are all of the shards present?  If so, there's
        // nothing to do.
        int numberPresent = 0;
        for (int i = 0; i < totalShardCount; i++) {
            if (shardPresent[i]) {
                numberPresent += 1;
            }
        }
        if (numberPresent == totalShardCount) {
            // Cool.  All of the shards data data.  We don't
            // need to do anything.
            return;
        }

        // More complete sanity check
        if (numberPresent < dataShardCount) {
            throw new IllegalArgumentException("Not enough shards present");
        }

        // Pull out the rows of the matrix that correspond to the
        // shards that we have and build a square matrix.  This
        // matrix could be used to generate the shards that we have
        // from the original data.
        //
        // Also, pull out an array holding just the shards that
        // correspond to the rows of the submatrix.  These shards
        // will be the input to the decoding process that re-creates
        // the missing data shards.
        Matrix subMatrix = new Matrix(dataShardCount, dataShardCount);
        byte [] [] subShards = new byte [dataShardCount] [];
        {
            int subMatrixRow = 0;
            for (int matrixRow = 0; matrixRow < totalShardCount && subMatrixRow < dataShardCount; matrixRow++) {
                if (shardPresent[matrixRow]) {
                    for (int c = 0; c < dataShardCount; c++) {
                        subMatrix.set(subMatrixRow, c, matrix.get(matrixRow, c));
                    }
                    subShards[subMatrixRow] = shards[matrixRow];
                    subMatrixRow += 1;
                }
            }
        }

        // Invert the matrix, so we can go from the encoded shards
        // back to the original data.  Then pull out the row that
        // generates the shard that we want to decode.  Note that
        // since this matrix maps back to the orginal data, it can
        // be used to create a data shard, but not a parity shard.
        Matrix dataDecodeMatrix = subMatrix.invert();

        // Re-create any data shards that were missing.
        //
        // The input to the coding is all of the shards we actually
        // have, and the output is the missing data shards.  The computation
        // is done using the special decode matrix we just built.
        byte [] [] outputs = new byte [parityShardCount] [];
        byte [] [] matrixRows = new byte [parityShardCount] [];
        int outputCount = 0;
        for (int iShard = 0; iShard < dataShardCount; iShard++) {
            if (!shardPresent[iShard]) {
                outputs[outputCount] = shards[iShard];
                matrixRows[outputCount] = dataDecodeMatrix.getRow(iShard);
                outputCount += 1;
            }
        }
        codingLoop.codeSomeShards(
                matrixRows,
                subShards, dataShardCount,
                outputs, outputCount,
                offset, byteCount);

        // Now that we have all of the data shards intact, we can
        // compute any of the parity that is missing.
        //
        // The input to the coding is ALL of the data shards, including
        // any that we just calculated.  The output is whichever of the
        // data shards were missing.
        outputCount = 0;
        for (int iShard = dataShardCount; iShard < totalShardCount; iShard++) {
            if (!shardPresent[iShard]) {
                outputs[outputCount] = shards[iShard];
                matrixRows[outputCount] = parityRows[iShard - dataShardCount];
                outputCount += 1;
            }
        }
        codingLoop.codeSomeShards(
                matrixRows,
                shards, dataShardCount,
                outputs, outputCount,
                offset, byteCount);
    }


    public void decodeMissing(ByteBuf [] shards,
                              boolean [] shardPresent,
                              final int offset,
                              final int byteCount) {
        // Check arguments.
        checkBuffersAndSizes(shards, offset, byteCount);

        // Quick check: are all of the shards present?  If so, there's
        // nothing to do.
        int numberPresent = 0;
        for (int i = 0; i < totalShardCount; i++) {
            if (shardPresent[i]) {
                numberPresent += 1;
            }
        }
        if (numberPresent == totalShardCount) {
            // Cool.  All of the shards data data.  We don't
            // need to do anything.
            return;
        }

        // More complete sanity check
        if (numberPresent < dataShardCount) {
            throw new IllegalArgumentException("Not enough shards present");
        }

        // Pull out the rows of the matrix that correspond to the
        // shards that we have and build a square matrix.  This
        // matrix could be used to generate the shards that we have
        // from the original data.
        //
        // Also, pull out an array holding just the shards that
        // correspond to the rows of the submatrix.  These shards
        // will be the input to the decoding process that re-creates
        // the missing data shards.
        Matrix subMatrix = new Matrix(dataShardCount, dataShardCount);
        ByteBuf [] subShards = new ByteBuf[dataShardCount];
        {
            int subMatrixRow = 0;
            for (int matrixRow = 0; matrixRow < totalShardCount && subMatrixRow < dataShardCount; matrixRow++) {
                if (shardPresent[matrixRow]) {
                    for (int c = 0; c < dataShardCount; c++) {
                        subMatrix.set(subMatrixRow, c, matrix.get(matrixRow, c));
                    }
                    subShards[subMatrixRow] = shards[matrixRow];
                    subMatrixRow += 1;
                }
            }
        }

        // Invert the matrix, so we can go from the encoded shards
        // back to the original data.  Then pull out the row that
        // generates the shard that we want to decode.  Note that
        // since this matrix maps back to the orginal data, it can
        // be used to create a data shard, but not a parity shard.
        Matrix dataDecodeMatrix = subMatrix.invert();

        // Re-create any data shards that were missing.
        //
        // The input to the coding is all of the shards we actually
        // have, and the output is the missing data shards.  The computation
        // is done using the special decode matrix we just built.
        ByteBuf [] outputs = new ByteBuf[parityShardCount];
        byte [] [] matrixRows = new byte [parityShardCount] [];
        int outputCount = 0;
        for (int iShard = 0; iShard < dataShardCount; iShard++) {
            if (!shardPresent[iShard]) {
                outputs[outputCount] = shards[iShard];
                matrixRows[outputCount] = dataDecodeMatrix.getRow(iShard);
                outputCount += 1;
            }
        }
        LOOP.codeSomeShards(
                matrixRows,
                subShards, dataShardCount,
                outputs, outputCount,
                offset, byteCount);

        // Now that we have all of the data shards intact, we can
        // compute any of the parity that is missing.
        //
        // The input to the coding is ALL of the data shards, including
        // any that we just calculated.  The output is whichever of the
        // data shards were missing.
        outputCount = 0;
        for (int iShard = dataShardCount; iShard < totalShardCount; iShard++) {
            if (!shardPresent[iShard]) {
                outputs[outputCount] = shards[iShard];
                matrixRows[outputCount] = parityRows[iShard - dataShardCount];
                outputCount += 1;
            }
        }
        LOOP.codeSomeShards(
                matrixRows,
                shards, dataShardCount,
                outputs, outputCount,
                offset, byteCount);
    }



    /**
     * Checks the consistency of arguments passed to public methods.
     */
    private void checkBuffersAndSizes(ByteBuf [] shards, int offset, int byteCount) {
        // The number of buffers should be equal to the number of
        // data shards plus the number of parity shards.
        if (shards.length != totalShardCount) {
            throw new IllegalArgumentException("wrong number of shards: " + shards.length);
        }

        // All of the shard buffers should be the same length.
        int shardLength = shards[0].readableBytes();
        for (int i = 1; i < shards.length; i++) {
            if (shards[i].readableBytes() != shardLength) {
                throw new IllegalArgumentException("Shards are different sizes");
            }
        }

        // The offset and byteCount must be non-negative and fit in the buffers.
        if (offset < 0) {
            throw new IllegalArgumentException("offset is negative: " + offset);
        }
        if (byteCount < 0) {
            throw new IllegalArgumentException("byteCount is negative: " + byteCount);
        }
        if (shardLength < offset + byteCount) {
            throw new IllegalArgumentException("buffers to small: " + byteCount + offset);
        }
    }

    /**
     * Checks the consistency of arguments passed to public methods.
     */
    private void checkBuffersAndSizes(byte [] [] shards, int offset, int byteCount) {
        // The number of buffers should be equal to the number of
        // data shards plus the number of parity shards.
        if (shards.length != totalShardCount) {
            throw new IllegalArgumentException("wrong number of shards: " + shards.length);
        }

        // All of the shard buffers should be the same length.
        int shardLength = 0;
        boolean allShardIsEmpty= true;
        //int shardLength = shards[0].length;
        for (int i = 1; i < shards.length; i++) {
            if(shards[i]==null) {
                continue;
            }
            allShardIsEmpty = false;
            if(shardLength==0){
                shardLength = shards[i].length;
                continue;
            }
            if (shards[i].length != shardLength) {
                throw new IllegalArgumentException("Shards are different sizes");
            }
        }

        if(allShardIsEmpty){
            throw new IllegalArgumentException("Shards are empty");
        }

        // The offset and byteCount must be non-negative and fit in the buffers.
        if (offset < 0) {
            throw new IllegalArgumentException("offset is negative: " + offset);
        }
        if (byteCount < 0) {
            throw new IllegalArgumentException("byteCount is negative: " + byteCount);
        }
        if (shardLength < offset + byteCount) {
            throw new IllegalArgumentException("buffers to small: " + byteCount + offset);
        }
    }

    /**
     * Create the matrix to use for encoding, given the number of
     * data shards and the number of total shards.
     *
     * The top square of the matrix is guaranteed to be an identity
     * matrix, which means that the data shards are unchanged after
     * encoding.
     */
    private static Matrix buildMatrix(int dataShards, int totalShards) {
        // Start with a Vandermonde matrix.  This matrix would work,
        // in theory, but doesn't have the property that the data
        // shards are unchanged after encoding.
        Matrix vandermonde = vandermonde(totalShards, dataShards);

        // Multiple by the inverse of the top square of the matrix.
        // This will make the top square be the identity matrix, but
        // preserve the property that any square subset of rows is
        // invertible.
        Matrix top = vandermonde.submatrix(0, 0, dataShards, dataShards);
        return vandermonde.times(top.invert());
    }

    /**
     * Create a Vandermonde matrix, which is guaranteed to have the
     * property that any subset of rows that forms a square matrix
     * is invertible.
     *
     * @param rows Number of rows in the result.
     * @param cols Number of columns in the result.
     * @return A Matrix.
     */
    private static Matrix vandermonde(int rows, int cols) {
        Matrix result = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                result.set(r, c, Galois.exp((byte) r, c));
            }
        }
        return result;
    }
}
