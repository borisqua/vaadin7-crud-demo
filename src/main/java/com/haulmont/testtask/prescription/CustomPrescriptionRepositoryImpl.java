package com.haulmont.testtask.prescription;

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
public class CustomPrescriptionRepositoryImpl implements CustomPrescriptionRepository {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  public List<Prescription> findByCustomCriteria(Long patientId, String priority, String pattern) {
    LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@HaulmontLOG4J2: Custom repository implementation: findByCriteria -> {}", patientId);
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Prescription> criteriaQuery = criteriaBuilder.createQuery(Prescription.class);
    
    Root<Prescription> prescription = criteriaQuery.from(Prescription.class);

//    LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@HaulmontLOG4J2: Custom repository implementation: findByCriteria result -> {}", resultList);
    
    List<Predicate> predicates = new ArrayList<>();
    if (patientId != null) {
      predicates.add(criteriaBuilder.equal(prescription.get("patientId"), patientId));
    }
    if (priority != null) {
      predicates.add(criteriaBuilder.equal(prescription.get("priority"), priority));
    }
    if (pattern != null) {
      predicates.add(criteriaBuilder.like(prescription.get("description"), "%" + pattern + "%"));
    }
//
//    criteriaQuery.select(prescription).where(predicates.toArray(new Predicate[]{}));
    
    criteriaQuery.select(prescription).where(predicates.toArray(new Predicate[]{}));
    
    TypedQuery<Prescription> query = entityManager.createQuery(criteriaQuery);
  
    return query.getResultList();
  }
}
