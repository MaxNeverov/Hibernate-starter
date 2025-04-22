package com.mndev;

import com.mndev.converter.BirthdayConverter;
import com.mndev.entity.Birthday;
import com.mndev.entity.PersonalInfo;
import com.mndev.entity.Role;
import com.mndev.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
@Slf4j
public class HibernateLifeCycle {

    //@slf4g аннотация - аналог кода ниже
//    private static final Logger log = LoggerFactory.getLogger(HibernateLifeCycle.class);

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure("hibernate.cfg.xml");

        UserEntity user = UserEntity.builder()
                .username("admin2")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Petr")
                        .lastname("Ivanov")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build())
                .build();
        log.info("User entity is in transient state, object {}", user);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User save in persistent state: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, session1);

//            try (Session session2 = sessionFactory.openSession()) {
//                //составной первичный ключ
//                UserEntity key = UserEntity.builder()
//                        .username("admin2")
//                        .personalInfo(PersonalInfo.builder()
//                                .firstname("Petr")
//                                .lastname("Ivanov")
//                                .birthDate(new Birthday(LocalDate.of(1990, 1, 1)))
//                                .build())
//                        .build();
//
//                UserEntity user2 = session2.get(UserEntity.class, key);
//            }

        } catch (Exception e) {
            log.error("Exception occurred", e);
            throw e;
        }
    }
}
