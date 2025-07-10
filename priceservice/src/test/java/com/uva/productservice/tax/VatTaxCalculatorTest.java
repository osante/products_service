package com.uva.productservice.tax;

import static org.assertj.core.api.Assertions.assertThat;

import com.uva.productservice.model.Product;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class VatTaxCalculatorTest {

  private final VatTaxCalculator vatTaxCalculator = new VatTaxCalculator();

  @Test
  void calculateVatTest() {
    float vatTax = vatTaxCalculator.calculateTax(Product.builder().price(12.54F).build());

    assertThat(vatTax).isCloseTo(2.63F, Offset.offset(0.005F));
  }
}
