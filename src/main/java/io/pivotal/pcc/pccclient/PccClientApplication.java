package io.pivotal.pcc.pccclient;

import io.pivotal.pcc.pccclient.model.Customer;
import io.pivotal.pcc.pccclient.repositories.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;

@SpringBootApplication
@EnableEntityDefinedRegions (basePackageClasses = {Customer.class})
public class PccClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PccClientApplication.class, args);
	}

}
