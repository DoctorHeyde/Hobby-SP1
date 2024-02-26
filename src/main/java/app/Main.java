package app;

import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Style;
import app.model.User;
import app.model.ZipCode;
import app.persistence.HobbyDAO;
import app.persistence.UserDAO;
import app.persistence.ZipCodeDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby", false);

        setupDB(emf);
    }

    private static void setupDB(EntityManagerFactory emf){
        ZipCode zip = new ZipCode(-100,"nowhere","nowhere","nowhere");
        Hobby hobby = new Hobby("name","link","cat",Style.Indendørs);
        User user = new User("name", 00000000, zip, "streetName", "1", 12);
        zip.addUser(user);
        user.addHobbie(hobby);



        UserDAO uDao = UserDAO.getUserDAOInstanse(emf);
        HobbyDAO hDao = HobbyDAO.getHobbyDAOInstanse(emf);
        ZipCodeDAO zDao = ZipCodeDAO.getZipCodeDAOInstanse(emf);

        user = uDao.save(user);
        hobby = hDao.save(hobby);
        zip = zDao.save(zip);

        hDao.remove(hobby);
        zDao.remove(zip);
        uDao.remove(user);
        
    }
}
