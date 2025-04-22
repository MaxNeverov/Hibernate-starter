package com.mndev.dao;

import com.mndev.entity.*;
import org.hibernate.Session;
import org.hibernate.query.criteria.*;

import java.util.Collections;
import java.util.List;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<UserEntity> findAll(Session session) {
        return session.createQuery("select u from UserEntity u", UserEntity.class).list();

//----------------------------------------------------------------------------------------------------
//        //Динамический запрос через Criteria
//        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
//
//        JpaCriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
//        JpaRoot<UserEntity> user = criteria.from(UserEntity.class);
//
//        criteria.select(user);
//
//        return session.createQuery(criteria).list();
//----------------------------------------------------------------------------------------------------

    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<UserEntity> findAllByFirstName(Session session, String firstName) {
        return session.createQuery("select u from UserEntity u " +
                        "where u.personalInfo.firstname = :firstName", UserEntity.class)
                .setParameter("firstName", firstName)
                .list();

//        //----------------------------------------------------------------------------------------------------
//        //Динамический запрос через Criteria
//        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
//        //какой класс возвращаем
//        JpaCriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
//        JpaRoot<UserEntity> user = criteria.from(UserEntity.class);
//
//        //используется зависимость annotationProcessor('org.hibernate.orm:hibernate-jpamodelgen:6.6.1.Final')
//        criteria.select(user).where(cb.equal(user.get(UserEntity_.personalInfo).get(PersonalInfo_.firstname), firstName));
//
//        return session.createQuery(criteria).list();
//        //----------------------------------------------------------------------------------------------------

    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<UserEntity> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select u from UserEntity u " +
                        "order by u.personalInfo.birthDate ASC " +
                        "limit :limit", UserEntity.class)
                .setParameter("limit", limit)
                .list();

        //----------------------------------------------------------------------------------------------------
//        //Динамический запрос через Criteria
//        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
//        //какой класс возвращаем
//        JpaCriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
//        JpaRoot<UserEntity> user = criteria.from(UserEntity.class);
//
//        //используется зависимость annotationProcessor('org.hibernate.orm:hibernate-jpamodelgen:6.6.1.Final')
//        criteria.select(user).orderBy(cb.asc(user.get(UserEntity_.personalInfo).get(PersonalInfo_.birthDate)));
//
//        return session.createQuery(criteria)
//                .setMaxResults(limit)
//                .list();
        //----------------------------------------------------------------------------------------------------
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<UserEntity> findAllByCompanyName(Session session, String companyName) {
        return session.createQuery("select u from UserEntity u " +
                                "join u.company c " +
                                "where c.name = :companyName",
                        UserEntity.class)
                .setParameter("companyName", companyName)
                .list();

        //----------------------------------------------------------------------------------------------------
//        //Динамический запрос через Criteria
//        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
//        //какой класс возвращаем
//        JpaCriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
//        JpaRoot<Company> company = criteria.from(Company.class);
//        JpaListJoin<Company, UserEntity> users = company.join(Company_.users);
//
//        //используется зависимость annotationProcessor('org.hibernate.orm:hibernate-jpamodelgen:6.6.1.Final')
//        criteria.select(users).where(cb.equal(company.get(Company_.name), companyName));
//
//        return session.createQuery(criteria).list();
//        //----------------------------------------------------------------------------------------------------

    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("select p from Payment p " +
                                "join p.receiver u " +
                                "join u.company c " +
                                "where c.name = :companyName " +
                                "order by u.personalInfo.firstname Asc, p.amount asc",
                        Payment.class)
                .setParameter("companyName", companyName)
                .list();

        //----------------------------------------------------------------------------------------------------
//        //Динамический запрос через Criteria
//        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
//        //какой класс возвращаем
//        JpaCriteriaQuery<Payment> criteria = cb.createQuery(Payment.class);
//        JpaRoot<Payment> payment = criteria.from(Payment.class);
//        JpaJoin<Payment, UserEntity> users = payment.join(Payment_.receiver);
//        JpaJoin<UserEntity, Company> companies = users.join(UserEntity_.company);
//
//        //используется зависимость annotationProcessor('org.hibernate.orm:hibernate-jpamodelgen:6.6.1.Final')
//        criteria.select(payment).where(cb.equal(companies.get(Company_.name), companyName))
//                .orderBy(
//                        cb.asc(users.get(UserEntity_.personalInfo).get(PersonalInfo_.firstname)),
//                        cb.asc(payment.get(Payment_.amount))
//                );
//
//        return session.createQuery(criteria).list();
        //----------------------------------------------------------------------------------------------------

    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("select avg(p.amount) from Payment p " +
                        "join p.receiver u " +
                        "where u.personalInfo.firstname = :firstName " +
                        "and u.personalInfo.lastname = :lastName", Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();
        //----------------------------------------------------------------------------------------------------
        //Динамический запрос через Criteria
        //----------------------------------------------------------------------------------------------------

    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {

//        List<Object[]> list = session.createNativeQuery("select c.name, round(avg(p.amount), 1) aa " +
//                "from company c " +
//                "join users u ON u.company_id = c.Id " +
//                "join payment p on u.id = p.receiver_id " +
//                "Group By c.name", Object[].class).list();
//        return list;

        return session.createQuery("select c.name, avg(p.amount) aa from Company c " +
                        "join c.users u " +
                        "join u.payments p " +
                        "group by c.name ", Object[].class)
                .list();
        //----------------------------------------------------------------------------------------------------
        //Динамический запрос через Criteria
        //----------------------------------------------------------------------------------------------------


    }



    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Object[]> isItPossible(Session session) {

                return session.createQuery("select u, avg(p.amount) from UserEntity u " +
                                "join u.payments p " +
                                "group by u " +
                                "having avg(p.amount) > (select  avg(p.amount) from Payment p)", Object[].class)
                .list();

        //----------------------------------------------------------------------------------------------------
        //Динамический запрос через Criteria
        //----------------------------------------------------------------------------------------------------

    }


    public static UserDao getInstance() {
        return INSTANCE;
    }
}
