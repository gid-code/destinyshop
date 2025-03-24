package com.gidcode.destinyshop.service.user;

import com.gidcode.destinyshop.exception.AlreadyExistException;
import com.gidcode.destinyshop.exception.UserNotFoundException;
import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.repository.UserRepository;
import com.gidcode.destinyshop.request.CreateUserRequest;
import com.gidcode.destinyshop.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id "+userId+" not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(user.email()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.firstName());
                    user.setLastName(req.lastName());
                    user.setEmail(req.email());
                    user.setPassword(req.password());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistException("User with email "+request.email()+" already exist."));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.firstName());
            existingUser.setLastName(request.lastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new UserNotFoundException("User with id "+userId+" not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                () -> { throw new UserNotFoundException("User with id "+userId+" not found");}
                );
    }
}
