package com.haulmont.testtask.jpa.doctor.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(method = GET, path = "/doctors/view")
public class DoctorHumanizedController {
  
  private final DoctorHumanizedRepository doctorRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(DoctorHumanizedRepository.class.getName());
  
  @Autowired
  public DoctorHumanizedController(DoctorHumanizedRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<DoctorHumanized> showAllPrescriptions() {
    try {
      return doctorRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<DoctorHumanized> getPrescription(@RequestParam(name = "id") Long id) {
    try {
      return doctorRepository.findById(id);
    } catch (Exception ignored) {
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<DoctorHumanized> filterPrescription(
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    
    Map<String, String> criteria = new HashMap<>();
    criteria.put("fullname", pattern);
    
    LOGGER.info("HaulmontLOG4J2:  filter doctors by pattern -> {}", pattern);
    
    return doctorRepository.findByCustomCriteria(DoctorHumanized.class, criteria);
  }
  
}
