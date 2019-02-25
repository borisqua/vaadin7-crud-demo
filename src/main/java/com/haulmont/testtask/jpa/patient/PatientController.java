package com.haulmont.testtask.jpa.patient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping(method = GET, path = "/patients")
public class PatientController {
  
  private final PatientRepository patientRepository;
  private static final Logger LOGGER = LogManager.getLogger();
  
  @Autowired
  public PatientController(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<Patient> showAllPatients() {
    try {
      return patientRepository.findAll();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @GetMapping(path = "/names")
  public @ResponseBody
  Iterable<String> getAllPatientsNames() {
    try{
      return patientRepository.getAllPatientsNames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @GetMapping(path = "/surnames")
  public @ResponseBody
  Iterable<String> getAllPatientsSurnames() {
    try{
      return patientRepository.getAllPatientsSurnames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/fullnames")
  public @ResponseBody
  List<String> getAllFullNames(/*@RequestParam(name="pattern") String pattern*/){
    try{
      return patientRepository.getAllPatientsFullNames();
    } catch (Exception ignored){
      return null;
    }
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Patient> getPatient(@RequestParam(name = "id") Long id) {
    try {
      return patientRepository.findById(id);
    } catch (Exception ignored){
      return Optional.empty();
    }
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Patient addPatient(@RequestParam(name = "name") String name,
                     @RequestParam(name = "surname") String surname,
                     @RequestParam(name = "patronymic", defaultValue = "") String patronymic,
                     @RequestParam(name = "phone", defaultValue = "") String phone
  ) {
    try{
      return patientRepository.save(new Patient(name, surname, patronymic, phone));
    } catch (Exception ignored){
      return null;
    }
  }
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Patient updatePatient(@RequestParam(name = "id") Long id,
                        @RequestParam(name = "name", required = false) String name,
                        @RequestParam(name = "surname", required = false) String surname,
                        @RequestParam(name = "patronymic", required = false) String patronymic,
                        @RequestParam(name = "phone", required = false) String phone
  ) {
    try {
      Optional<Patient> p = patientRepository.findById(id);
      Patient patient;
      if (p.isPresent()) {
        patient = p.get();
        if (name != null) {
          patient.setName(name);
        }
        if (surname != null) {
          patient.setSurname(surname);
        }
        if (patronymic != null) {
          patient.setPatronymic(patronymic);
        }
        if (phone != null) {
          patient.setPhone(phone);
        }
        return patientRepository.save(patient);
      } else {
        return null;
      }
    } catch (Exception ignored){
      return null;
    }
  }
  
  @SuppressWarnings("Duplicates")
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Iterable<Patient> removePatient(@RequestParam(name = "id") Long id) {
    try {
      Optional<Patient> patient = patientRepository.findById(id);
      patient.ifPresent(patientRepository::delete);
      return patientRepository.findAll();
    } catch (DataIntegrityViolationException dataIntegrityError) {
      LOGGER.info("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE DELETING PATIENT ENTITY -> {}", dataIntegrityError);
      return patientRepository.findAll();
    } catch (Exception unknown) {
      LOGGER.info("HaulmontLOG4J2:  UNKNOWN ERROR WHILE DELETING PATIENT ENTITY -> {}", unknown);
      return patientRepository.findAll();
    }
  }
  
}
