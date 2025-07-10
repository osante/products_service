package com.uva.productservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.uva.productservice.model.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

  @LocalServerPort private int port;

  private String baseUrl;

  @Autowired private TestRestTemplate restTemplate;

  @BeforeEach
  void init() {
    baseUrl = "http://localhost:" + port + "/";
    restTemplate = restTemplate.withBasicAuth("user", "password");
  }

  @Test
  void contextLoads() {}

  @Test
  void integrationTest_listProducts() {
    List<Product> products =
        List.of(restTemplate.getForObject(baseUrl + "products", Product[].class));

    assertThat(products)
        .isEqualTo(
            List.of(
                Product.builder()
                    .id(1L)
                    .name("Chiles")
                    .description("Lata de 1 kg")
                    .price(10.5F)
                    .build(),
                Product.builder()
                    .id(2L)
                    .name("Tamales")
                    .description("3 bien sabrosos")
                    .price(1.5F)
                    .build(),
                Product.builder()
                    .id(3L)
                    .name("Tacos al pastor")
                    .description("5 con todo")
                    .price(15.5F)
                    .build(),
                Product.builder()
                    .id(4L)
                    .name("Chilaquiles")
                    .description("En salsa verde con pollo")
                    .price(11.5F)
                    .build()));
  }

  @Test
  void integrationTest_findProduct() {
    Product product = restTemplate.getForObject(baseUrl + "/products/2", Product.class);

    assertThat(product)
        .isEqualTo(
            Product.builder()
                .id(2L)
                .name("Tamales")
                .description("3 bien sabrosos")
                .price(1.5F)
                .build());
  }

  @Test
  void integrationTest_createProduct() {
    Product product =
        restTemplate.postForObject(
            baseUrl + "products",
            Product.builder().name("Bolt").price(10.12F).build(),
            Product.class);

    assertThat(product).isEqualTo(Product.builder().id(5L).name("Bolt").price(10.12F).build());
  }

  @Test
  void integrationTest_modifyProduct() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Product> requestEntity =
        new HttpEntity<>(
            Product.builder()
                .name("Tacos al pastor")
                .description("5 con todo, salsa, cebolla y cilantro")
                .price(10.12F)
                .build(),
            headers);

    Product product =
        restTemplate
            .exchange(baseUrl + "products/3", HttpMethod.PUT, requestEntity, Product.class)
            .getBody();

    assertThat(product)
        .isEqualTo(
            Product.builder()
                .id(3L)
                .name("Tacos al pastor")
                .description("5 con todo, salsa, cebolla y cilantro")
                .price(10.12F)
                .build());
  }

  @Test
  void integrationTest_deleteProduct() {
    restTemplate.delete(baseUrl + "products/1");

    ResponseEntity<Product> responseEntity =
        restTemplate.getForEntity(baseUrl + "products/1", Product.class);

    assertThat(responseEntity.getStatusCode().value()).isEqualTo(404);
  }
}
