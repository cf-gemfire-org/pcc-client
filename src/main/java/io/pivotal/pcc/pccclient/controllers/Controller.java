package io.pivotal.pcc.pccclient.controllers;

import io.pivotal.pcc.pccclient.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {


    @Autowired
    ServiceImpl service;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello THere";
    }

    @PutMapping(path = "/customer/loadEntries/{count}")
    public String loadEntries(@PathVariable int count) {
        System.out.printf("$$$$ Loading %d entries now into Customer region", count);
        return service.loadCustomerEntries(count);
    }

    @PutMapping(path = "/customer/loadBytes/{bytes}")
    public String loadBytes(@PathVariable String bytes) {
        System.out.printf("$$$$ Loading %s of data into Customer region", bytes);
        service.loadCustomerBytes(bytes);
        return String.format("Done Loading %s of data into Customer region", bytes);
    }

    @DeleteMapping(path = "/customer/remove/{count}")
    public String removeEntries(@PathVariable int count){
        System.out.printf("$$$$ Removing %d entries from Customer region", count);
        service.removeEntries(count);
        return String.format("Done removing %d entries from Customer region", count);
    }
}
