package com.uva.productservice.model;

import jakarta.persistence.*;
import lombok.*;

/** Product entity. All the fields have default values to avoid null pointer exceptions. */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Product {
  /**
   * Product identifier, it is generated automatically when the value is 0 and the product is saved.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Builder.Default
  long id = 0L;

  /** Name of the product. */
  @Builder.Default String name = "";

  /** Description of the product. */
  @NonNull @Builder.Default String description = "";

  /** Price of the product. */
  float price;
}
