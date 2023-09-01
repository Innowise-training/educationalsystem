package com.innowise.educationalsystem.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

public abstract class TestTask<IN, OUT, T extends TestSubtype> {
    IN in;

    OUT out;

    T subtype;
}

interface TestSubtype {

}

interface TranslationSubtype extends TestSubtype {

}

@Data
class PuzzleSubtype implements TranslationSubtype {

    private List<String> add;
}

/**
 * {"in": "Hello world", "out": ["Privet mir"], "name": "Trans", "subtype": {"name": "puzzle", "add": ["goodbye"]}}
 *
 */

class TranslationTask<T extends TranslationSubtype> extends TestTask<String, List<String>, T> {

}


@Data
class TypeDto {
    private Object in;
    private Object out;
    private String name;
    private SubtypeDto subtype;
}

@Data
class SubtypeDto {
    private Object add;
    private String name;
}

class MyMapper {

    TestTask<?, ?, ?> toEntity(TypeDto typeDto) {


        return null;
    }
}

class main {
    private static String obj = "{\"in\": \"Hello world\", \"out\": [\"Privet mir\"], \"type\": \"Trans\", \"subtype\": {\"name\": \"puzzle\", \"add\": [\"goodbye\"]}}";

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        TypeDto tmp = objectMapper.readValue(obj, TypeDto.class);

        TranslationTask<PuzzleSubtype> task = new TranslationTask<>();

        task.in = (String) tmp.getIn();
        task.out = (List<String>) tmp.getOut();
        task.subtype = new PuzzleSubtype();

        System.out.println(tmp);
    }
}