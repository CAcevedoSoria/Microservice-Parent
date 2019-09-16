package com.example.microservicioparent.repository;

import com.example.microservicioparent.model.Parent;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface ParentRepository extends ReactiveMongoRepository<Parent, String> {

    Mono<Parent> findByFullName(String name);

    Mono<Parent> findByDocument(String document);







}
