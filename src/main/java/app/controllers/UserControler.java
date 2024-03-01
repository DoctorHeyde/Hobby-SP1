package app.controllers;

import app.model.User;
import app.persistence.UserDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserControler {
    @NonNull
    private UserDAO dao;

    
    public void printPersonByZip(int zipCode){
        dao.getUsersByZip(zipCode).forEach(u -> System.out.println(u.getName()));
    }

    /*
    public void printPersonByAllInfo(){
        dao.getAllUserInfo().forEach(u -> System.out.println("Name: " + u.getName() + ", Phone number: " + u.getPhoneNumber() + ", Zip code: " + u.getZipCode() + ", Street name: " + u.getStreetName() + ", Floor: " + u.getFloor() + ", House number: " + u.getHouseNumber()));
    }

     */
}
