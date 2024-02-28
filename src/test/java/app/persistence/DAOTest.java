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

    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
    private static HobbyDAO hobbyDAO;
    private static UserDAO userDAO;
    private static ZipCodeDAO zipCodeDAO;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("testdb",true);
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
        ZipCode zip1 = new ZipCode(2400,"københavn","regionHovedstad","København");
        User user1 = new User("Valde",239239,zip1,"Møllegade","stue",1);
        Hobby hobby1 = new Hobby("Skak","wwww.wiki","Generel",Style.Educational_hobbies);


        zipCodeDAO.save(zip1);
        userDAO.save(user1);
        hobbyDAO.save(hobby1);

        ZipCode expectedZipcode;
        User expectedUser;
        Hobby expectedHobby;

        try(EntityManager em = emfTest.createEntityManager()){

            expectedZipcode = em.find(ZipCode.class, 2400);
            expectedHobby = em.find(Hobby.class, 3);
            expectedUser = em.find(User.class, 4);

            //Assery

            assertEquals(expectedZipcode.getCityName(), zip1.getCityName());

            assertEquals(expectedUser.getName(),user1.getName());

            assertEquals(expectedHobby.getName(),hobby1.getName());


        }
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
    }

    @Test
    void remove() {
    }
}