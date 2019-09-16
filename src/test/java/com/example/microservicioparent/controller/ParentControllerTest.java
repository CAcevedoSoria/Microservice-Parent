package com.example.microservicioparent.controller;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.service.ParentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ParentControllerTest {


    @Autowired
    private WebTestClient client;

    @Autowired
    private ParentService parentService;


    @Test
    public void create() {



        Parent parent = new Parent("123456","Hector","female",new Date(),"dni","07543124");

        client.post().uri("/api/v1.0/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(parent), Parent.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Parent.class);

    }


    @Test
    public void findAll() {

        client.get()
                .uri("/api/v1.0/parents")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Parent.class)
                .consumeWith(response -> {
                    List<Parent> parents = response.getResponseBody();
                    parents.forEach(p -> {
                        System.out.println(p.getFullName());
                    });

                    Assertions.assertThat(parents.size()>0).isTrue();
                });

    }


    @Test
    public void findById() {

        Parent parent = parentService.findById("123456").block();
        client.get()
                .uri("/api/v1.0" + "/{id}", Collections.singletonMap("id", parent.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Parent.class)
                .consumeWith(response -> {
                    Parent p = response.getResponseBody();
                    Assertions.assertThat(p.getId()).isNotEmpty();
                    Assertions.assertThat(p.getId().length()>0).isTrue();

                });

    }

    @Test
    public void update() {

        Parent parent = parentService.findFullName("Flor").block();


        Parent parentEdit = new Parent("123456","Luis","male",new Date(),"dni","7545555");

        client.put().uri("/api/v1.0" + "/{id}", Collections.singletonMap("id", parent.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(parentEdit), Parent.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("123456")
                ;



    }

    @Test
    public void eliminar() {

        Parent parent = parentService.findFullName("Ynes").block();
        client.delete()
                .uri("/api/v1.0" + "/{id}", Collections.singletonMap("id", parent.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();

        client.get()
                .uri("/api/v1.0" + "/{id}", Collections.singletonMap("id", parent.getId()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .isEmpty();
    }

    @Test
    public void findByDocument() {

        Parent parent = parentService.findByDocument("88888888").block();
        client.get()
                .uri("/api/v1.0" + "/document/{document}", Collections.singletonMap("document", parent.getDocument()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Parent.class)
                .consumeWith(response -> {
                    Parent p = response.getResponseBody();
                    Assertions.assertThat(p.getDocument()).isNotEmpty();
                    Assertions.assertThat(p.getDocument().length()>0).isTrue();

                });
    }

    @Test
    public void findByFullName() {

        Parent parent = parentService.findFullName("Hector").block();
        client.get()
                .uri("/api/v1.0" + "/name/{name}", Collections.singletonMap("name", parent.getFullName()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Parent.class)
                .consumeWith(response -> {
                    Parent p = response.getResponseBody();
                    Assertions.assertThat(p.getFullName()).isNotEmpty();
                    Assertions.assertThat(p.getFullName().length()>0).isTrue();

                });
    }
}