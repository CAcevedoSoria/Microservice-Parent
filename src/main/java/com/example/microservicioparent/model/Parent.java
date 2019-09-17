package com.example.microservicioparent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** The type Parent. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Parents")
public class Parent {

  @NotNull
  @Size(min = 3, max = 25)
  @Id private String id;
  private String fullName;
  private String gender;

  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private Date createAt;

  private String typeDocument;
  private String document;
}
