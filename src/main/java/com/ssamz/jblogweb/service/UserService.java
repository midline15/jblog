package com.ssamz.jblogweb.service;

import com.ssamz.jblogweb.domain.OAuthType;
import com.ssamz.jblogweb.domain.RoleType;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User updateUser(User user) {
        User findUser = userRepository.findById(user.getId()).get();
        findUser.setUsername(user.getUsername());
        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
        findUser.setEmail(user.getEmail());

        return findUser;
    }
    @Transactional(readOnly = true)
    public User getUser(String username) {
        User findUser = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return findUser;
    }

    @Transactional
    public void insertUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        if (user.getOauth() == null) {
            user.setOauth(OAuthType.JBLOG);
        }
        userRepository.save(user);
    }
}
