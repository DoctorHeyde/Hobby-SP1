package app.persistence;


import app.DTO.NumberOfUsersPerHobbyDTO;
import app.DTO.UserDto;
import app.model.Hobby;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;


public class UserDAO extends DAO<User>{
    private static UserDAO instance;
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

    public List<UserDto> getAllUserInfo(int id){
        try(var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT new app.DTO.UserDto(u.name, u.phoneNumber, u.zipCode, u.streetName, u.floor, u.houseNumber, h) " +
                            "FROM User u " +
                            "JOIN u.hobbies h JOIN u.zipCode z WHERE u.id = ?1",
                    UserDto.class).setParameter(1, id);

            return query.getResultList();
        }
    }

    public List<User> getUsersByZip(int zipCode){
        try(var em = emf.createEntityManager()){
            String sql = "SELECT u FROM User u JOIN u.zipCode z WHERE z.zip = :zipCode";
            TypedQuery<User> q = em.createQuery(sql, User.class);
            q.setParameter("zipCode", zipCode);
            return q.getResultList();
        }
    }

    public User getByPhoneNumber(int phoneNumber) {
        try(var em = emf.createEntityManager()){
            String sql = "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber";
            TypedQuery<User> q = em.createQuery(sql, User.class);
            q.setParameter("phoneNumber", phoneNumber);
            return q.getSingleResult();
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

    //14-us-10-as-a-user-i-want-to-see-all-people-on-an-address-with-a-count-on-how-many-hobbies-each-person-has-use-java-streams-for-this-one
    public Map<User, Integer> getUsersHobbyCountByAddress(String streetName, int houseNumber){
        try(var em = emf.createEntityManager()){
            String sql = "SELECT u FROM User u WHERE u.streetName = :streetName AND u.houseNumber = :houseNumber";
            TypedQuery<User> q = em.createQuery(sql, User.class);
            q.setParameter("streetName", streetName);
            q.setParameter("houseNumber", houseNumber);
            return q.getResultStream().collect(Collectors.toMap(user -> user, user -> user.getHobbies().size()));
        }
    }
}
