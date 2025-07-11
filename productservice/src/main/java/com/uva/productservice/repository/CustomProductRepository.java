package com.uva.productservice.repository;

import com.uva.productservice.model.Product;
import java.util.List;

public interface CustomProductRepository {
  /**
   * Search for all the products that meet the specified criteria, parameters are joined by AND
   * condition. In case a parameter is null it is excluded from the query.
   *
   * @param name of the product to search for. If null or empty it is not included.
   * @param description of the product to search for. If null or empty it is not included.
   * @param price of the product to search for. If null it is not included.
   * @return the list of products that meet the criteria.
   */
  List<Product> findAllByCriteria(String name, String description, Float price);
}
