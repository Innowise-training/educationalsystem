package com.innowise.educationalsystem.mapper;


import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = RoleMapper.class)
public interface UserMapper {
    UserResponseDto entityToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserByDto(UserSignUpRequestDto userSignUpRequestDto, @MappingTarget User user);

    List<UserResponseDto> mapToDtoList(List<User> userList);
}
