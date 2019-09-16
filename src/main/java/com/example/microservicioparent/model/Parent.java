package com.example.microservicioparent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    @Document(collection = "Parents")


    public class Parent {


        @Id
        private String id;
        private String fullName;
        private String gender;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date createAt;
        private String typeDocument;
        private String document;



    }

