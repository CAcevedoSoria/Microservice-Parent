package com.example.microservicioparent.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/** The type Parent. */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Parents")

public class Parent  {

  @NotNull
  @Size(min = 3, max = 25)
  @Id
  private String id;

  @Size(min = 3, max = 25)
  @NotEmpty
  private String fullName;
  private String gender;
  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private Date createAt;
  private String typeDocument;
  private String document;

  public Parent(String fullName, String gender, Date createAt, String typeDocument, String document) {
    this.fullName = fullName;
    this.gender = gender;
    this.createAt = createAt;
    this.typeDocument = typeDocument;
    this.document = document;
  }



}
