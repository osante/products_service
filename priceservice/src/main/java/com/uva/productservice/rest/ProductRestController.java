package com.uva.productservice.rest;

import com.uva.productservice.model.Product;
import com.uva.productservice.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/products", "/products/"})
public class ProductRestController {
  private final ProductService productService;

  @Autowired
  public ProductRestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  List<Product> getProducts() {
    return productService.getProducts();
  }

  @GetMapping("/{id}")
  ResponseEntity<Product> getProduct(@PathVariable long id) {
    return ResponseEntity.of(productService.getProduct(id));
  }

  @PostMapping
  Product saveProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  @PutMapping("/{id}")
  Product updateProduct(@PathVariable long id, @RequestBody Product product) {
    return productService.saveProduct(product.toBuilder().id(id).build());
  }

  @DeleteMapping("/{id}")
  void deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
  }
}
