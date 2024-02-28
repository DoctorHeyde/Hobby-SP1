package app.persistence;

import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public abstract class DAO<T extends Object> {
    protected static EntityManagerFactory emf;

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
    public T update(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } 
        return t;
    }

    public abstract T getById(int id);



    public void remove(T t){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        } 
    }

}
