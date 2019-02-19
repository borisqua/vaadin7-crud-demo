package com.haulmont.testtask.prescription;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping(method = GET, path = "/prescriptions")
public class PrescriptionController {
  
  private final PrescriptionRepository prescriptionRepository;
  private static final Logger LOGGER = LogManager.getLogger();
  
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
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Iterable<Prescription> removePrescription(@RequestParam(name = "id") Long id) {
    try {
      Optional<Prescription> prescription = prescriptionRepository.findById(id);
      prescription.ifPresent(prescriptionRepository::delete);
      return prescriptionRepository.findAll();
    } catch (DataIntegrityViolationException e) {
      //todo>> consider how to handle this case
      return prescriptionRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<Prescription> filterPrescription(
    @RequestParam(name = "patientId", required = false) Long patientId,
    @RequestParam(name = "priority", required = false) String priority,
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@HaulmontLOG4J2:  filterPrescription -> {}", patientId);
    return prescriptionRepository.findByCustomCriteria(patientId, priority, pattern);
//    return null;
  }
  
}
