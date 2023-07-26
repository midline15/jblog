package com.ssamz.jblogweb.service;

import com.ssamz.jblogweb.domain.RoleType;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(String username) {
        User findUser = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return findUser;
    }

    @Transactional
    public void insertUser(User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }
}
