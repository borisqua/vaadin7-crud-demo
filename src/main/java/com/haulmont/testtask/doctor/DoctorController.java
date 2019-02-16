package com.haulmont.testtask.doctor;

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
@RequestMapping(method = GET, path = "/doctors")
public class DoctorController {
  
  private final DoctorRepository doctorRepository;
  
  @Autowired
  public DoctorController(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }
  
  @RequestMapping(method = GET, path = "/get")
  public @ResponseBody
  Optional<Doctor> getDoctor(@RequestParam(name = "id") Long id) {
    return doctorRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/add")
  public @ResponseBody
  Optional<Doctor> addDoctor(@RequestParam(name = "id") Long id) {
    return doctorRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/update")
  public @ResponseBody
  Optional<Doctor> updateDoctor(@RequestParam(name = "id") Long id) {
    return doctorRepository.findById(id);
  }
  
  @RequestMapping(method = GET, path = "/remove")
  public @ResponseBody
  Optional<Doctor> removeDoctor(@RequestParam(name = "id") Long id) {
    return doctorRepository.findById(id);
  }
  
/*  @RequestMapping(method = GET, path = "/filter")
  public @ResponseBody Iterable<Doctor> filter(@RequestParam(name="name", defaultValue="*") String name){
  
  }*/
  
  @GetMapping("/all")
  public @ResponseBody Iterable<Doctor> showAllDoctors(){
    return doctorRepository.findAll();
  }
  
}
