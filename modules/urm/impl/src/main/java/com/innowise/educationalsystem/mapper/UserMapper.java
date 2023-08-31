package com.innowise.educationalsystem.mapper;


import com.innowise.educationalsystem.dto.UserLoginDto;
import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserResponseDto entityToDto(User user);

    List<UserResponseDto> toDtoList(List<User> users);

    UserLoginDto entityToLoginDto(User user);

    User signUpDtoToEntity(UserSignUpRequestDto userSignUpRequestDto);
}
