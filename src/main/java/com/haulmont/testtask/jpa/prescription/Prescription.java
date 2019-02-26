package com.haulmont.testtask.jpa.prescription;

import javax.persistence.*;
import java.time.LocalDate;

@SuppressWarnings({"unused"})
@Entity
@Table(name = "prescriptions")
public class Prescription {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="id")
  private Long id;
  @Column(name="description")
  private String description;
  @Column(name="patientid")
  private Long patientId;
  @Column(name="doctorid")
  private Long doctorId;
  @Column(name="creationdate")
  private LocalDate creationDate;
  @Column(name="validitylength")
  private Integer validityLength;
  @Column(name="priority")
  private String priority;
  
  public Prescription(){}
  public Prescription(String description, Long patientId, Long doctorId){
    this(description, patientId, doctorId, LocalDate.now(), 7, "Нормальынй");
  }
  public Prescription(String description, Long patientId, Long doctorId,
                      LocalDate creationDate, Integer validityLength, String priority) {
    this.description = description;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.creationDate = creationDate;
    this.validityLength = validityLength;
    this.priority = priority;
  }
  
  public Long getId() {
    return id;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Long getDoctorId() {
    return doctorId;
  }
  
  public void setDoctorId(Long doctorId) {
    this.doctorId = doctorId;
  }
  
  public Long getPatientId() {
    return patientId;
  }
  
  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }
  
  public LocalDate getCreationDate() {
    return creationDate;
  }
  
  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }
  
  public Integer getValidityLength() {
    return validityLength;
  }
  
  public void setValidityLength(Integer validityLength) {
    this.validityLength = validityLength;
  }
  
  public String getPriority() {
    return priority;
  }
  
  public void setPriority(String priority) {
    this.priority = priority;
  }
  
  @Override
  public String toString(){
    return "{" +
      "id: " + id +
      ", doctorId: " + doctorId +
      ", patientId: " + patientId +
      ", description: " + description +
      ", creationDate: " + creationDate +
      ", validityLength: " + validityLength +
      ", priority: " + priority + "}";
  }
}
