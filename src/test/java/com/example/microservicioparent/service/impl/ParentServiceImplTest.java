package com.example.microservicioparent.service.impl;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.repository.ParentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ParentServiceImplTest {
  @Mock private ParentRepository parentRepository;

  @InjectMocks private ParentServiceImpl parentService;

  @Test
  public void findAll() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentService.findAll()).thenReturn(Flux.just(parent));

    Flux<Parent> actual = parentService.findAll();

    assertResults(actual, parent);
  }

  @Test
  public void findById_when_id_exist() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.findById(parent.getId())).thenReturn(Mono.just(parent));
    Mono<Parent> actual = parentService.findById(parent.getId());

    assertResults(actual, parent);
  }

  @Test
  public void findById_when_ID_NO_exist() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.findById(parent.getId())).thenReturn(Mono.empty());
    Mono<Parent> actual = parentService.findById(parent.getId());

    assertResults(actual);
  }

  @Test
  public void save() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.save(parent)).thenReturn(Mono.just(parent));

    Mono<Parent> actual = parentService.save(parent);

    assertResults(actual, parent);
  }

  @Test
  public void delete() {

    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.delete(parent)).thenReturn(Mono.empty());
  }

  @Test
  public void findByDocument() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.findByDocument("736723727")).thenReturn(Mono.just(parent));

    Mono<Parent> actual = parentService.findByDocument("736723727");

    assertResults(actual, parent);
  }

  @Test
  public void findFullName() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.findByFullName("cristohper")).thenReturn(Mono.just(parent));

    Mono<Parent> actual = parentService.findFullName("cristohper");

    assertResults(actual, parent);
  }

  @Test
  public void findByDateBetween() {
    Parent parent = new Parent();
    parent.setFullName("cristohper");
    parent.setGender("male");
    parent.setBirthday(LocalDate.of(1965, 01, 25));
    parent.setTypeDocument("dni");
    parent.setDocument("736723727");

    when(parentRepository.findBybirthdayBetween(
            LocalDate.of(1600, 03, 02), LocalDate.of(2008, 01, 11)))
        .thenReturn(Flux.just(parent));

    Flux<Parent> actual =
        parentRepository.findBybirthdayBetween(
            LocalDate.of(1600, 03, 02), LocalDate.of(2008, 01, 11));

    assertResults(actual, parent);
  }

  private void assertResults(Publisher<Parent> publisher, Parent... expectedProducts) {
    StepVerifier.create(publisher).expectNext(expectedProducts).verifyComplete();
  }
}
