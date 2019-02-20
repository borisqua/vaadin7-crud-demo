package com.haulmont.testtask;

import com.haulmont.testtask.doctor.DoctorFormRepositoryTest;
import com.haulmont.testtask.patient.PatientRepositoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(value = {
  //  list of test classes included to the suit
  //  Class1Tests.class,
  //  Class2Tests.class,
  PatientRepositoryTest.class,
  DoctorFormRepositoryTest.class
  //  ...
})
public class ApplicationTestsSuit {
}
