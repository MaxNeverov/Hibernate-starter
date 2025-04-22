package com.mndev;

import com.mndev.entity.*;
import com.mndev.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
//import util.HibernateTestUtil;

import java.time.LocalDate;
import java.util.List;

public class HibernateMapTest {

    @Test
    void checkHQL1() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            //select * from users
            Query query = session.createQuery("select c.name from UserEntity u " +
                    "join u.company c " +
                    "where u.id = 3");
            System.out.println(query.list().iterator().next());

            int countRows = session.createQuery("update UserEntity u set u.role = 'ADMIN'").executeUpdate();

            //чтобы писать обычные sql запросы
            List<UserEntity> list = session.createNativeQuery("select * from users", UserEntity.class).list();
            System.out.println(list);


            session.getTransaction().commit();
        }
    }

//    @Test
//    void checkDockerTest() {
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//
//            session.beginTransaction();
//
//            Company com = Company.builder()
//                    .name("Aboba")
//                    .build();
//            session.save(com);
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = Profile.builder()
                        .language("ru")
                        .street("Konova 18")
                        .build();

                UserEntity user = UserEntity.builder()
                        .username("test55")
                        .build();

                profile.setUser(user);

                session.save(user);

                session.getTransaction().commit();
            }

        }
    }

    @Test
// Если OrhanRemoval - true, то можно удалить конкретный элемент из коллекции юзеров, к примеру по айди
    void checkOrhanRemoval() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();


                Company company = session.get(Company.class, 1);
                System.out.println("");

                company.getUsers().removeIf(user -> user.getId().equals(2L));
//                System.out.println(users.toString());

                session.getTransaction().commit();
            }

        }
    }

    @Test
    //получение компании у юзера
    void ManyToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Company company = session.get(UserEntity.class, 1).getCompany();
                System.out.println(company);

                session.getTransaction().commit();
            }
        }
    }
    @Test
    //получение всех юзеров у компании
    void oneToMany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                List<UserEntity> users = session.get(Company.class, 1).getUsers();
                System.out.println(users);

//                UserEntity user = session.get(UserEntity.class, 1);
//                System.out.println(user.toString());

                session.getTransaction().commit();
            }
        }
    }

    @Test

    void ManyToManySeparate() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                UserEntity user = session.get(UserEntity.class, 5);
                Chats chat = session.get(Chats.class, 1);

                UsersChats usersChat = UsersChats.builder()
                        .addBy(user.getUsername())
                        .build();
                usersChat.setUser(user);
                usersChat.setChat(chat);

                session.save(usersChat);

                session.getTransaction().commit();
            }
        }
    }

//    @Test
//    void checkManyToMany() {
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//            try (Session session = sessionFactory.openSession()) {
//                session.beginTransaction();
//
//                UserEntity user = session.get(UserEntity.class, 5);
//
//                user.getChats().clear();
//
////                Chats chats = Chats.builder()
////                        .name("dmdev")
////                        .build();
////
////                user.addChat(chats);
////
////                session.save(chats);
//
//
//                session.getTransaction().commit();
//            }
//        }
//    }

//    @Test
//    void ghjghj() {
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//            try (Session session = sessionFactory.openSession()) {
//                session.beginTransaction();
//
//
//                Company company = Company.builder()
//                        .name("Apple")
//                        .build();
//
//                UserEntity user = UserEntity.builder()
//                        .username("Sveta@123")
//                        .personalInfo(PersonalInfo.builder()
//                                .firstname("Sveta")
//                                .lastname("Svetova")
//                                .birthDate(LocalDate.of(1990, 1, 1))
//                                .build())
//                        .role(Role.USER)
//                        .build();
//
//                company.addUser(user);
//
//                //сам сохранит user тк у компании включен каскад
//                session.save(company);
//
//                session.getTransaction().commit();
//            }
//        }
//    }

    @Test
    //Добавление пользователя к существующей в таблице компании
    void addUserToCompany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                UserEntity user = UserEntity.builder()
                        .username("Petr@123")
                        .personalInfo(PersonalInfo.builder()
                                .firstname("Sveta")
                                .lastname("Svetova")
                                .birthDate(LocalDate.of(1990, 1, 1))
                                .build())
                        .role(Role.USER)
                        .company(session.get(Company.class, 3))
                        .build();

                session.save(user);

                session.getTransaction().commit();
            }
        }
    }

    @Test
    void addCompany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Company company = Company.builder()
                        .name("Google")
                        .build();

                session.save(company);

                session.getTransaction().commit();
            }
        }
    }

}
