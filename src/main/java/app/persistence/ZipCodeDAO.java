package app.persistence;

import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ZipCodeDAO extends DAO<ZipCode> {
    private static ZipCodeDAO instanse;
        public static ZipCodeDAO getZipCodeDAOInstanse(EntityManagerFactory _emf){
        if (instanse == null) {
            emf = _emf;
            instanse = new ZipCodeDAO();
        }
        return instanse;
    }

    @Override
    public ZipCode getById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(ZipCode.class, id);
        }  
    }
    
}
