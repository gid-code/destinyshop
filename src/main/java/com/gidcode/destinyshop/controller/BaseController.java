package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.exception.AlreadyExistException;
import com.gidcode.destinyshop.exception.OrderNotFoundException;
import com.gidcode.destinyshop.exception.UserNotFoundException;
import com.gidcode.destinyshop.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    public ResponseEntity<ApiResponse> handleException(Exception e){
        if (e instanceof OrderNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", null));
        } else if (e instanceof UserNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", null));
        } else if (e instanceof AlreadyExistException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
