package com.haulmont.testtask.jpa.stats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
//@Controller
@Service
@RequestMapping(method = GET, path = "/results")
public class DoctorResultsController {
  
  private final DoctorResultsRepository doctorResultsRepository;
  private static final Logger LOGGER = LogManager.getLogger();
  
  @Autowired
  public DoctorResultsController(DoctorResultsRepository doctorResultsRepository) {
      this.doctorResultsRepository = doctorResultsRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<DoctorResult> showAllDoctors() {
    try {
      return doctorResultsRepository.findAll();
    } catch (Exception ignored){
      return null;
    }
  }
  
}
