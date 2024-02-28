package app.persistence;

import app.DTO.NumberOfUsersPerHobbyDTO;
import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HobbyDAOTest {

    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
    private static HobbyDAO hobbyDAO = HobbyDAO.getHobbyDAOInstance(emfTest);
    private static UserDAO userDAO = UserDAO.getUserDAOInstance(emfTest);
    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
    }

    @AfterEach
    void afterEach() {
        //Flush the database before each test
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Hobby h");
            query.executeUpdate();
            em.getTransaction().commit();
        }
    }

    @BeforeEach
    void setUp() {
        Hobby h1 = new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs);
        Hobby h2 = new Hobby("BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs);

        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");

        User u1 = new User("Lauritz", 12312312, zip, "Street1", "1tv",17);
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv",17);
        User u3 = new User("John doe", 60230304, zip, "Street2", "1tv",17);
        u1.addHobby(h1);
        u2.addHobby(h1);
        u3.addHobby(h2);
        //Persist user entities(Use save method from DAO if available)
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(zip);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.getTransaction().commit();
        }

        hobbyDAO.save(h1);
        hobbyDAO.save(h2);
    }

    @Test
    void getNumberOfUsersWithHobby() {
        assertEquals(2,hobbyDAO.getNumberOfUsersWithHobby(1));
        assertEquals(1,hobbyDAO.getNumberOfUsersWithHobby(2));
    }

    @Test
    void getNumberOfUsersPerHobby(){

        // Arrange
        String expectedName1 = "3d-printing";
        String expectedName2 = "BasketBall";

        int expectedNumber1 = 2;
        int expectedNumber2 = 1;

        // Act
        List<NumberOfUsersPerHobbyDTO> actualList = hobbyDAO.getNumberOfPersonsPerHobby();
        String actualName1 = actualList.get(0).getHobby().getName();
        String actualName2 = actualList.get(1).getHobby().getName();
        long actualNumber1 = actualList.get(0).getNumberOfUsers();
        long actualNumber2 = actualList.get(1).getNumberOfUsers();

        // Assert
        assertEquals(expectedName1, actualName1);
        assertEquals(expectedNumber1, actualNumber1);
        assertEquals(expectedName2, actualName2);
        assertEquals(expectedNumber2, actualNumber2);
    }
}