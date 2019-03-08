package io.pivotal.pcc.pccclient.util;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchHelper {

    public static final int ONE_BYTE = 1;
    public static final int ONE_KB = 1000;
    public static final int ONE_MB = 1000 * ONE_KB;

    public static final int NUM_BATCHES = 5;

    private final String START_RANGE_KEY = "start";
    private final String END_RANGE_KEY = "start";

    public int getBatchSize(int count) {
        if (count < 100) return 1;

        return (count / NUM_BATCHES);
    }


    @Autowired
    ClientCache clientCache;

    public int[] generate(int batchSize, String regionName){
        Region<String, Object> region = clientCache.getRegion(regionName);

    }

}
