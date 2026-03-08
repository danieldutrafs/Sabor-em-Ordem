package br.com.sabor.util;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("SaborEmOrdemPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}