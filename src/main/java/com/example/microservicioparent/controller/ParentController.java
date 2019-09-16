package com.example.microservicioparent.controller;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.service.ParentService;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

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
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1.0")
@RestController
public class ParentController {

    @Autowired
    private ParentService  parentService;


    @PostMapping
     public Mono<ResponseEntity<Map<String, Object>>>
     create(@Valid @RequestBody Mono<Parent> parentmono) {

        Map<String, Object> respuesta = new HashMap<String, Object>();

        return parentmono.flatMap(parent -> {
            if (parent.getCreateAt() == null) {
                parent.setCreateAt(new Date());
            }

            return parentService.save(parent).map(p -> {
                respuesta.put("producto", p);
                respuesta.put("mensaje", "Producto creado con éxito");
                respuesta.put("timestamp", new Date());
                return ResponseEntity
                        .created(URI.create("/api/v1.0".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(respuesta);
            });

        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });

        });
    }
        @GetMapping("/parents")
        public Mono<ResponseEntity<Flux<Parent>>> findAll(){
            return Mono.just(
                    ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(parentService.findAll())
            );
        }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Parent>> findById(@PathVariable String id){
        return parentService.findById(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Parent>> update(@RequestBody Parent parent, @PathVariable String id){
        return parentService.findById(id).flatMap(p -> {
            p.setFullName(parent.getFullName());
            p.setGender(parent.getGender());
            p.setTypeDocument(parent.getTypeDocument());
            p.setDocument(parent.getDocument());

            return parentService.save(p);
        }).map(p->ResponseEntity.created(URI.create("/api/v1.0".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
        return parentService.findById(id).flatMap(p ->{
            return parentService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("document/{document}")
    public Mono<ResponseEntity<Parent>> findByDocument(@PathVariable String document){
        return parentService.findByDocument(document).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("name/{name}")
    public Mono<ResponseEntity<Parent>> findByFullName(@PathVariable String name){
        return parentService.findFullName(name).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}









