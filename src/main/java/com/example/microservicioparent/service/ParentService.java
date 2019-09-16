package com.example.microservicioparent.service;

import com.example.microservicioparent.model.Parent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface ParentService {

    public Flux<Parent> findAll();

    public Mono<Parent> findById(String id);

    public Mono<Parent> save(Parent producto);

    public Mono<Void> delete(Parent producto);

    public Mono<Parent>findByDocument(String  document);



    public Mono<Parent>findFullName(String  name);


}
