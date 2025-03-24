package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.dto.UserDto;
import com.gidcode.destinyshop.exception.UserNotFoundException;
import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.request.CreateUserRequest;
import com.gidcode.destinyshop.request.UpdateUserRequest;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController extends BaseController{
    private final IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id).toDto();
            return ResponseEntity.ok(new ApiResponse("User found",user));
        } catch (UserNotFoundException e) {
            return handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            UserDto user = userService.createUser(request).toDto();
            return ResponseEntity.ok(new ApiResponse("Create User Success!", user));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long id){
        try {
            UserDto user = userService.updateUser(request, id).toDto();
            return ResponseEntity.ok(new ApiResponse("Update User Success!", user));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!",null));
        } catch (Exception e) {
            return handleException(e);
        }
    }
}
