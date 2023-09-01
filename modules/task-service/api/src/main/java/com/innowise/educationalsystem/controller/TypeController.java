package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.document.Ordering;
import com.innowise.educationalsystem.document.Translation;
import com.innowise.educationalsystem.document.Type;
import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.mapper.TypeMapper;
import com.innowise.educationalsystem.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/type")
public class TypeController {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    @GetMapping
    public Type temp() {
        ArrayList<String> correctAnswers = new ArrayList<>();
        correctAnswers.add("correct one text");

        HashSet<String> words = new HashSet<>();
        words.add("correct");
        words.add("text");
        words.add("one");

        return typeService.save(Ordering.builder()
                .words(words)
                .correctAnswers(correctAnswers)
                .build());
    }

    @GetMapping("/ttt")
    public List<Type> getTypes() {
        List<Type> all = typeService.findAll();
        //return all.stream().map(typeMapper::toDto).collect(Collectors.toList());
        return all;
    }
}
