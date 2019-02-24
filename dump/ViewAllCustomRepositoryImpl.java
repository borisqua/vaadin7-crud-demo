package com.haulmont.testtask.jpa.prescription.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Service
public class ViewAllCustomRepositoryImpl implements ViewAllCustomRepository {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  @SuppressWarnings("Duplicates")
  public List<ViewAll> findByCustomCriteria(String patient, String priority, String pattern) {
    
    LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@HaulmontLOG4J2: Custom repository implementation: findByCriteria -> {}русский язык - кодировка utf8 - поциэнт", patient);
    
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<ViewAll> criteriaQuery = criteriaBuilder.createQuery(ViewAll.class);
    
    Root<ViewAll> prescription = criteriaQuery.from(ViewAll.class);
    
    List<Predicate> predicates = new ArrayList<>();
    if (patient != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.trim(criteriaBuilder.lower(
        prescription.get("patient"))), "%"+patient.toLowerCase()+"%"));
    }
    if (priority != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.trim(criteriaBuilder.lower(
        prescription.get("priority"))), "%"+priority.toLowerCase()+"%"));
    }
    if (pattern != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.trim(criteriaBuilder.lower(
        prescription.get("prescription"))), "%" + pattern.toLowerCase() + "%"));
    }
    
    criteriaQuery.select(prescription).where(predicates.toArray(new Predicate[]{}));
    
    TypedQuery<ViewAll> query = entityManager.createQuery(criteriaQuery);
    
    return query.getResultList();
  }
}
