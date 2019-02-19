package com.haulmont.testtask.patient;

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
public class PatientRepositoryTest {
  
  @Autowired
  private PatientRepository patientRepository;
  
  @Test
  public void ifFoundById_successfully_then_OK() {
    patientRepository.save(new Patient("Test1Name1", "Test1Surname1"));
    assertThat(patientRepository.findById(1L)).isInstanceOf(Optional.class);
  }
  
  @Test
  public void ifFoundAll_successfully_then_OK() {
    patientRepository.save(new Patient("Test2Name1", "Test2Surname1"));
    patientRepository.save(new Patient("Test2Name2", "Test2Surname2"));
    assertThat(patientRepository.findAll()).isInstanceOf(List.class);
  }
  
  @Test
  public void ifSaved_successfully_then_OK() {
    patientRepository.save(new Patient("Test3Name1", "Test3Surname1"));
    Patient patient = patientRepository.findById(0L).orElseGet(()
      -> new Patient("Test3Name2", "Test3Surname2"));
    assertThat(patient.getName()).isEqualTo("Test3Surname2");
  }
}
