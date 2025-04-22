package com.mndev;

import com.mndev.converter.BirthdayConverter;
import com.mndev.entity.*;
import com.mndev.util.DataImporter;
import com.mndev.util.HibernateUtil;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Transactional
public class HibernateMap {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {

                DataImporter.importData(sessionFactory);

                session1.beginTransaction();


/// ///////////////////////////////////////////////////////////////////////////////////////
//
                Payment payment = session1.find(Payment.class, 1L);
                payment.setAmount(payment.getAmount() + 10);
/// ///////////////////////////////////////////////////////////////////////////////////////
                //создание графа для решения проблемы N+1 для одной сущности и коллекции
//                Map<String, Object> properties = Map.of(
//                        GraphSemantic.LOAD.getJakartaHintName(), session1.getEntityGraph("WithCompanyAndChat")
//                );
////
//                UserEntity user = session1.find(UserEntity.class, 1);
//                System.out.println(user.getCompany().getName());
//                System.out.println(user.getUsersChats().size());
//
//                //запрос с решением проблемы N+1
//                List<UserEntity> users = session1.createQuery("select u from UserEntity u " +
//                        "where 1 = 1 ", UserEntity.class)
//                        .setHint( GraphSemantic.LOAD.getJakartaHintName(), session1.getEntityGraph("WithCompanyAndChat"))
//                        .list();
//                users.forEach(u -> System.out.println(u.getUsersChats().size()));
//                users.forEach(u -> System.out.println(u.getCompany().getName()));


/// ///////////////////////////////////////////////////////////////////////////////////////
////                Company company = Company.builder()
////                        .name("Xiaomi")
////                        .build();
//                UserEntity user = UserEntity.builder()
//                        .username("admin3")
//                        .personalInfo(PersonalInfo.builder()
//                                .firstname("Petr")
//                                .lastname("Ivanov")
//                                .birthDate(LocalDate.of(1990, 1, 1))
//                                .build())
////                        .company(company)
//                        //добавление юзера к существующей компании с другими пользователями
//                        .company(session1.get(Company.class, 1))
//                        .build();
//
////                session1.save(company);
//                session1.save(user);
/// ///////////////////////////////////////////////////////////////////////////////////////
                session1.getTransaction().commit();
            }

        }
    }
}
