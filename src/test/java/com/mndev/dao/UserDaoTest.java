package com.mndev.dao;

import com.mndev.entity.Payment;
import com.mndev.entity.UserEntity;
import com.mndev.util.HibernateUtil;
import com.mndev.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class UserDaoTest {

    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final UserDao userDao = UserDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserEntity> results = userDao.findAll(session);
        assertThat(results).hasSize(5);

        List<String> fullNames = results.stream().map(UserEntity::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bill Gates", "Steve Jobs", "Sergey Brin", "Tim Cook", "Diane Greene");

        session.getTransaction().commit();
    }

    @Test
    void findAllByFirstName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserEntity> results = userDao.findAllByFirstName(session, "Bill");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).fullName()).isEqualTo("Bill Gates");

        session.getTransaction().commit();
    }

    @Test
    void findLimitedUsersOrderedByBirthday() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 3;
        List<UserEntity> results = userDao.findLimitedUsersOrderedByBirthday(session, limit);
        assertThat(results).hasSize(limit);

        List<String> fullNames = results.stream().map(UserEntity::fullName).collect(toList());
        assertThat(fullNames).contains("Diane Greene", "Steve Jobs", "Bill Gates");

        session.getTransaction().commit();
    }

    @Test
    void findAllByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserEntity> results = userDao.findAllByCompanyName(session, "Google");
        assertThat(results).hasSize(2);

        List<String> fullNames = results.stream().map(UserEntity::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Sergey Brin", "Diane Greene");

        session.getTransaction().commit();
    }

    @Test
    void findAllPaymentsByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Payment> applePayments = userDao.findAllPaymentsByCompanyName(session, "Apple");
        assertThat(applePayments).hasSize(5);

        List<Integer> amounts = applePayments.stream().map(Payment::getAmount).collect(toList());
        assertThat(amounts).contains(250, 500, 600, 300, 400);

        session.getTransaction().commit();
    }

    @Test
    void findAveragePaymentAmountByFirstAndLastNames() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Double averagePaymentAmount = userDao.findAveragePaymentAmountByFirstAndLastNames(session, "Bill", "Gates");
        assertThat(averagePaymentAmount).isEqualTo(300.0);

        session.getTransaction().commit();
    }

    @Test
    void findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Object[]> results = userDao.findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session);
        assertThat(results).hasSize(3);

        List<String> orgNames = results.stream().map(a -> (String) a[0]).collect(toList());
        assertThat(orgNames).contains("Apple", "Google", "Microsoft");

//        List<BigDecimal> orgAvgPayments = results.stream().map(a ->   (BigDecimal) a[1]).collect(toList());
//        List<Double> collect = orgAvgPayments.stream().map(BigDecimal::doubleValue).collect(toList());
//        assertThat(collect).contains(410.0, 400.0, 300.0);

        List<Double> orgAvgPayments = results.stream().map(a ->   (Double) a[1]).collect(toList());
        assertThat(orgAvgPayments).contains(410.0, 400.0, 300.0);

        session.getTransaction().commit();
    }

    @Test
    void isItPossible() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Object[]> results = userDao.isItPossible(session);
        assertThat(results).hasSize(2);

        List<String> names = results.stream().map(r -> ((UserEntity) r[0]).fullName()).collect(toList());
        assertThat(names).contains("Sergey Brin", "Steve Jobs");

        List<Double> averagePayments = results.stream().map(r -> (Double) r[1]).collect(toList());
        assertThat(averagePayments).contains(500.0, 450.0);

        session.getTransaction().commit();
    }
}
