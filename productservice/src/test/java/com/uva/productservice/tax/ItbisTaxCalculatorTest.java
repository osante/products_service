package com.uva.productservice.tax;

import static org.assertj.core.api.Assertions.assertThat;

import com.uva.productservice.model.Product;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class ItbisTaxCalculatorTest {
  private final ItbisTaxCalculator itbisTaxCalculator = new ItbisTaxCalculator();

  @Test
  void calculateItbisTest() {
    float itbisTax = itbisTaxCalculator.calculateTax(Product.builder().price(10F).build());

    assertThat(itbisTax).isCloseTo(1.8F, Offset.offset(0.005F));
  }
}
