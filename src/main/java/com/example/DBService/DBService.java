package com.example.DBService;

import com.example.accounts.UserProfile;
import java.sql.*;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBService {
    private final SessionFactory sessionFactory;

    public DBService() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            String dbUrl = dotenv.get("DB_URL");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new RuntimeException("Не удалось загрузить настройки БД из .env файла");
            }

            Properties hibernateProps = new Properties();
            hibernateProps.setProperty("hibernate.connection.url", dbUrl);
            hibernateProps.setProperty("hibernate.connection.username", dbUser);
            hibernateProps.setProperty("hibernate.connection.password", dbPassword);
            hibernateProps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            hibernateProps.setProperty("hibernate.hbm2ddl.auto", "update");
            hibernateProps.setProperty("hibernate.show_sql", "true");

            Configuration config = new Configuration()
                    .setProperties(hibernateProps)
                    .addAnnotatedClass(UserProfile.class);

            this.sessionFactory = config.buildSessionFactory();

        } catch (Exception e) {
            throw new RuntimeException("Не удалось запустить Hibernate", e);
        }
    }

    public void addUser(UserProfile user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public UserProfile getUserByName(String name) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(UserProfile.class, name);
        } finally {
            session.close();
        }
    }

    public void close() throws SQLException {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}