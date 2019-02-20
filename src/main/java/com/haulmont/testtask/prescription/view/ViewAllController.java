package com.haulmont.testtask.prescription.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping(method = GET, path = "/prescriptions/view")
public class ViewAllController {
  
  private final ViewAllRepository prescriptionRepository;
  private static final Logger LOGGER = LogManager.getLogger();
  
  @Autowired
  public ViewAllController(ViewAllRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<ViewAll> showAllPrescriptions() {
    try {
      return prescriptionRepository.findAll();
    } catch (Exception ignored) {
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<ViewAll> getPrescription(@RequestParam(name = "id") Long id) {
    try {
      return prescriptionRepository.findById(id);
    } catch (Exception ignored) {
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<ViewAll> filterPrescription(
    @RequestParam(name = "patient", required = false) String patient,
    @RequestParam(name = "priority", required = false) String priority,
    @RequestParam(name = "pattern", required = false) String pattern
  ) {
    LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@HaulmontLOG4J2:  filterPrescription -> {}", patient);
    return prescriptionRepository.findByCustomCriteria(patient, priority, pattern);
//    return null;
  }
  
}
