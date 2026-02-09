package com.example.jwtdemo.dummy;

import lombok.extern.slf4j.Slf4j;
import org.apache.jasper.compiler.JspUtil;

@Slf4j
public class Inventry {

    public static boolean getProduct() {
        return true;
    }

    public static void processPayment() throws InterruptedException {
        log.info("Initiated the form service.....");
        Thread.sleep(3000);
        log.info("Completed the payment.......");
    }
}
