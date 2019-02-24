package com.haulmont.testtask.jpa;

import java.util.List;
import java.util.Map;

public interface RepositoryWithSoftFilter<T> {
  
  List<T> findByCustomCriteria(Class<T> clazz, Map<String, String> criteria);
  
}
