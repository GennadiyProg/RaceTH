package ru.corporateproduct.raceth;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure().
                build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }

    public static void closeSessionFactory(){
        sessionFactory.close();
    }
}
