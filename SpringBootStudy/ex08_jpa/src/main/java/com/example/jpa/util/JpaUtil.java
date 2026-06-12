package com.example.jpa.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
  private static EntityManagerFactory factory;

  public static void initFactory() {
    factory = Persistence.createEntityManagerFactory("jpa-learning");
  }

  public static EntityManager getEntityManager() {
    return factory.createEntityManager();
  }

  public static void closeFactory() {
    factory.close();
  }
}