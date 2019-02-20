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
public class DoctorFormRepositoryTest {
  
  @Autowired
  private DoctorRepository doctorRepository;
  
  @Test
  public void ifFoundById_successfully_then_OK() {
    doctorRepository.save(new Doctor("Test1Name1", "Test1Surname1"));
    assertThat(doctorRepository.findById(1L)).isInstanceOf(Optional.class);
  }
  
  @Test
  public void ifFoundAll_successfully_then_OK() {
    doctorRepository.save(new Doctor("Test2Name1", "Test2Surname1"));
    doctorRepository.save(new Doctor("Test2Name2", "Test2Surname2"));
    assertThat(doctorRepository.findAll()).isInstanceOf(List.class);
  }
  
  @Test
  public void ifSaved_successfully_then_OK() {
    doctorRepository.save(new Doctor("Test3Name1", "Test3Surname1"));
    Doctor doctor = doctorRepository.findById(0L).orElseGet(()
      -> new Doctor("Test3Name2", "Test3Surname2"));
    assertThat(doctor.getName()).isEqualTo("Test3Name2");
  }
}
