package org.project.musicweb.service;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.project.musicweb.dto.UserDTO;
import org.project.musicweb.entity.RoleEntity;
import org.project.musicweb.entity.UserEntity;
import org.project.musicweb.repository.RoleRepository;
import org.project.musicweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StorageService storageService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.storageService = storageService;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toDTO(user);
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::toDTO);
    }

    public Long getUserIdFromUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserEntity::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    public UserDTO registerNewUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Assign default role
        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));

        user.getRoles().add(userRole);

        UserEntity saved = userRepository.save(user);
        return toDTO(saved);
    }


    public UserDTO addUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));

        user.getRoles().add(userRole);

        UserEntity saved = userRepository.save(user);
        return toDTO(saved);
    }


    public UserDTO updateUser(Long id, UserDTO updatedDTO, MultipartFile profilePic) throws IOException {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setUsername(updatedDTO.getUsername());
        existingUser.setEmail(updatedDTO.getEmail());

        // Handle profile picture update
        if (profilePic != null && !profilePic.isEmpty()) {
            String profilePicFileName = storageService.uploadFile(profilePic);
            existingUser.setProfilePic(profilePicFileName);
        }

        // Handle roles
        if (updatedDTO.getRoles() != null) {
            Set<RoleEntity> roleEntities = updatedDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roleEntities);
        }

        UserEntity updatedUser = userRepository.save(existingUser);
        return toDTO(updatedUser);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO toDTO(UserEntity user) {
        String signedUrl = StringUtils.isNotBlank(user.getProfilePic())
                ? storageService.generateSignedUrl(user.getProfilePic()) : null;

        return UserDTO.entityToDTO(user, signedUrl);
    }

    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        UserEntity user = (UserEntity) auth.getPrincipal();
        return toDTO(user);
    }


}
