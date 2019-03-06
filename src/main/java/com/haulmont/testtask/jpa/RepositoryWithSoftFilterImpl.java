package com.haulmont.testtask.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryWithSoftFilterImpl<T> implements RepositoryWithSoftFilter<T> {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryWithSoftFilterImpl.class.getName());
  
  @Override
  public List<T> findByCustomCriteria(Class<T> clazz, Map<String, String> criteria) {
    
    LOGGER.info("HaulmontLOG4J2: RepositoryWithSoftFilterImpl<T>");
    
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
    Root<T> root = criteriaQuery.from(clazz);
    List<Predicate> predicates = new ArrayList<>();
    
    for (Map.Entry<String, String> c : criteria.entrySet()) {
      String field = c.getKey();
      String criterion = c.getValue();
      if (criterion !=null && criterion.length() != 0) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.trim(criteriaBuilder.lower(
          root.get(field))), "%" + criterion.toLowerCase() + "%"));
      }
    }
    
    criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
    TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
    
    return query.getResultList();
  }
}
