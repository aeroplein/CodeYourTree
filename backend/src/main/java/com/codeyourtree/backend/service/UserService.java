package com.codeyourtree.backend.service;

import com.codeyourtree.backend.model.TreeData;
import com.codeyourtree.backend.model.User;
import com.codeyourtree.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String username, String email, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("This username is already taken.");
        }

        if (userRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("This email is already registered.");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        TreeData initialTree = new TreeData();

        newUser.setTreeData(initialTree);
        initialTree.setUser(newUser);

        return userRepo.save(newUser);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username "
                + username));
    }

    public User loginUser(String username, String password) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Incorrect username or password."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect username or password.");
        }
        return user;
    }
}
