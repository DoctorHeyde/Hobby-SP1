package app.persistence;


import app.model.Hobby;

import java.util.List;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;


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
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(User.class, id);
        }
    }


    public void addHobbyToUser(int userId, int hobbyId) {

        try (var em = emf.createEntityManager()) {

            em.getTransaction().begin();
            User userFromDB = em.find(User.class, userId);
            Hobby hobbyFromDB = em.find(Hobby.class, hobbyId);

            userFromDB.addHobby(hobbyFromDB);

            em.merge(userFromDB);

            em.getTransaction().commit();
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

    //As a user I want to get all persons with a given hobby
    public List<User> getUsersByHobby(int hobbyId){
        try(var em = emf.createEntityManager()){
            String sql = "SELECT u FROM User u JOIN u.hobbies h WHERE h.id = :hobbyId";
            TypedQuery<User> q = em.createQuery(sql, User.class);
            q.setParameter("hobbyId", hobbyId);
            return q.getResultList();
        }
    }

    //As a user I want to get a phone number from a given person
    public Integer getPhoneNumber(int userId){
        try(EntityManager em = emf.createEntityManager()){
            String sql = "SELECT u.phoneNumber FROM User u WHERE u.id = :userId";
            TypedQuery<Integer> q = em.createQuery(sql, Integer.class);
            q.setParameter("userId", userId);
            return q.getSingleResult();
        }
    }
}
