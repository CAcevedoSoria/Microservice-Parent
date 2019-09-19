package com.example.microservicioparent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** The type Parent. */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Parents")
public class Parent {

  @Id private String id;

  @Size(min = 3, max = 25)
  @NotEmpty
  private String fullName;
  @NotEmpty
  private String gender;
  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private LocalDate birthday;
  @NotEmpty
  private String typeDocument;
  @NotEmpty
  @Size(min = 8 , max=8)
  private String document;

  /**
   * Instantiates a new Parent.
   *
   * @param fullName the full name
   * @param gender the gender
   * @param birthday the birthday
   * @param typeDocument the type document
   * @param document the document
   */
  public Parent(
      String fullName, String gender, LocalDate birthday, String typeDocument, String document) {
    this.fullName = fullName;
    this.gender = gender;
    this.birthday = birthday;
    this.typeDocument = typeDocument;
    this.document = document;
  }
}
