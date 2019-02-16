package com.haulmont.testtask;

import com.haulmont.testtask.doctor.Doctor;
import com.haulmont.testtask.doctor.DoctorRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("unused")
public class DoctorRepositoryTest {
  
  @Autowired
  private DoctorRepository doctorRepository;
  
  @Test
  public void whenFindingDoctorById_thenCorrect() {
    doctorRepository.save(new Doctor("John", "john@domain.com"));
    assertThat(doctorRepository.findById(1L)).isInstanceOf(Optional.class);
  }
  
  @Test
  public void whenFindingAllDoctors_thenCorrect() {
    doctorRepository.save(new Doctor("John", "john@domain.com"));
    doctorRepository.save(new Doctor("Julie", "julie@domain.com"));
    assertThat(doctorRepository.findAll()).isInstanceOf(List.class);
  }
}
