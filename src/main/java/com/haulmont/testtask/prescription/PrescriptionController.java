package com.haulmont.testtask.prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Controller
@RequestMapping(method = GET, path = "/prescriptions")
public class PrescriptionController {
  
  private EntityManager entityManager;
  
  private final PrescriptionRepository prescriptionRepository;
  
  @Autowired
  public PrescriptionController(PrescriptionRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<Prescription> showAllPrescriptions() {
    try {
      return prescriptionRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Prescription> getPrescription(@RequestParam(name = "id") Long id) {
    try {
      return prescriptionRepository.findById(id);
    } catch (Exception ignored) {
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Prescription addPrescription(@RequestParam(name = "description") String description,
                               @RequestParam(name = "patientId") Long patientId,
                               @RequestParam(name = "doctorId") Long doctorId,
                               @RequestParam(name = "creationDate", required = false) Date creationDate,
                               @RequestParam(name = "validityLength", required = false) Integer validityLength,
                               @RequestParam(name = "priority", required = false) String priority
  ) {
    try {
      if (creationDate == null) {
        creationDate = Date.valueOf(LocalDate.now());
      }
      if (validityLength == null) {
        validityLength = 7;
      }
      if (priority == null) {
        priority = "UNSOLET";
      }
      return prescriptionRepository.save(new Prescription(description, patientId, doctorId, creationDate, validityLength, priority));
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Prescription updatePrescription(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "description", required = false) String description,
                                  @RequestParam(name = "patientId", required = false) Long patientId,
                                  @RequestParam(name = "doctorId", required = false) Long doctorId,
                                  @RequestParam(name = "creationDate", required = false) Date creationDate,
                                  @RequestParam(name = "validityLength", required = false) Integer validityLength,
                                  @RequestParam(name = "priority", required = false) String priority
  ) {
    try {
      Optional<Prescription> p = prescriptionRepository.findById(id);
      Prescription prescription;
      if (p.isPresent()) {
        prescription = p.get();
        if (description != null) {
          prescription.setDescription(description);
        }
        if (patientId != null) {
          prescription.setPatientId(patientId);
        }
        if (doctorId != null) {
          prescription.setDoctorId(doctorId);
        }
        if (creationDate != null) {
          prescription.setCreationDate(creationDate);
        }
        if (validityLength != null) {
          prescription.setValidityLength(validityLength);
        }
        if (priority != null) {
          prescription.setPriority(priority);
        }
        return prescriptionRepository.save(prescription);
      } else {
        return null;
      }
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  void removePrescription(@RequestParam(name = "id") Long id) {
    try {
      Optional<Prescription> prescription = prescriptionRepository.findById(id);
      prescription.ifPresent(prescriptionRepository::delete);
    } catch (Exception ignored) {
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<Prescription> filterPrescription(
    @RequestParam(name = "patientId", required = false) Long patientId,
    @RequestParam(name = "priority", required = false) String priority,
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Prescription> criteriaQuery = criteriaBuilder.createQuery(Prescription.class);
    
    Root<Prescription> prescription = criteriaQuery.from(Prescription.class);
    
    Predicate[] restrictions = new Array(3);
    Predicate patientIdPredicate = criteriaBuilder.equal(prescription.get("patientId"), patientId);
    Predicate priorityPredicate = criteriaBuilder.equal(prescription.get("priority"), priority);
    
    return null;
  }
  
}
