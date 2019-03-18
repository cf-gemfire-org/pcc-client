package io.pivotal.pcc.pccclient.util;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchHelper {

    public static final int ONE_KB = 1000;

    public static final int NUM_BATCHES = 5;

    private final String CURRENT_MAX_KEY = "maxKey";

    public static final String CUSTOMER_COUNTER_LOCK = "Customer_Counter_Lock";

    public static int numberOfEntriesFor(int value, UNIT unit) {

        switch (unit) {
            case KB:
                return value;
            case MB:
                return value * 1000;
            case GB:
                return value * 1000 * 1000;
            default:
                throw new IllegalArgumentException("Invalid unit for data size");
        }
    }

    public enum UNIT {
        KB,
        MB,
        GB,
    }

    public int getBatchSize(int count) {
        if (count < 100) return 1;

        return (count / NUM_BATCHES);
    }


    @Autowired
    Cache clientCache;

    /**
     * Returns start and end values for a range of keys that this client reserved.
     *
     * @param batchSize
     * @return startKey, endkey
     */
    public int claimKeys(int batchSize) {
        Region<String, Integer> customerCounter = clientCache.getRegion("CustomerCounter");
        CacheTransactionManager cacheTransactionManager = clientCache.getCacheTransactionManager();

        while (!tryLock(customerCounter, cacheTransactionManager)) {
            tryLock(customerCounter, cacheTransactionManager);
        }

        Integer currentMax = customerCounter.get(CURRENT_MAX_KEY);
        if (currentMax == null) currentMax = 0;

        int newMaxKey = currentMax + batchSize;
        customerCounter.put(CURRENT_MAX_KEY, newMaxKey);
        customerCounter.destroy(CUSTOMER_COUNTER_LOCK); //unlock

        return newMaxKey;

    }

    private boolean tryLock(Region<String, Integer> customerCounter, CacheTransactionManager cacheTransactionManager) {
        while (customerCounter.containsKey(CUSTOMER_COUNTER_LOCK)) {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cacheTransactionManager.begin();
        if (!customerCounter.containsKey(CUSTOMER_COUNTER_LOCK)) {
            customerCounter.put(CUSTOMER_COUNTER_LOCK, 1);
            cacheTransactionManager.commit();
            return true;
        } else {
            cacheTransactionManager.suspend();
            return false;
        }
    }
}
