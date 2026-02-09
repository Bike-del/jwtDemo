package com.example.jwtdemo.GlobalException;

import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }
}
