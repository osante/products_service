package com.uva.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.uva.productservice.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomProductRepositoryImplTest {

  @InjectMocks private CustomProductRepositoryImpl customProductRepository;

  @Mock private EntityManager entityManager;

  @Mock private CriteriaBuilder criteriaBuilder;

  @Mock private CriteriaQuery<Product> criteriaQuery;

  @Mock private Root<Product> root;

  @Mock private Predicate namePredicate;

  @Mock private Predicate descriptionPredicate;

  @Mock private Predicate pricePredicate;

  @Mock private Predicate combinedPredicate;

  @Mock private TypedQuery<Product> typedQuery;

  @BeforeEach
  void setup() {
    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    when(criteriaBuilder.createQuery(Product.class)).thenReturn(criteriaQuery);
    when(criteriaQuery.from(Product.class)).thenReturn(root);
    when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
  }

  @Test
  void testFindAllByCriteria_AllParamsPresent() {
    String name = "Chilaquiles";
    String description = "Con salsa verde";
    Float price = 10.90F;
    when(criteriaBuilder.equal(root.get("name"), name)).thenReturn(namePredicate);
    when(criteriaBuilder.equal(root.get("description"), description))
        .thenReturn(descriptionPredicate);
    when(criteriaBuilder.equal(root.get("price"), price)).thenReturn(pricePredicate);
    when(criteriaBuilder.and(namePredicate, descriptionPredicate, pricePredicate))
        .thenReturn(combinedPredicate);
    when(criteriaQuery.where(combinedPredicate)).thenReturn(criteriaQuery);
    List<Product> expectedProducts =
        List.of(Product.builder().name(name).description(description).price(price).build());
    when(typedQuery.getResultList()).thenReturn(expectedProducts);

    List<Product> products = customProductRepository.findAllByCriteria(name, description, price);

    assertThat(products).isEqualTo(expectedProducts);
  }

  @Test
  void testFindAllByCriteria_OnlyName() {
    String name = "Tacos al pastor";
    when(criteriaBuilder.equal(root.get("name"), name)).thenReturn(namePredicate);
    when(criteriaBuilder.and(namePredicate)).thenReturn(namePredicate);
    when(criteriaQuery.where(namePredicate)).thenReturn(criteriaQuery);
    when(typedQuery.getResultList())
        .thenReturn(List.of(Product.builder().name("Tacos al pastor").build()));

    List<Product> products = customProductRepository.findAllByCriteria(name, null, null);

    assertThat(products).isEqualTo(List.of(Product.builder().name("Tacos al pastor").build()));
  }

  @Test
  void testFindAllByCriteria_NoParams() {
    when(criteriaBuilder.and()).thenReturn(combinedPredicate); // No predicates
    when(criteriaQuery.where(combinedPredicate)).thenReturn(criteriaQuery);
    when(typedQuery.getResultList()).thenReturn(List.of(Product.builder().name("Pozole").build()));

    List<Product> products = customProductRepository.findAllByCriteria(null, null, null);

    assertThat(products).isEqualTo(List.of(Product.builder().name("Pozole").build()));
  }
}
