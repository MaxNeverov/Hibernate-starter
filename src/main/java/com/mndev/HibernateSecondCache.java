package com.mndev;

import com.mndev.entity.Payment;
import com.mndev.entity.UserEntity;
import com.mndev.util.DataImporter;
import com.mndev.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class HibernateSecondCache {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

//            DataImporter.importData(sessionFactory);
            UserEntity user = null;

            try (Session session1 = sessionFactory.openSession()) {

                session1.beginTransaction();

//                user = session1.find(UserEntity.class, 1);
//                user.getCompany().getName();
//                user.getUsersChats().size();
//                UserEntity user1 = session1.find(UserEntity.class, 1);

                List<Payment> payment = session1.createQuery("select p from Payment p where receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1)
                        .setCacheable(true)
//                        .setCacheRegion("payment")
                        .getResultList();

                session1.getTransaction().commit();
            }

            try (Session session1 = sessionFactory.openSession()) {

                session1.beginTransaction();

//                UserEntity user2 = session1.find(UserEntity.class, 1);
//                user2.getCompany().getName();
//                user2.getUsersChats().size();

                List<Payment> payment2 = session1.createQuery("select p from Payment p where receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1)
                        .setCacheable(true)
//                        .setCacheRegion("payment")
                        .getResultList();

                session1.getTransaction().commit();
            }
        }


    }

}
