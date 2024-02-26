package app.persistence;

import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO extends DAO<User>{
    private static UserDAO instanse;
        public static UserDAO getUserDAOInstanse(EntityManagerFactory _emf){
        if (instanse == null) {
            emf = _emf;
            instanse = new UserDAO();
        }
        return instanse;
    }

    @Override
    public User getById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(User.class, id);
        }  
    }
}
