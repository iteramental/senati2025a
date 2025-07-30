package far.imp;

import far.jpa.FarmaceuticoJPA;
import far.uti.HashGenerador;
import jakarta.persistence.EntityManager;

public class RegistrarFarmaceutico {

    public static boolean registrar(EntityManager em, String nombre, String usuario, String password, String rol) {
        try {
            em.getTransaction().begin();

            FarmaceuticoJPA farmaceutico = new FarmaceuticoJPA();
            farmaceutico.setNombre(nombre);
            farmaceutico.setUsuario(usuario);
            farmaceutico.setPasswordHash(HashGenerador.generarHash(password));
            farmaceutico.setRol(rol);

            em.persist(farmaceutico);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}