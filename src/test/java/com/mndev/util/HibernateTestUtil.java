package com.mndev.util;

//import org.testcontainers.containers.PostgreSQLContainer;


//@UtilityClass
//public class HibernateTestUtil {
//
//    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
//
//    static {
//        postgreSQLContainer.start();
//    }
//
//    public static SessionFactory buildSessionFactory() {
//        Configuration configuration = HibernateUtil.buildConfig();
//        configuration.setProperty("hibernate.connection.url", postgreSQLContainer.getJdbcUrl());
//        configuration.setProperty("hibernate.connection.username", postgreSQLContainer.getUsername());
//        configuration.setProperty("hibernate.connection.password", postgreSQLContainer.getPassword());
//        configuration.configure("hibernate.cfg.xml");
//
//        return configuration.buildSessionFactory();
//    }
//}

