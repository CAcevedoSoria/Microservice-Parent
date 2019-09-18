package com.example.microservicioparent.controller;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** The type Parent controller. */
@RequestMapping("/api/v1.0/parents")
@RestController
public class ParentController {

  @Autowired private ParentService parentService;

  /**
   * Create mono.
   *
   * @param parent the parentmono
   * @return the mono
   */
  @PostMapping
  public Mono<ResponseEntity<Parent>> create(@RequestBody Parent parent) {

    return parentService.save(parent)
      .map(p -> ResponseEntity.created(URI.create("/api/v1.0".concat(p.getId())))
        .contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

  }

  /**
   * Find all mono.
   *
   * @return the mono
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Parent>>> findAll() {
    return Mono.just(
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(parentService.findAll()));
  }

  /**
   * Find by id mono.
   *
   * @param id the id
   * @return the mono
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Parent>> findById(@PathVariable String id) {
    return parentService
        .findById(id)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Update mono.
   *
   * @param parent the parent
   * @param id the id
   * @return the mono
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Parent>> update(@RequestBody Parent parent, @PathVariable String id) {
    return parentService
        .findById(id)
        .flatMap(
            p -> {
              p.setFullName(parent.getFullName());
              p.setGender(parent.getGender());
              p.setTypeDocument(parent.getTypeDocument());
              p.setDocument(parent.getDocument());

              return parentService.save(p);
            })
        .map(
            p ->
                ResponseEntity.created(URI.create("/api/v1.0".concat(p.getId())))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Eliminar mono.
   *
   * @param id the id
   * @return the mono
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
    return parentService
        .findById(id)
        .flatMap(
            p -> {
              return parentService
                  .delete(p)
                  .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
            })
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  @GetMapping("document/{document}")
  public Mono<ResponseEntity<Parent>> findByDocument(@PathVariable String document) {
    return parentService
        .findByDocument(document)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Find by full name mono.
   *
   * @param name the name
   * @return the mono
   */
  @GetMapping("name/{name}")
  public Mono<ResponseEntity<Parent>> findByFullName(@PathVariable String name) {
    return parentService
        .findFullName(name)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
