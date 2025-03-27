package com.gidcode.destinyshop.data;

import com.gidcode.destinyshop.model.Role;
import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.repository.RoleRepository;
import com.gidcode.destinyshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");

        createDefaultRolesIfNotExists(defaultRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User "+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++){
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin "+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
        }
    }

    private void createDefaultRolesIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRepository::save);
    }
}
