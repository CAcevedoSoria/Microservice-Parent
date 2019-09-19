package com.example.microservicioparent.repository;

import com.example.microservicioparent.model.Parent;
import java.time.LocalDate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
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

  /**
   * Find bybirthday between flux.
   *
   * @param date1 the date 1
   * @param date2 the date 2
   * @return the flux
   */
  Flux<Parent> findBybirthdayBetween(LocalDate date1, LocalDate date2);


}
