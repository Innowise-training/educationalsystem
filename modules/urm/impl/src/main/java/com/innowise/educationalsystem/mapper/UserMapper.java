package com.innowise.educationalsystem.mapper;


import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto entityToDto(User user);

    User signUpDtoToEntity(UserSignUpRequestDto userSignUpRequestDto);
}
