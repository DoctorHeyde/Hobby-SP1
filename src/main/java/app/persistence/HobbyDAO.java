package app.persistence;

import app.model.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class HobbyDAO extends DAO<Hobby> {

    private static HobbyDAO instance;
    public static HobbyDAO getHobbyDAOInstance(EntityManagerFactory _emf){
        if (instance == null) {
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }

    @Override
    public Hobby getById(int id) {
       try(EntityManager em = emf.createEntityManager()){
            return em.find(Hobby.class, id);
        }  
    }

    //getNumberOfUsersWithHobby
    public int getNumberOfUsersWithHobby(int hobbyId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u JOIN u.hobbies h WHERE h.id = :hobbyId", Long.class);
            query.setParameter("hobbyId", hobbyId);
            return query.getSingleResult().intValue();
        }
    }
}
