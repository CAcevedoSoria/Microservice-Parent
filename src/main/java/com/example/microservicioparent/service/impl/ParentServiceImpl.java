package com.example.microservicioparent.service.impl;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.repository.ParentRepository;
import com.example.microservicioparent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Parent service. */
@Service
public class ParentServiceImpl implements ParentService {

  @Autowired private ParentRepository parentRepository;

  @Override
  public Flux<Parent> findAll() {
    return parentRepository.findAll();
  }

  @Override
  public Mono<Parent> findById(String id) {
    return parentRepository.findById(id);
  }

  @Override
  public Mono<Parent> save(Parent parent) {
    return parentRepository.save(parent);
  }

  @Override
  public Mono<Void> delete(Parent parent) {
    return parentRepository.delete(parent);
  }

  @Override
  public Mono<Parent> findByDocument(String document) {
    return parentRepository.findByDocument(document);
  }

  @Override
  public Mono<Parent> findFullName(String name) {

    return parentRepository.findByFullName(name);
  }
}
