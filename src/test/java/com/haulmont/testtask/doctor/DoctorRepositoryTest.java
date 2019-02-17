package com.haulmont.testtask.doctor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorRepositoryTest {
  
  @Autowired
  private DoctorRepository doctorRepository;
  
  @Test
  public void ifFoundById_successfully_then_OK() {
    doctorRepository.save(new Doctor("Вася", "Василёк"));
    assertThat(doctorRepository.findById(1L)).isInstanceOf(Optional.class);
  }
  
  @Test
  public void ifFoundAll_successfully_then_OK() {
    doctorRepository.save(new Doctor("John", "john@domain.com"));
    doctorRepository.save(new Doctor("Julie", "julie@domain.com"));
    assertThat(doctorRepository.findAll()).isInstanceOf(List.class);
  }
  
  @Test
  public void ifSaved_successfully_then_OK() {
    doctorRepository.save(new Doctor("Rose", "Challenger"));
    Doctor doctor = doctorRepository.findById(0L).orElseGet(()
      -> new Doctor("Rose", "Challenger"));
    assertThat(doctor.getName()).isEqualTo("Rose");
  }
}
