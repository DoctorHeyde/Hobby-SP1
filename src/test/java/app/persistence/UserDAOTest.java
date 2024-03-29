package app.persistence;

import app.DTO.UserDTO;
import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAOTest {
    private static EntityManagerFactory emfTest;
    private static UserDAO userDAO;

    @BeforeAll
    public static void setupAll(){
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
        userDAO = UserDAO.getUserDAOInstance(emfTest);
    }

    @AfterEach
    public void afterEach() {
        //Flush the database before each test
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hobby h").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE public.hobby_id_seq RESTART WITH 1").executeUpdate();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE public.hobbyuser_id_seq RESTART WITH 1").executeUpdate();
            em.createQuery("DELETE FROM ZipCode z").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @BeforeEach
    public void setUp() {

        Hobby h1 = new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs); //id 1
        Hobby h2 = new Hobby("BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs); //id 2

        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");

        User u1 = new User("Lauritz", 12312312, zip, "Street1", "1tv",17);
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv",17);
        User u3 = new User("John doe", 60230305, zip, "Street2", "1tv",17);
        u1.addHobby(h1);
        u1.addHobby(h2);
        u2.addHobby(h1);
        u3.addHobby(h2);

        try(var em = emfTest.createEntityManager()){
            em.getTransaction().begin();
            em.persist(zip);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.persist(h1);
            em.persist(h2);
            em.getTransaction().commit();
        }
    }


    @Test
    public void getUsersByZip(){

        // Arrange
        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");

        User u1 = new User("Lauritz", 12312312, zip, "Street1", "1tv",17);
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv",17);
        User u3 = new User("John doe", 60230305, zip, "Street2", "1tv",17);

        List<User> expected = new ArrayList<>();
        expected.add(u1);
        expected.add(u2);
        expected.add(u3);

        // Act
        List<User> actual = userDAO.getUsersByZip(2500);

        // Assert
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getPhoneNumber(), actual.get(2).getPhoneNumber());
    }
    @Test
    public void getUsersByHobby() {
        assertEquals(2, userDAO.getUsersByHobby(1).size());
        assertEquals(2, userDAO.getUsersByHobby(2).size());
    }

    @Test
    public void getUserByPhoneNumber(){
        User expected = userDAO.getById(3);
        User actual = userDAO.getByPhoneNumber(60230305);
        assertEquals(expected, actual);
    }
    
    @Test
    public void getPhoneNumber() {
        assertEquals(12312312, userDAO.getPhoneNumber(1));
        assertEquals(60230304, userDAO.getPhoneNumber(2));
    }

    @Test
    void getUsersHobbyCountByAddress() {

        Map<User, Integer> actualMap = userDAO.getUsersHobbyCountByAddress("Street1", 17);
        User actualUser = actualMap.keySet().stream().findFirst().get();
        int actualCount = actualMap.get(actualUser);
        assertEquals(2,actualCount);
        assertEquals(1,actualUser.getId());
    }

    @Test
    void getAllUserInfo() {

        //Arrange
        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv",17);

        Hobby h1 = new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs); //id 1

        String expectedName = u2.getName();
        int expectedNumber = u2.getPhoneNumber();
        Hobby expectedHobby = h1;


        //Act
        UserDTO actualUserDTO = userDAO.getAllUserInfo(2);

        String actualName = actualUserDTO.getName();
        int actualNumber = actualUserDTO.getPhoneNumber();
        List<Hobby> actualHobbies = actualUserDTO.getHobbies();

        //Assert
        assertEquals(expectedName, actualName);
        assertEquals(expectedNumber, actualNumber);
        assertEquals(expectedHobby.getName(), actualHobbies.get(0).getName());

    }
}
