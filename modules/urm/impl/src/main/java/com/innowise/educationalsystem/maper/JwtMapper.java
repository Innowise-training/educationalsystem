package com.innowise.educationalsystem.maper;


import com.innowise.educationalsystem.dto.JwtDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
    JwtDto rawTokenToDto(String token);
}
