package io.pivotal.pcc.pccclient.service;

import io.pivotal.pcc.pccclient.model.Customer;
import io.pivotal.pcc.pccclient.repositories.CustomerRepository;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ClientCache clientCache;

    public String loadCustomerEntries(int count) {

        int batchSize = Util.getBatchSize(count);
        List<Customer> customers = new ArrayList<>(batchSize);
        int id = 0;

        for (int i = 0; i < Util.NUM_BATCHES; i++) {
            for (int j = 0; j < batchSize; j++) {
                customers.add(Customer.of(id, ("name" + id), id, new byte[Util.ONE_BYTE]));
                id++;
            }
            customerRepository.saveAll(customers);
            customers.clear();
        }

        int reminder = count - (batchSize * Util.NUM_BATCHES);
        if (reminder > 0) {
            for (int j = 0; j < reminder; j++) {
                customers.add(Customer.of(id, ("name" + id), id, new byte[Util.ONE_BYTE]));
                id++;
            }
        }

        return String.format("Loaded %d customers in %d batches with batch size %d", count, Util.NUM_BATCHES, batchSize);
    }

    public void loadCustomerBytes(String bytes) {

    }


    public void removeEntries(int count) {


        customerRepository.deleteAll();
    }
}
