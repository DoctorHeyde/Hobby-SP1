package app.persistence;

import app.model.Hobby;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO extends DAO<User> {
    public static UserDAO instanse;

    public static UserDAO getUserDAOInstanse(EntityManagerFactory _emf) {
        if (instanse == null) {
            emf = _emf;
            instanse = new UserDAO();
        }
        return instanse;
    }

    @Override
    public User getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public void saveUser(User user) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }

    }

    public void addHobbyToUser(int userId, int hobbyId) {

        try (var em = emf.createEntityManager()) {

            em.getTransaction().begin();
            User userFromDB = em.find(User.class, userId);
            Hobby hobbyFromDB = em.find(Hobby.class, hobbyId);

            userFromDB.addHobbie(hobbyFromDB);

            em.merge(userFromDB);

            em.getTransaction().commit();
        }

    }
}
