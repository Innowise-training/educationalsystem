package com.innowise.educationalsystem.mapper;


import com.innowise.educationalsystem.dto.JwtDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
    JwtDto rawTokenToDto(String token);
}
