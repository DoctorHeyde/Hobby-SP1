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

class DAOTest {

    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb", true);
    private static HobbyDAO hobbyDAO;
    private static UserDAO userDAO;
    private static ZipCodeDAO zipCodeDAO;


    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb", true);
        hobbyDAO = HobbyDAO.getHobbyDAOInstance(emfTest);
        userDAO = UserDAO.getUserDAOInstance(emfTest);
        zipCodeDAO = ZipCodeDAO.getZipCodeDAOInstanse(emfTest);
    }

    @BeforeEach
    void setUp() {
        Hobby h1 = new Hobby("3d-printing", "https://en.wikipedia.org/wiki/3D_printing", "Generel", Style.Indendørs);
        Hobby h2 = new Hobby("BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs);

        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");
        ZipCode zip2 = new ZipCode(2300, "Amagerbro", "Nordsjælland", "København");

        User u1 = new User("Lauritz", 12312312, zip, "Street1", "1tv", 17);
        User u2 = new User("Alberte", 60230304, zip, "Street2", "1tv", 17);
        User u3 = new User("John doe", 60230304, zip, "Street2", "1tv", 17);

        //Persist user entities(Use save method from DAO if available)
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(h1);
            em.persist(h2);
            em.persist(zip);
            em.persist(zip2);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.getTransaction().commit();
        }

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    void save() {
        ZipCode expectedZip = new ZipCode(2400, "københavn", "regionHovedstad", "København");
        User expectedUser = new User("Valde", 239239, expectedZip, "Møllegade", "stue", 1);
        Hobby expectedHobby = new Hobby("Skak", "wwww.wiki", "Generel", Style.Educational_hobbies);


        zipCodeDAO.save(expectedZip);
        userDAO.save(expectedUser);
        hobbyDAO.save(expectedHobby);

        ZipCode actualZipcode;
        User actualUser;
        Hobby actualHobby;

        try (EntityManager em = emfTest.createEntityManager()) {

            actualZipcode = em.find(ZipCode.class, 2400);
            actualHobby = em.find(Hobby.class, 3);
            actualUser = em.find(User.class, 4);

            //Assert
            assertEquals(actualZipcode.getCityName(), expectedZip.getCityName());
            assertEquals(actualUser.getName(), expectedUser.getName());
            assertEquals(actualHobby.getName(), expectedHobby.getName());
        }
    }


    @Test
    void update() {
        //Arrange
        ZipCode zip2 = new ZipCode(2300, "Christianshavn", "Nordsjælland", "København");

        //Act
        zipCodeDAO.update(zip2);
        ZipCode actualZipCode;

        try (EntityManager em = emfTest.createEntityManager()) {
            actualZipCode = em.find(ZipCode.class, 2300);


        //Assert
            assertEquals(zip2.getCityName(), actualZipCode.getCityName());

        }
    }

    @Test
    void getById() {

        // Act
        ZipCode zipCode = zipCodeDAO.getById(2500);
        User user = userDAO.getById(1);
        Hobby hobby = hobbyDAO.getById(1);

        // Assert
        assertEquals("Valby", zipCode.getCityName());
        assertEquals("Lauritz", user.getName());
        assertEquals("3d-printing", hobby.getName());


    }

    @Test
    void remove() {

        // Arrange
        Hobby h2 = new Hobby(2, "BasketBall", "https://en.wikipedia.org/wiki/basketball", "sport", Style.Udendørs);

        ZipCode zip2 = new ZipCode(2300, "Amagerbro", "Nordsjælland", "København");

        ZipCode zip = new ZipCode(2500, "Valby", "Nordsjælland", "København");
        User u1 = new User(1, "Lauritz", 12312312, zip, "Street1", "1tv", 17);


        // Act
        zipCodeDAO.remove(zip2);
        userDAO.remove(u1);
        hobbyDAO.remove(h2);

        ZipCode actualZipcode;
        User actualUser;
        Hobby actualHobby;

        try (EntityManager em = emfTest.createEntityManager()) {

            actualZipcode = em.find(ZipCode.class, 2300);
            actualHobby = em.find(Hobby.class, 2);
            actualUser = em.find(User.class, 1);}

        // Assert
        assertEquals(null, actualZipcode);
        assertEquals(null, actualUser);
        assertEquals(null, actualHobby);
    }
}