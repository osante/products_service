package com.uva.productservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uva.productservice.model.Product;
import com.uva.productservice.repository.ProductRepository;
import com.uva.productservice.tax.VatTaxCalculator;
import java.util.List;
import java.util.Optional;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
  @Mock private ProductRepository productRepositoryMock;

  private final VatTaxCalculator vatTaxCalculator = new VatTaxCalculator();

  private ProductService productService;

  @BeforeEach
  void init() {
    productService = new ProductService(productRepositoryMock, vatTaxCalculator);
  }

  @Test
  void getProductsTest() {
    List<Product> expectedProducts =
        List.of(Product.builder().id(1L).build(), Product.builder().id(2L).build());
    when(productRepositoryMock.findAllByCriteria(null, null, null)).thenReturn(expectedProducts);

    List<Product> products = productService.getProducts(null, null, null);

    assertThat(products).isEqualTo(expectedProducts);
  }

  @Test
  void getProduct_productExists() {
    Product expectedProduct = Product.builder().id(3L).build();
    when(productRepositoryMock.findById(3L)).thenReturn(Optional.of(expectedProduct));

    Optional<Product> productOptional = productService.getProduct(3L);

    assertThat(productOptional.orElseThrow()).isEqualTo(expectedProduct);
  }

  @Test
  void getProduct_productDoesntExists() {
    when(productRepositoryMock.findById(3L)).thenReturn(Optional.empty());

    Optional<Product> productOptional = productService.getProduct(3L);

    assertThat(productOptional.isEmpty()).isTrue();
  }

  @Test
  void saveProductTest() {
    when(productRepositoryMock.save(Product.builder().name("Carrillada").build()))
        .thenReturn(Product.builder().id(2L).name("Carrillada").build());

    Product product = productService.saveProduct(Product.builder().name("Carrillada").build());

    assertThat(product).isEqualTo(Product.builder().id(2L).name("Carrillada").build());
  }

  @Test
  void deleteProductTest() {
    productService.deleteProduct(3L);

    verify(productRepositoryMock).deleteById(3L);
  }

  @Test
  void calculateTaxTest() {
    float tax = productService.calculateTax(Product.builder().price(10F).build());

    assertThat(tax).isCloseTo(2.1F, Offset.offset(0.005F));
  }
}
