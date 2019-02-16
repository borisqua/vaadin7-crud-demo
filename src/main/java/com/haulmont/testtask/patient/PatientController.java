package com.haulmont.testtask.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Controller
@RequestMapping(method = GET, path = "/patients")
public class PatientController {
  
  private final PatientRepository patientRepository;
  
  @Autowired
  public PatientController(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Patient> getPatient(@RequestParam(name = "id") Long id) {
    return patientRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Optional<Patient> addPatient(@RequestParam(name = "id") Long id) {
    return patientRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Optional<Patient> updatePatient(@RequestParam(name = "id") Long id) {
    return patientRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Optional<Patient> removePatient(@RequestParam(name = "id") Long id) {
    return patientRepository.findById(id);
  }
  
/*  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody Iterable<Patient> filter(@RequestParam(name="name", defaultValue="*") String name){
  
  }*/
  
  @GetMapping("/all")
  public @ResponseBody Iterable<Patient> showAllPatients(){
    return patientRepository.findAll();
  }
  
}
