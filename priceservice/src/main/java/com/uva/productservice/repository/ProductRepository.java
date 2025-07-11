package com.uva.productservice.repository;

import com.uva.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {}
