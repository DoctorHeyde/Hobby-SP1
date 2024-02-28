package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import app.DTO.NumberOfUsersPerHobbyDTO;
import app.config.HibernateConfig;

import app.controllers.UserControler;
import app.controllers.ZipCodeControler;

import app.model.User;
import app.model.ZipCode;
import app.persistence.HobbyDAO;
import app.persistence.UserDAO;
import app.persistence.ZipCodeDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby", false);


        //ZipCodeControler zipCodeControler = new ZipCodeControler(ZipCodeDAO.getZipCodeDAOInstanse(emf));

        //zipCodeControler.printAllZipsCity();


        ZipCode zip2400 = new ZipCode(2400, "København NV", "Københavns Kommune", "Region Hovedstaden");

        User person1 = new User("Tobias Rossen", 66934829, zip2400, "Bispebjerg Torv", "st tv", 6);
        User person2 = new User("Jonas Rossen", 77777777, zip2400, "Bispebjerg Torv", "st tv", 6);
        User person3 = new User("Finn Rossen", 88888888, zip2400, "Bispebjerg Torv", "st tv", 6);
        User person4 = new User("Lykke Andersen", 99999999, zip2400, "Bispebjerg Torv", "st tv", 6);


        UserDAO userDAO = UserDAO.getUserDAOInstance(emf);

        userDAO.save(person1);
        userDAO.save(person2);
        userDAO.save(person3);
        userDAO.save(person4);


        userDAO.addHobbyToUser(1, 4);
        userDAO.addHobbyToUser(2, 4);
        userDAO.addHobbyToUser(3, 4);

        userDAO.addHobbyToUser(1, 5);
        userDAO.addHobbyToUser(2, 5);

        userDAO.addHobbyToUser(4, 6);


        HobbyDAO hobbyDAO = HobbyDAO.getHobbyDAOInstance(emf);

        List<NumberOfUsersPerHobbyDTO> usersPerHobby = hobbyDAO.getNumberOfPersonsPerHobby();

        usersPerHobby.forEach(System.out::println);


        ZipCodeDAO zipCodeDAO   = ZipCodeDAO.getZipCodeDAOInstanse(emf);

        //UserDAO userDAO         = UserDAO.getUserDAOInstance(emf);


        ZipCodeControler zipCodeControler   = new ZipCodeControler(zipCodeDAO);
        UserControler userControler         = new UserControler(userDAO);

        zipCodeControler.printAllZipsCity();

        // Uncomment below to insert the data into db
        // insertDB(emf);

        ZipCodeDAO.close();
        UserDAO.close();
        HobbyDAO.close();

    }


    public static void insertDB(EntityManagerFactory emf){
        StringBuilder sqlBuilder = new StringBuilder();
        try(Scanner hobbyScanner = new Scanner(new File("doc\\sql\\hobbyInsert.sql"))){
            try(Scanner zipCodeScanner = new Scanner(new File("doc\\sql\\zipCodeInsert.sql"))){
                while (hobbyScanner.hasNext()) {
                    sqlBuilder.append(hobbyScanner.nextLine());
                    sqlBuilder.append("\n");
                }
                while (zipCodeScanner.hasNext()) {
                    sqlBuilder.append(zipCodeScanner.nextLine());
                    sqlBuilder.append("\n");
                    
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try(var em = emf.createEntityManager()){
            String sql = sqlBuilder.toString();
            System.out.println(sql);
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
        }
    }
}
