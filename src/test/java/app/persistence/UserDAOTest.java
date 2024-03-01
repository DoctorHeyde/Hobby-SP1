package app.persistence;

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

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb", true);
    private static HobbyDAO hobbyDAO = HobbyDAO.getHobbyDAOInstance(emfTest);
    private static UserDAO userDAO = UserDAO.getUserDAOInstance(emfTest);

    @AfterEach
    void afterEach() {
        //Flush the database before each test
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Hobby h");
            query.executeUpdate();
            em.getTransaction().commit();
        }
    }

    @BeforeEach
    void setUp() {
        Hobby h1 = new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs); //id 1
        Hobby h2 = new Hobby("BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs); //id 2

        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");

        User u1 = new User("Lauritz", 12312312, zip, "Street1", "1tv", 17);
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv", 17);
        User u3 = new User("John doe", 60230304, zip, "Street2", "1tv", 17);
        u1.addHobby(h1);
        u2.addHobby(h1);
        u3.addHobby(h2);
        //Persist user entities(Use save method from DAO if available)
        try (EntityManager em = emfTest.createEntityManager()) {
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
    void getUsersByHobby() {
        assertEquals(2, userDAO.getUsersByHobby(1).size());
        assertEquals(1, userDAO.getUsersByHobby(2).size());
    }

    @Test
    void getAllUserInfo() {
        ZipCode zip = new ZipCode(2610, "Solrød Strand", "Nordsjælland", "København");
        User u4 = new User("Alex", 29790322, zip, "Street2", "2", 5);

        //Arrange
        String expectedName = "Alex";
        int expectedNumber = 29790322;
        ZipCode expectedZip = zip;
        String expectedStreet = u4.getStreetName();
        String expectedFloor = u4.getFloor();
        int expectedHouse = u4.getHouseNumber();

        //Act
        String actualName = u4.getName();
        int actualNumber = u4.getPhoneNumber();
        ZipCode actualZip = u4.getZipCode();
        String actualStreet = u4.getStreetName();
        String actualFloor = u4.getFloor();
        int actualHouse = u4.getHouseNumber();

        //Assert
        assertEquals(expectedName, actualName);
        assertEquals(expectedNumber, actualNumber);
        assertEquals(expectedZip, actualZip);
        assertEquals(expectedStreet, actualStreet);
        assertEquals(expectedFloor, actualFloor);
        assertEquals(expectedHouse, actualHouse);

    }
}