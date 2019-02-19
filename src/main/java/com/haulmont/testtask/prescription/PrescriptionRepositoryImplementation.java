package com.haulmont.testtask.prescription;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionRepositoryImplementation implements PrescriptionRepositoryCustom{
  
  private EntityManager entityManager = null;
  
  @Override
  public List<Prescription> findByPatientIdAndPriorityAndDescription(Long patientId, String priority, String  pattern) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Prescription> criteriaQuery = criteriaBuilder.createQuery(Prescription.class);

    Root<Prescription> prescription = criteriaQuery.from(Prescription.class);

    List<Predicate> predicates = new ArrayList<>();
    if(patientId != null){
      predicates.add(criteriaBuilder.equal(prescription.get("patientId"), patientId));
    }
    if(patientId != null){
      predicates.add(criteriaBuilder.equal(prescription.get("priority"), priority));
    }
    if(pattern!= null){
      predicates.add(criteriaBuilder.like(prescription.get("description"), "%" + pattern + "%"));
    }
    criteriaQuery.select(prescription).where(predicates.toArray(new Predicate[]{}));

    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}
