package app.persistence;

import java.util.List;

import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class UserDAO extends DAO<User>{
    public static UserDAO instanse;
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

    public List<User> getUsersByZip(int zipCode){
        try(var em = emf.createEntityManager()){
            String sql = "SELECT u FROM User u WHERE u.zipCode = :zipCode";
            TypedQuery<User> q = em.createQuery(sql, User.class);
            q.setParameter("zipCode", zipCode);
            return q.getResultList();
        }
    }
}
