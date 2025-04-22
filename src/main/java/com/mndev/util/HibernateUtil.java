package com.mndev.util;

import com.mndev.converter.BirthdayConverter;
import com.mndev.entity.Audit;
import com.mndev.listener.AuditTableListener;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfig();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        registerListeners(sessionFactory);


        return sessionFactory;
    }

//    private static void registerListeners(SessionFactory sessionFactory) {
//        SessionFactoryImpl unwrap = sessionFactory.unwrap(SessionFactoryImpl.class);
//        EventListenerRegistry service = unwrap.getServiceRegistry().getService(EventListenerRegistry.class);
//        AuditTableListener auditTableListener = new AuditTableListener();
//        service.appendListeners(EventType.PRE_INSERT, auditTableListener);
//        service.appendListeners(EventType.PRE_DELETE, auditTableListener);
//    }

    public static Configuration buildConfig() {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter());
//        configuration.addAnnotatedClass(Audit.class);
        return configuration;
    }

}
