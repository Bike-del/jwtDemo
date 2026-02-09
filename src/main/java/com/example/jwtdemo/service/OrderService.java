package com.example.jwtdemo.service;


import com.example.jwtdemo.dummy.Inventry;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ThemeResolver;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {

    private final ThemeResolver themeResolver;

    public OrderService(ThemeResolver themeResolver) {
        this.themeResolver = themeResolver;
    }

    public String processOrder() throws InterruptedException {
        if(Inventry.getProduct()){
            Inventry.processPayment();
        }else{
            throw new RuntimeException("Technical Error....");
        }
        return "order placed.....";
    }

    @Async("asyncTaskExecutor")
    public  void notifyUser() throws InterruptedException {
        Thread.sleep(4000);
        log.info("Notified the user"+Thread.currentThread().getName());

    }

    @Async("asyncTaskExecutor")
    public  void assignVendor() throws InterruptedException {
        Thread.sleep(5000);
        log.info("Assign order to vender "+Thread.currentThread().getName());

    }
    @Async("asyncTaskExecutor")
    public  void packing() throws InterruptedException {
        Thread.sleep(2000);
        log.info("Order packing compeleted.."+Thread.currentThread().getName());

    }

    @Async("asyncTaskExecutor")
    public void assignDeliveryPartner() throws InterruptedException {
        Thread.sleep(10000L);
        log.info("Delivery partner assigned " + Thread.currentThread().getName());
    }


    @Async("asyncTaskExecutor")
    public void assignTrailerAndDispatch() throws InterruptedException {
        Thread.sleep(3000L);
        log.info("Trailer assigned and Order dispatched " + Thread.currentThread().getName());
    }

    @Async
    public CompletableFuture<String> process() throws InterruptedException {
        String result = doheavywork();
        return CompletableFuture.completedFuture(result);

    }

    private String doheavywork() throws InterruptedException {
        Thread.sleep(5000);
        return "heavy work done...";
    }
}
