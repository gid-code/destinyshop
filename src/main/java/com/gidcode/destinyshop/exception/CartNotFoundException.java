package com.gidcode.destinyshop.exception;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String message){
        super(message);
    }
}
