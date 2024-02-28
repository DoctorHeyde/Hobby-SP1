package app.persistence;

import app.DTO.NumberOfUsersPerHobbyDTO;
import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HobbyDAOTest {

    private static EntityManagerFactory emfTest;
    private static HobbyDAO hobbyDAO;
    @BeforeAll
    public static void setupAll(){
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
        hobbyDAO = HobbyDAO.getHobbyDAOInstance(emfTest);
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