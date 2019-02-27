package com.haulmont.testtask;

import com.haulmont.testtask.jpa.doctor.DoctorRepositoryTest;
import com.haulmont.testtask.jpa.patient.PatientRepositoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(value = {
  //  list of test classes included to the suit
  //  Class1Tests.class,
  //  Class2Tests.class,
  PatientRepositoryTest.class,
  DoctorRepositoryTest.class
  //  ...
})
public class ApplicationTestsSuit {
}
