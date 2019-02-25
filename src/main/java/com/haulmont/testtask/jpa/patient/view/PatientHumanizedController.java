package com.haulmont.testtask.jpa.patient.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping(method = GET, path = "/patients/view")
public class PatientHumanizedController {
  
  private final PatientHumanizedRepository patientRepository;
  private static final Logger LOGGER = LogManager.getLogger();
  
  @Autowired
  public PatientHumanizedController(PatientHumanizedRepository patientRepository) {
    this.patientRepository = patientRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<PatientHumanized> showAllPrescriptions() {
    try {
      return patientRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<PatientHumanized> getPrescription(@RequestParam(name = "id") Long id) {
    try {
      return patientRepository.findById(id);
    } catch (Exception ignored) {
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<PatientHumanized> filterPrescription(
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    
    Map<String, String> criteria = new HashMap<>();
    criteria.put("fullname", pattern);
    
    LOGGER.info("@HaulmontLOG4J2:  filter patients by pattern -> {}", pattern);
    
    return patientRepository.findByCustomCriteria(PatientHumanized.class, criteria);
  }
  
}
