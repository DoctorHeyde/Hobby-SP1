package app.persistence;

import app.model.Hobby;
import app.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PersonDAO extends DAO<Person> {
    public static PersonDAO instanse;

    public static PersonDAO getUserDAOInstanse(EntityManagerFactory _emf) {
        if (instanse == null) {
            emf = _emf;
            instanse = new PersonDAO();
        }
        return instanse;
    }

    @Override
    public Person getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Person.class, id);
        }
    }

    public void savePerson(Person person) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        }

    }

    public void addHobbyToPerson(int personId, int hobbyId) {

        try (var em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person personFromDB = em.find(Person.class, personId);
            Hobby hobbyFromDB = em.find(Hobby.class, hobbyId);

            personFromDB.addHobbie(hobbyFromDB);

            em.merge(personFromDB);

            em.getTransaction().commit();
        }

    }
}
