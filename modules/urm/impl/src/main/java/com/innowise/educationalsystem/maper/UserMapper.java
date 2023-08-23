package com.innowise.educationalsystem.maper;


import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto entityToDto(User user);
}
