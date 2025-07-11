package com.uva.productservice.tax;

import com.uva.productservice.model.Product;

public class ItbisTaxCalculator implements TaxCalculator {

  private static final float ITBIS_FACTOR = 0.18f;

  @Override
  public float calculateTax(Product product) {
    return product.getPrice() * ITBIS_FACTOR;
  }
}
