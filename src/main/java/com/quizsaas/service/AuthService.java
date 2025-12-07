package com.quizsaas.service;

import com.quizsaas.dto.UserDTO;
import com.quizsaas.entity.Organization;
import com.quizsaas.entity.User;
import com.quizsaas.repository.OrganizationRepository;
import com.quizsaas.repository.UserRepository;
import com.quizsaas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        User user = userRepository.findByEmail(email).orElseThrow();
        return jwtUtil.generateToken(user.getEmail(), user.getId(), user.getOrganization().getId(), user.getRole());
    }

    public User register(UserDTO userDTO) {
        // Basic registration logic (e.g., for Org Admin)
        // In a real scenario, we might need to create Organization first or link to
        // existing one

        // For simplicity, assuming Organization exists or is handled separately
        // This method might need to be expanded based on specific registration flows

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());

        // Set organization if provided
        if (userDTO.getOrganizationId() != null) {
            Organization org = organizationRepository.findById(userDTO.getOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Organization not found"));
            user.setOrganization(org);
        }

        return userRepository.save(user);
    }
}
