package com.lee.basicspring.service.interfaces;

import java.util.List;

import com.lee.basicspring.data.dto.UserDto;

public interface UserService {
    
    UserDto getUser(Long id);
    
    List<UserDto> getAllUsers();
    
    UserDto createUser(UserDto dto);
}
