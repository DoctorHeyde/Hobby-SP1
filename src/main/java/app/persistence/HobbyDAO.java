package app.persistence;

import app.DTO.NumberOfUsersPerHobbyDTO;
import app.model.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class HobbyDAO extends DAO<Hobby> {


    public static HobbyDAO instance;
        public static HobbyDAO getHobbyDAOInstance(EntityManagerFactory _emf){
        if (instance == null) {

            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }

    @Override
    public Hobby getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Hobby.class, id);
        }
    }



    public List<NumberOfUsersPerHobbyDTO> getNumberOfPersonsPerHobby() {

        try (var em = emf.createEntityManager()) {

            var query = em.createQuery("SELECT new app.DTO.NumberOfUsersPerHobbyDTO(h, COUNT(p)) FROM Hobby h " +
                            "LEFT JOIN h.users p GROUP BY h ORDER BY COUNT(p) DESC",
                    NumberOfUsersPerHobbyDTO.class);


            return query.getResultList();
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
