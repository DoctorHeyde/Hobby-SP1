package app.controllers;

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
}
