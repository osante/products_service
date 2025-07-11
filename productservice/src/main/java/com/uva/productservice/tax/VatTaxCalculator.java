package com.uva.productservice.tax;

import com.uva.productservice.model.Product;

public class VatTaxCalculator implements TaxCalculator {

  private static final float VAT_FACTOR = 0.21f;

  @Override
  public float calculateTax(Product product) {
    return product.getPrice() * VAT_FACTOR;
  }
}
