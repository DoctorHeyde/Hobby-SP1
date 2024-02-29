package app.persistence;

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

import static org.junit.jupiter.api.Assertions.*;

class ZipCodeDAOTest {
    private static EntityManagerFactory emfTest;
    private static ZipCodeDAO zipCodeDAO;

    @BeforeAll
    public static void setupAll(){
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
        zipCodeDAO = ZipCodeDAO.getZipCodeDAOInstanse(emfTest);
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
        ZipCode zip1 = new ZipCode(2500, "Valby", "Nordsjælland", "København");
        ZipCode zip2 = new ZipCode(1233, "Johnby", "Jylland", "Århus");
        ZipCode zip3 = new ZipCode(4222, "Janeby", "Nordsjælland", "Janeby");

        try(var em = emfTest.createEntityManager()){
            em.getTransaction().begin();
            em.persist(zip1);
            em.persist(zip2);
            em.persist(zip3);
            em.getTransaction().commit();
        }
    }
    @Test
    void getAllZipCodes() {
        assertEquals(3, zipCodeDAO.getAllZipCodes().size());
    }
}