package io.pivotal.pcc.pccclient.service;

import io.pivotal.pcc.pccclient.model.Customer;
import io.pivotal.pcc.pccclient.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl {

    @Autowired
    CustomerRepository customerRepository;

    public void loadCustomerEntries(int count) {

        List<Customer> customers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            customers.add(Customer.of(i,("name"+i),i, new byte[Util.ONE_BYTE]));
        }
        customerRepository.saveAll(customers);
    }

    public void loadCustomerBytes(String bytes) {

    }


}
