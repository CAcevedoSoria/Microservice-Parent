package com.example.microservicioparent.service;

import com.example.microservicioparent.model.Parent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The interface Parent service. */
public interface ParentService {

  /**
   * Find all flux.
   *
   * @return the flux
   */
  public Flux<Parent> findAll();

  /**
   * Find by id mono.
   *
   * @param id the id
   * @return the mono
   */
  public Mono<Parent> findById(String id);

  /**
   * Save mono.
   *
   * @param producto the producto
   * @return the mono
   */
  public Mono<Parent> save(Parent producto);

  /**
   * Delete mono.
   *
   * @param producto the producto
   * @return the mono
   */
  public Mono<Void> delete(Parent producto);

  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  public Mono<Parent> findByDocument(String document);

  /**
   * Find full name mono.
   *
   * @param name the name
   * @return the mono
   */
  public Mono<Parent> findFullName(String name);
}
