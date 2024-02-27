package app.persistence;

import app.DTO.NumberOfUsersPerHobbyDTO;
import app.model.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HobbyDAO extends DAO<Hobby> {

    public static HobbyDAO instanse;

    public static HobbyDAO getHobbyDAOInstanse(EntityManagerFactory _emf) {
        if (instanse == null) {
            emf = _emf;
            instanse = new HobbyDAO();
        }
        return instanse;
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


}
