package com.example.microservicioparent.service.impl;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.repository.ParentRepository;
import com.example.microservicioparent.service.ParentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ParentServiceImplTest {

	@Mock
	private ParentRepository parentRepository;

	@InjectMocks

	private ParentServiceImpl parentService;

	private  Parent parent;




	@Test
	public void findAll() {
		Parent parent = new Parent();
		parent.setFullName("cristohper");
		parent.setGender("male");
		parent.setCreateAt(new Date());
		parent.setTypeDocument("dni");
		parent.setDocument("736723727");

		when(parentService.findAll()).thenReturn(Flux.just(parent));

		Flux<Parent> actual = parentService.findAll();

		assertResults(actual, parent);


	}

	@Test
	public void findById_when_id_exist() {
		Parent parent2 = new Parent();
		parent2.setId("dekweowe");
		parent2.setFullName("cristohper");
		parent2.setGender("male");
		parent2.setCreateAt(new Date());
		parent2.setTypeDocument("dni");
		parent2.setDocument("736723727");

		when(parentRepository.findById(parent2.getId())).thenReturn(Mono.just(parent2));
		Mono<Parent> actual = parentService.findById(parent2.getId());

		assertResults(actual, parent2);
	}

	@Test
	public void findById_when_ID_NO_exist() {
		Parent parent2 = new Parent();
		parent2.setId("dekweowe");
		parent2.setFullName("cristohper");
		parent2.setGender("male");
		parent2.setCreateAt(new Date());
		parent2.setTypeDocument("dni");
		parent2.setDocument("736723727");

		when(parentRepository.findById(parent2.getId())).thenReturn(Mono.empty());
		Mono<Parent> actual = parentService.findById(parent2.getId());

		assertResults(actual);


	}



	@Test
	public void save() {
		Parent parent = new Parent();
		parent.setFullName("cristohper");
		parent.setGender("male");
		parent.setCreateAt(new Date());
		parent.setTypeDocument("dni");
		parent.setDocument("736723727");

		when(parentRepository.save(parent)).thenReturn(Mono.just(parent));

		Mono<Parent> actual = parentService.save(parent);

		assertResults(actual, parent);


	}

	@Test
	public void delete() {

		Parent parent2 = new Parent();
		parent2.setId("dekweowe");
		parent2.setFullName("cristohper");
		parent2.setGender("male");
		parent2.setCreateAt(new Date());
		parent2.setTypeDocument("dni");
		parent2.setDocument("736723727");

		when(parentRepository.delete(parent2)).thenReturn(Mono.empty());





	}

	@Test
	public void findByDocument() {
		Parent parent2 = new Parent();
		parent2.setId("dekweowe");
		parent2.setFullName("cristohper");
		parent2.setGender("male");
		parent2.setCreateAt(new Date());
		parent2.setTypeDocument("dni");
		parent2.setDocument("736723727");



		when(parentRepository.findByDocument("736723727")).thenReturn(Mono.just(parent2));

		Mono<Parent> actual = parentService.findByDocument("736723727");

		assertResults(actual,parent2);
	}

	@Test
	public void findFullName() {
		Parent parent2 = new Parent();
		parent2.setId("dekweowe");
		parent2.setFullName("cristohper");
		parent2.setGender("male");
		parent2.setCreateAt(new Date());
		parent2.setTypeDocument("dni");
		parent2.setDocument("736723727");


		when(parentRepository.findByFullName("cristohper")).thenReturn(Mono.just(parent2));

		Mono<Parent> actual = parentService.findFullName("cristohper");

		assertResults(actual,parent2);
	}

	private void assertResults(Publisher<Parent> publisher, Parent... expectedProducts) {
		StepVerifier
			.create(publisher)
			.expectNext(expectedProducts)
			.verifyComplete();
	}
}