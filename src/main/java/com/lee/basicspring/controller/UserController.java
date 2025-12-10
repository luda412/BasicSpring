package com.lee.basicspring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.basicspring.data.dto.UserDto;
import com.lee.basicspring.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final Logger LOGGER = LoggerFactory.getLogger((UserController.class));

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        LOGGER.info("UserController Single user request Get user with id:{}", id);
        return userService.getUser(id);
        
    }

    @GetMapping
    public List<UserDto> getAllUsers() {

        LOGGER.info("UserController All users request Get all users{}", UserDto.class);
        return userService.getAllUsers();
    }


    @PostMapping
    public String createUser(@RequestBody UserDto dto) {
        LOGGER.info("UserController Create user request Create user with data:{}", dto);
        userService.createUser(dto);
        return "success";
    }
    
    

}
