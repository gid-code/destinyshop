package com.gidcode.destinyshop.service.user;

import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.request.CreateUserRequest;
import com.gidcode.destinyshop.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}
