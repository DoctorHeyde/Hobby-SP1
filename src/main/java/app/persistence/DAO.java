package app.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public abstract class DAO<T extends Object> {
    public static EntityManagerFactory emf;

    public static void close(){
        emf.close();
    }

    public T save(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } 
        return t;
    }

    public abstract T getById(int id);
    
}
