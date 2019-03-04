package io.pivotal.pcc.pccclient.repositories;


import io.pivotal.pcc.pccclient.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {}
