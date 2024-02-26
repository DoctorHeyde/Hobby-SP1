package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import app.config.HibernateConfig;
import app.controllers.UserControler;
import app.controllers.ZipCodeControler;
import app.model.User;
import app.persistence.UserDAO;
import app.persistence.ZipCodeDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby", false);

        ZipCodeControler zipCodeControler   = new ZipCodeControler(ZipCodeDAO.getZipCodeDAOInstanse(emf));
        UserControler userControler         = new UserControler(UserDAO.getUserDAOInstanse(emf));

        zipCodeControler.printAllZipsCity();
        
        // Uncomment below to insert the data into db
        // insertDB(emf);
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
