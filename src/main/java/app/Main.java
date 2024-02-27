package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import app.DTO.NumberOfPersonsPerHobbyDTO;
import app.config.HibernateConfig;
import app.model.Hobby;
import app.model.Person;
import app.model.Style;
import app.model.ZipCode;
import app.persistence.HobbyDAO;
import app.persistence.PersonDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby", false);

        //ZipCodeControler zipCodeControler = new ZipCodeControler(ZipCodeDAO.getZipCodeDAOInstanse(emf));

        //zipCodeControler.printAllZipsCity();

        ZipCode zip2400 = new ZipCode(2400, "København NV", "Københavns Kommune", "Region Hovedstaden");

        Person person1 = new Person("Tobias Rossen", 66934829, zip2400, "Bispebjerg Torv", "st tv", 6);
        Person person2 = new Person("Jonas Rossen", 77777777, zip2400, "Bispebjerg Torv", "st tv", 6);
        Person person3 = new Person("Finn Rossen", 88888888, zip2400, "Bispebjerg Torv", "st tv", 6);
        Person person4 = new Person("Lykke Andersen", 99999999, zip2400, "Bispebjerg Torv", "st tv", 6);


        PersonDAO personDAO = PersonDAO.getUserDAOInstanse(emf);
/*
        personDAO.savePerson(person1);
        personDAO.savePerson(person2);
        personDAO.savePerson(person3);
        personDAO.savePerson(person4);

 */
        personDAO.addHobbyToPerson(1, 4);
        personDAO.addHobbyToPerson(2, 4);
        personDAO.addHobbyToPerson(3, 4);

        personDAO.addHobbyToPerson(1, 5);
        personDAO.addHobbyToPerson(2, 5);

        personDAO.addHobbyToPerson(4, 6);


        HobbyDAO hobbyDAO = HobbyDAO.getHobbyDAOInstanse(emf);

        List<NumberOfPersonsPerHobbyDTO> personsPerHobby = hobbyDAO.getNumberOfPersonsPerHobby();

        personsPerHobby.forEach(System.out::println);
        
        // Uncomment below to insert the data into db
         //insertDB(emf);
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
