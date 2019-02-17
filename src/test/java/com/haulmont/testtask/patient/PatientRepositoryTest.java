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
    patientRepository.save(new Patient("Вася", "Василёк"));
    assertThat(patientRepository.findById(1L)).isInstanceOf(Optional.class);
  }
  
  @Test
  public void ifFoundAll_successfully_then_OK() {
    patientRepository.save(new Patient("Коля", "Неугомонный"));
    patientRepository.save(new Patient("Юля", "Кроcсовкина"));
    assertThat(patientRepository.findAll()).isInstanceOf(List.class);
  }
  
  @Test
  public void ifSaved_successfully_then_OK() {
    patientRepository.save(new Patient("Азраил", "ЧЁрный"));
    Patient patient = patientRepository.findById(0L).orElseGet(()
      -> new Patient("Азраил", "ЧЁрный"));
    assertThat(patient.getName()).isEqualTo("Азраил");
  }
}
