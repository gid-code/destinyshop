package com.gidcode.destinyshop.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message){
        super(message);
    }
}
