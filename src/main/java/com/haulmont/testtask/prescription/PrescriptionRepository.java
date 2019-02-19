package com.haulmont.testtask.prescription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long>, PrescriptionRepositoryCustom {
//  Long countByDoctorId(Long doctorId);
//  Long countByDoctorIdAndPriority(Long doctorId, String priority);
//  Long countByDoctorIdAndDescriptionIsLike(Long doctorId, String pattern);
//  Long countByDoctorIdAndPriorityAndDescriptionIsLike(Long doctorId, String priority, String pattern);
}
