package com.mndev;


import com.mndev.converter.BirthdayConverter;
import com.mndev.entity.Birthday;
import com.mndev.entity.Role;
import com.mndev.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        Connection connection = DriverManager.getConnection("db.url", "db.username", "db.password");

        Configuration configuration = new Configuration();

        //Для соответствия названия столбцов sql (в sql названия через_ в маппинге camelCase)
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

//        тут возможно подключение сущностей, но лучше подключать их в XML
//        configuration.addAnnotatedClass(User.class);

        //добавление конвертора
        configuration.addAttributeConverter(new BirthdayConverter());

        //регистрация типа jsonb
//        configuration.registerTypeOverride(new JsonBinaryType());

        //путь к конфигу
        configuration.configure("hibernate.cfg.xml");

//        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//
//            session.beginTransaction();
///// /////////////////////////////////////////////////////////////////////////////////////
//            UserEntity user = UserEntity.builder()
//                    .username("admin1")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    //jsonb
////                    .info("{\"name\": \"Ivan\", " + "\"id\": \"25\"}")
//                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
//                    .role(Role.ADMIN)
//                    .build();

            //сохранение записи
//            session.save(user);

            //обновление записи(ошибка если нет строки)
//            session.update(user);

            //сохранение или обновление, если нет
//            session.saveOrUpdate(user);

            //удаление
//            session.delete(user);
/// //////////////////////////////////////////////////////////////////////////////////////
        //получение строки из sql
//            UserEntity user = session.get(UserEntity.class, "admin1");

//            session.getTransaction().commit();

        }

    }
//}
