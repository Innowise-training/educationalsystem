package com.innowise.educationalsystem.dto;

import lombok.Data;

@Data
public class TypeDto {
    private Object in;

    private Object out;

    private String name;

    private SubtypeDto subtype;
}
