package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.document.Subtype;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubtypeRepository extends MongoRepository<Subtype, String>, CrudRepository<Subtype, String> {
}
