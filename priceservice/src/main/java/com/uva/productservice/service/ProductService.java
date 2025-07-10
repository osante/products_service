package com.uva.productservice.service;

import com.uva.productservice.model.Product;
import com.uva.productservice.repository.ProductRepository;
import com.uva.productservice.tax.TaxCalculator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final TaxCalculator taxCalculator;

  @Autowired
  public ProductService(ProductRepository productRepository, TaxCalculator taxCalculator) {
    this.productRepository = productRepository;
    this.taxCalculator = taxCalculator;
  }

  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  public Optional<Product> getProduct(long id) {
    return productRepository.findById(id);
  }

  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  public void deleteProduct(long id) {
    productRepository.deleteById(id);
  }

  public float calculateTax(Product product) {
    return taxCalculator.calculateTax(product);
  }
}
