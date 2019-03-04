package io.pivotal.pcc.pccclient.controllers;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestControllerEndpoint(id = "/say")
public class Controller {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello THere";
    }

    @PostMapping("/loadBytes")
    public String loadBytes(){
        return "Loading bytes";
    }

    @PostMapping("/loadEntries")
    public String loadEntries(){
        return "Loading Entries";
    }
}
