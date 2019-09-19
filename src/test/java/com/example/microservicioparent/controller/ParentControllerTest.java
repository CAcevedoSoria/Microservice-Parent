package com.example.microservicioparent.controller;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.service.ParentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/** The type Parent controller test. */
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ParentControllerTest {

  @Autowired private WebTestClient client;

  @Autowired private ParentService parentService;

  /** Create. */
  @Test
  public void create() {

    Parent parent =
        new Parent("Humberto Acevedo Ponce", "male", LocalDate.of(1997, 03, 07), "dni", "398392632");

    client
        .post()
        .uri("/api/v1.0/parents")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(parent), Parent.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Parent.class);
  }

  /** Find all. */
  @Test
  public void findAll() {

    client
        .get()
        .uri("/api/v1.0/parents")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Parent.class)
        .consumeWith(
            response -> {
              List<Parent> parents = response.getResponseBody();
              parents.forEach(
                  p -> {
                    System.out.println(p.getFullName());
                  });

              Assertions.assertThat(parents.size() > 0).isTrue();
            });
  }

  /** Find by id. */
  @Test
  public void findById() {

    Parent parent = parentService.findById("5d83de882523ef89b5cd5ed8").block();
    client
        .get()
        .uri("/api/v1.0/parents" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parent.class)
        .consumeWith(
            response -> {
              Parent p = response.getResponseBody();
              Assertions.assertThat(p.getId()).isNotEmpty();
              Assertions.assertThat(p.getId().length() > 0).isTrue();
            });
  }

  /** Update. */
  @Test
  public void update() {

    Parent parent = parentService.findFullName("Diego Acevedo Soria").block();

    Parent parentEdit =
        new Parent("Alejandra Acevedo Soria", "male", LocalDate.of(2000, 01, 22), "dni", "dsdsee");

    client
        .put()
        .uri("/api/v1.0/parents" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(parentEdit), Parent.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody()
        .jsonPath("$.id")
        .isNotEmpty()
        .jsonPath("$.id")
        .isEqualTo("5d83e24636ec1860c022d39b");

  }
  /** Find by document. */
  @Test
  public void findByDocument() {

    Parent parent = parentService.findByDocument("28762786").block();
    client
        .get()
        .uri(
            "/api/v1.0/parents" + "/document/{document}",
            Collections.singletonMap("document", parent.getDocument()))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parent.class)
        .consumeWith(
            response -> {
              Parent p = response.getResponseBody();
              Assertions.assertThat(p.getDocument()).isNotEmpty();
              Assertions.assertThat(p.getDocument().length() > 0).isTrue();
            });
  }

  /** Find by full name. */
  @Test
  public void findByFullName() {

    Parent parent = parentService.findFullName("Flor Soria Carpio").block();
    client
        .get()
        .uri(
            "/api/v1.0/parents" + "/name/{name}",
            Collections.singletonMap("name", parent.getFullName()))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parent.class)
        .consumeWith(
            response -> {
              Parent p = response.getResponseBody();
              Assertions.assertThat(p.getFullName()).isNotEmpty();
              Assertions.assertThat(p.getFullName().length() > 0).isTrue();
            });
  }

  /** Eliminar. */
  @Test
  public void eliminar() {

    Parent parent = parentService.findById("5d83de882523ef89b5cd5ed7").block();
    client
        .delete()
        .uri("/api/v1.0/parents" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/api/v1.0/parents" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .isEmpty();
  }


}
