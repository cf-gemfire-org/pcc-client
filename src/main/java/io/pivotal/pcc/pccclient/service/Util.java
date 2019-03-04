package io.pivotal.pcc.pccclient.service;

public class Util {

    static final int ONE_BYTE = 1;
    static final int ONE_KB = 1000;
    static final int ONE_MB = 1000 * ONE_KB;

    static final int NUM_BATCHES = 5;

    static int getBatchSize(int count) {
        if (count < 100) return 1;

        return (count / NUM_BATCHES);
    }

}
