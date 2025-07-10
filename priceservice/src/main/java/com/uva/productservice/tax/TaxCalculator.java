package com.uva.productservice.tax;

import com.uva.productservice.model.Product;

@FunctionalInterface
public interface TaxCalculator {
  float calculateTax(Product product);

  enum TaxCalculatorType {
    VAT_TAX_CALCULATOR,
    ITBIS_TAX_CALCULATOR
  }
}
