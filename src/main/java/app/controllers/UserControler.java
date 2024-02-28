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

    public void printPersonByAllInfo(){
        dao.getAllUserInfo().forEach(a -> System.out.println("Name: " + a.getName() + ", Phone number: " + a.getPhoneNumber() + ", Zip code: " + a.getZipCode() + ", Street name: " + a.getStreetName() + ", Floor: " + a.getFloor() + ", House number: " + a.getHouseNumber()));
    }
}
