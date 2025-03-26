package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.exception.*;
import com.gidcode.destinyshop.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public class BaseController {

    public ResponseEntity<ApiResponse> handleException(Exception e){
        if (e instanceof OrderNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", null));
        } else if (e instanceof UserNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", null));
        } else if (e instanceof ProductNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } else if (e instanceof CartNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } else if (e instanceof JwtException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        } else if (e instanceof AuthenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        } else if (e instanceof AlreadyExistException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
