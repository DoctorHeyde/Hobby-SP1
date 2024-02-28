package app.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

public class UserDAOTest {
    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
    private static UserDAO userDAO = UserDAO.getUserDAOInstance(emfTest);
    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
    }

    @AfterAll
    public static void teardownAll(){
        ZipCodeDAO.close();
        UserDAO.close();
        HobbyDAO.close();
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
        
        
    }
    
    @Test
    public void getUserByPhoneNumber(){
        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");
        ZipCodeDAO zipCodeDAO = ZipCodeDAO.getZipCodeDAOInstanse(emfTest);
        zipCodeDAO.save(zip);
        User expected = new User("John doe", 60230304, zip, "Street2", "1tv",17);
        userDAO.save(expected);

        User actual = userDAO.getByPhoneNumber(60230304);

        assertEquals(expected, actual);
    }
}
