package io.pivotal.pcc.pccclient.service;

import io.pivotal.pcc.pccclient.model.Customer;
import io.pivotal.pcc.pccclient.repositories.CustomerRepository;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
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
        operateBatch(count, Operation.ADD);
        return String.format("Loaded %d customers", count);
    }

    public void loadCustomerBytes(String bytes) {
    }


    public void removeEntries(int count) {
        customerRepository.deleteAll();
    }

    private void operateBatch(int count, Operation operation){

        int batchSize = Util.getBatchSize(count);
        int id = 0;

        for (int i = 0; i < Util.NUM_BATCHES; i++) {
            id = operateOneBatch(operation, batchSize, id);
        }

        int reminder = count - (batchSize * Util.NUM_BATCHES);
        if (reminder > 0) {
            operateOneBatch(operation, reminder, id);
        }

    }

    private int operateOneBatch(Operation operation, int batchSize, int id) {
        List<Customer> customers = new ArrayList<>(batchSize);
        for (int j = 0; j < batchSize; j++) {
            if (operation.equals(Operation.ADD)){
                customers.add(Customer.of(id, ("name" + id), id, new byte[Util.ONE_BYTE]));
            }else{
                customers.add(new Customer(id));
            }
            id++;
        }

        if(operation.equals(Operation.ADD)){
            customerRepository.saveAll(customers);
        }else{
            customerRepository.deleteAll(customers);
        }
        return id;
    }

    enum Operation{
        ADD, REMOVE
    }
}
