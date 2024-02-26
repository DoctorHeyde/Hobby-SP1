package app.controllers;

import app.persistence.ZipCodeDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZipCodeControler {

    @NonNull
    private ZipCodeDAO dao;
    
    public void printAllZipsCity(){
        dao.getAllZipCodeds().forEach(c -> System.out.println("Zip code: " + c.getZip() + ", City name: " + c.getCityName()));
    }
}
