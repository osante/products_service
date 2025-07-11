package com.uva.productservice.service;

import com.uva.productservice.model.Product;
import com.uva.productservice.repository.ProductRepository;
import com.uva.productservice.tax.TaxCalculator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for the product operations. */
@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final TaxCalculator taxCalculator;

  /**
   * Constructor for the product service.
   *
   * @param productRepository for the storage operations.
   * @param taxCalculator to calculate the tax of the products.
   */
  @Autowired
  public ProductService(ProductRepository productRepository, TaxCalculator taxCalculator) {
    this.productRepository = productRepository;
    this.taxCalculator = taxCalculator;
  }

  /**
   * Get the all the products.
   *
   * @return a list of the products.
   */
  public List<Product> getProducts(String name, String description, Float price) {
    return productRepository.findAllByCriteria(name, description, price);
  }

  /**
   * Get a product by the id supplied. If the product doesn't exist an empty optional is returned.
   *
   * @param id of the product to find.
   * @return an Optional with the product, empty optional if the product doesn't exist.
   */
  public Optional<Product> getProduct(long id) {
    return productRepository.findById(id);
  }

  /**
   * Saves or update a product. To save a new product set the id to 0.
   *
   * @param product to save or update.
   * @return the saved or updated product.
   */
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  /**
   * Delete the product with the id passed.
   *
   * @param id of the product to delete.
   */
  public void deleteProduct(long id) {
    productRepository.deleteById(id);
  }

  /**
   * Calculate the tax with the tax calculator of this service.
   *
   * @param product to calculate the tax.
   * @return the value of the calculated tax.
   */
  public float calculateTax(Product product) {
    return taxCalculator.calculateTax(product);
  }
}
