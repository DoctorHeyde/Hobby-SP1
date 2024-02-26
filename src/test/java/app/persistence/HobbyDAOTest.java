package app.persistence;

import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HobbyDAOTest {

    private static EntityManagerFactory emfTest;
    HobbyDAO hobbyDAO;
    UserDAO userDAO;
    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
        HobbyDAO hobbyDAO = HobbyDAO.getHobbyDAOInstance(emfTest);
        UserDAO userDAO = UserDAO.getUserDAOInstance(emfTest);
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
        hobbyDAO.save(new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs));
        hobbyDAO.save(new Hobby("BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs));

        User u1 = new User("Lauritz", 12312312, 2540, "Street1", "1tv",17);
        
    }

    @Test
    void getNumberOfUsersWithHobby() {


    }
}