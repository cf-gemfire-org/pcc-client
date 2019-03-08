package io.pivotal.pcc.pccclient.service;

import io.pivotal.pcc.pccclient.model.Customer;
import io.pivotal.pcc.pccclient.repositories.CustomerRepository;
import io.pivotal.pcc.pccclient.util.BatchHelper;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceImpl {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ClientCache clientCache;

    @Autowired
    BatchHelper batchHelper;

    public String loadCustomerEntries(int count) {
        operateBatch(count, Operation.ADD);
        return String.format("Loaded %d customers", count);
    }

    public void loadCustomerBytes(String bytes) {
    }


    public void removeEntries(int count) {
        Region<Integer, Object> region = clientCache.getRegion("Customer");
        Set<Integer> keys = region.keySetOnServer();
        region.removeAll((keys.stream().limit(count).collect(Collectors.toList())));
    }

    private void operateBatch(int count, Operation operation) {

        int batchSize = batchHelper.getBatchSize(count);

        for (int i = 0; i < BatchHelper.NUM_BATCHES && i < count; i++) {
            operateOneBatch(operation, batchSize);
        }

        int reminder = count - (batchSize * BatchHelper.NUM_BATCHES);
        if (reminder > 0) {
            operateOneBatch(operation, reminder);
        }

    }

    private void operateOneBatch(Operation operation, int batchSize) {
        List<Customer> customers = new ArrayList<>(batchSize);
        int reservedMaxKey = batchHelper.claimKeys(batchSize);
        int startKey = reservedMaxKey - batchSize;
        for (int j = startKey + 1; j <= reservedMaxKey; j++) {
            if (operation.equals(Operation.ADD)) {
                customers.add(Customer.of(j, ("name" + j), j, new byte[BatchHelper.ONE_BYTE]));
            } else {
                customers.add(new Customer(j));
            }
        }

        if (operation.equals(Operation.ADD)) {
            customerRepository.saveAll(customers);
        } else if (operation.equals(Operation.REMOVE)) {
            customerRepository.deleteAll(customers);
        }
        System.out.println("PROCESSED BATCH");

    }

    enum Operation {
        ADD, REMOVE
    }
}
