package com.haulmont.testtask.prescription;

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
@RequestMapping(method = GET, path = "/prescriptions")
public class PrescriptionController {
  
  private final PrescriptionRepository prescriptionRepository;
  
  @Autowired
  public PrescriptionController(PrescriptionRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Prescription> getPrescription(@RequestParam(name = "id") Long id) {
    return prescriptionRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Optional<Prescription> addPrescription(@RequestParam(name = "id") Long id) {
    return prescriptionRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Optional<Prescription> updatePrescription(@RequestParam(name = "id") Long id) {
    return prescriptionRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Optional<Prescription> removePrescription(@RequestParam(name = "id") Long id) {
    return prescriptionRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody
  Iterable<Prescription> filterPrescription(
    @RequestParam(name = "patient") Long patientId,
    @RequestParam(name = "priority", defaultValue = "") Long priority,
    @RequestParam(name = "description", defaultValue = "") Long inDescription
  ) {
    Iterable<Prescription> result;
    result = prescriptionRepository.findByPatientId(patientId);
    return result;
  }
  
  @GetMapping("/all")
  public @ResponseBody
  Iterable<Prescription> showAllPrescriptions() {
    return prescriptionRepository.findAll();
  }
  
}
