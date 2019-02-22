package com.haulmont.testtask.jpa.prescription;

@SuppressWarnings("unused")
public enum Priority {
  UNSOLET {
    @Override
    public String toString() {
      return "Нормальный";
    }
  },
  CITO {
    @Override
    public String toString() {
      return "Срочный (Cito)";
    }
  },
  STATIM {
    @Override
    public String toString() {
      return "Немедленный (Statim)";
    }
  };
  
  public String getCaption() {
    return name();
  }
  
  public int getId() {
    return ordinal();
  }
}
  

