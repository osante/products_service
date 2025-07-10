package com.uva.productservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NonNull
  @Builder.Default
  Long id = 0L;

  @Builder.Default String name = "";
  @NonNull @Builder.Default String description = "";
  float price;
}
