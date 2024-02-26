package app.persistence;

import app.model.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class HobbyDAO extends DAO<Hobby> {

    public static HobbyDAO instanse;
        public static HobbyDAO getHobbyDAOInstanse(EntityManagerFactory _emf){
        if (instanse == null) {
            emf = _emf;
            instanse = new HobbyDAO();
        }
        return instanse;
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
            TypedQuery<Integer> query = em.createQuery("SELECT h.users.size FROM Hobby h WHERE h.id = :hobbyId", Integer.class);
            query.setParameter("hobbyId", hobbyId);
            return query.getSingleResult();
        }
    }
    
}
