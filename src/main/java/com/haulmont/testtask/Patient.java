package com.haulmont.testtask;

import javax.persistence.*;

@SuppressWarnings({"WeakerAccess","unused"})
@Entity
@Table(name="patients")
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String surname;
  private String patronymic;
  private String phone;
  
  public Patient(String name, String surname){
    this(name, surname, "", "");
  }
  public Patient(String name, String surname, String phone){
    this(name, surname, "", phone);
  }
  public Patient(String name, String surname, String patronymic, String phone) {
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.phone = phone;
  }
  
  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getSurname() {
    return surname;
  }
  
  public void setSurname(String surname) {
    this.surname = surname;
  }
  
  public String getPatronymic() {
    return patronymic;
  }
  
  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
}
