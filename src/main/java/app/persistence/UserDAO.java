package app.persistence;

import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO extends DAO<User>{
    public static UserDAO instance;
        public static UserDAO getUserDAOInstance(EntityManagerFactory _emf){
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public User getById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(User.class, id);
        }  
    }
}
