package app.persistence;

import app.model.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
    
}
