package app.persistence;

import java.util.List;

import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class ZipCodeDAO extends DAO<ZipCode> {
    public static ZipCodeDAO instanse;
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

    public List<ZipCode> getAllZipCodes(){
        try(EntityManager em = emf.createEntityManager()){
            String sql = "FROM ZipCode";
            TypedQuery<ZipCode> zips =  em.createQuery(sql, ZipCode.class);
            return zips.getResultList();
        }
    }
    
}
