package com.example.jwtdemo.Controller;

import com.example.jwtdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<String> processOrder() throws InterruptedException {
        service.processOrder(); // synchronous
        // asynchronous
        service.notifyUser();
        service.assignVendor();
        service.packing();
        service.assignDeliveryPartner();
        service.assignTrailerAndDispatch();
        return ResponseEntity.ok("All Done......");
    }

}
