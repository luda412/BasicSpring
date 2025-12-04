package com.lee.basicspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lee.basicspring.data.dto.PetDto;
import com.lee.basicspring.data.dto.UserDto;
import com.lee.basicspring.data.entity.Pet;
import com.lee.basicspring.data.entity.User;
import com.lee.basicspring.repository.PetRepository;
import com.lee.basicspring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Override
    public UserDto createUser(UserDto dto) {
        User user = User.builder()
            .name(dto.getName())
            .age(dto.getAge())
            .gender(dto.getGender())
            .build();
        userRepository.save(user);
        
        if(dto.getPets() != null){
            for(PetDto petDto : dto.getPets()){
                Pet pet = Pet.builder()
                    .name(petDto.getName())
                    .type(petDto.getType())
                    .user(user)
                    .build();
                petRepository.save(pet);
            }
        }
        return convertToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }
    
    private UserDto convertToDto (User user){
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .gender(user.getGender())
            .pets(
                user.getPets().stream()
                    .map(pet -> PetDto.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .type(pet.getType())
                        .build()
                    )
                    .collect(Collectors.toList()   
            ))
            .build();
    }

}
