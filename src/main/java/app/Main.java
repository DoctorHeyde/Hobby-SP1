package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby", false);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.getTransaction().commit();
        }
    }
}
