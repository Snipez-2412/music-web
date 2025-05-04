package org.project.musicweb.runner;


import org.project.musicweb.entity.RoleEntity;
import org.project.musicweb.entity.UserEntity;
import org.project.musicweb.repository.RoleRepository;
import org.project.musicweb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_USER")));
        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_ADMIN")));

        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setRoles(Set.of(userRole, adminRole));
            userRepository.save(adminUser);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            UserEntity regularUser = new UserEntity();
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("1234"));
            regularUser.setRoles(Set.of(userRole));
            userRepository.save(regularUser);
        }

        if (userRepository.findByUsername("huy").isEmpty()) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("huy");
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setRoles(Set.of(userRole, adminRole));
            userRepository.save(adminUser);
        }
    }
}
