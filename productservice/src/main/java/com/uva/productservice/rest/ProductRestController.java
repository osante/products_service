package com.uva.productservice.rest;

import com.uva.productservice.model.Product;
import com.uva.productservice.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/products", "/products/"})
public class ProductRestController {
  private final ProductService productService;

  @Autowired
  public ProductRestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  List<Product> getProducts(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) Float price) {
    return productService.getProducts(name, description, price);
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
