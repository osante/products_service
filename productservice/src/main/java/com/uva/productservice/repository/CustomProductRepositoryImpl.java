package com.uva.productservice.repository;

import com.uva.productservice.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomProductRepositoryImpl implements CustomProductRepository {

  private final EntityManager entityManager;

  @Autowired
  public CustomProductRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Product> findAllByCriteria(String name, String description, Float price) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
    Root<Product> root = criteriaQuery.from(Product.class);

    List<Predicate> predicates = new ArrayList<>();

    if (name != null && !name.trim().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("name"), name));
    }

    if (description != null && !description.trim().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("description"), description));
    }

    if (price != null) {
      predicates.add(criteriaBuilder.equal(root.get("price"), price));
    }

    criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}
