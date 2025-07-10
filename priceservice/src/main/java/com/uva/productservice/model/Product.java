package com.uva.productservice.model;

import jakarta.persistence.*;
import lombok.*;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NonNull
  @Builder.Default
  Long id = 0L;

  @Builder.Default String name = "";
  @NonNull @Builder.Default String description = "";
  float price;
}
