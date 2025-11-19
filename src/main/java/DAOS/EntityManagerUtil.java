package DAOS;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author angel
 */

public class EntityManagerUtil {

    private static final EntityManagerFactory FACTORY;

    static {
        try {
            FACTORY = Persistence.createEntityManagerFactory("HospitalPU");
        } catch (Throwable ex) {
            System.err.println("Error al iniciar la creación de EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void close() {
        if (FACTORY != null && !FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
