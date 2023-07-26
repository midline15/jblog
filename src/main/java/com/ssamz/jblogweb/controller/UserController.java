package com.ssamz.jblogweb.controller;

import com.ssamz.jblogweb.DTO.ResponseDTO;
import com.ssamz.jblogweb.DTO.UserDTO;
import com.ssamz.jblogweb.domain.RoleType;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.exception.JBlogException;
import com.ssamz.jblogweb.persistence.UserRepository;
import com.ssamz.jblogweb.security.UserDetailsImpl;
import com.ssamz.jblogweb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/auth/login")
    public String login() {
        return "system/login";
    }

    @GetMapping("/auth/insertUser")
    public String insertUser() {
        return "user/insertUser";
    }

    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(@Valid @RequestBody UserDTO userDTO,BindingResult bindingResult) {

        User user = modelMapper.map(userDTO, User.class);
        User findUser = userService.getUser(user.getUsername());

        if (findUser.getUsername() == null) {
            userService.insertUser(user);
            return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + " 가입 성공.");
        } else {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 회원입니다.");
        }
    }

    @GetMapping("/user/updateUser")
    public String updateUser() {
        return "user/updateUser";
    }

    @PutMapping("/user")
    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody User user, @AuthenticationPrincipal UserDetailsImpl principal) {
        principal.setUser(userService.updateUser(user));

        return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + " 수정 완료");
    }
}
