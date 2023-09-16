package emu.grasscutter.utils.algorithms;

public final class MersenneTwister64 {
    // Period parameters
    private static final int N = 312;
    private static final int M = 156;
    private static final long MATRIX_A =
            0xB5026F5AA96619E9L; // private static final * constant vector a
    private static final long UPPER_MASK = 0xFFFFFFFF80000000L; // most significant w-r bits
    private static final int LOWER_MASK = 0x7FFFFFFF; // least significant r bits

    private final long[] mt = new long[N]; // the array for the state vector
    private int mti; // mti == N+1 means mt[N] is not initialized

    public synchronized void setSeed(long seed) {
        mt[0] = seed;
        for (mti = 1; mti < N; mti++) {
            mt[mti] = (0x5851F42D4C957F2DL * (mt[mti - 1] ^ (mt[mti - 1] >>> 62)) + mti);
        }
    }

    public synchronized long nextLong() {
        int i;
        long x;
        final long[] mag01 = {0x0L, MATRIX_A};

        if (mti >= N) { // generate N words at one time
            if (mti == N + 1) {
                setSeed(5489L);
            }

            for (i = 0; i < N - M; i++) {
                x = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
                mt[i] = mt[i + M] ^ (x >>> 1) ^ mag01[(int) (x & 0x1)];
            }
            for (; i < N - 1; i++) {
                x = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
                mt[i] = mt[i + (M - N)] ^ (x >>> 1) ^ mag01[(int) (x & 0x1)];
            }
            x = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
            mt[N - 1] = mt[M - 1] ^ (x >>> 1) ^ mag01[(int) (x & 0x1)];

            mti = 0;
        }

        x = mt[mti++];
        x ^= (x >>> 29) & 0x5555555555555555L;
        x ^= (x << 17) & 0x71D67FFFEDA60000L;
        x ^= (x << 37) & 0xFFF7EEE000000000L;
        x ^= (x >>> 43);

        return x;
    }
}
