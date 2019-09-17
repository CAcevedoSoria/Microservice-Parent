package com.example.microservicioparent.repository;

import com.example.microservicioparent.model.Parent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/** The interface Parent repository. */
public interface ParentRepository extends ReactiveMongoRepository<Parent, String> {

  /**
   * Find by full name mono.
   *
   * @param name the name
   * @return the mono
   */
  Mono<Parent> findByFullName(String name);

  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  Mono<Parent> findByDocument(String document);
}
