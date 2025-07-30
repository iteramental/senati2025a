package far.imp;

import far.jpa.FarmaceuticoJPA;
import far.uti.HashGenerador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GestionFarmaceutico {

    public static void registrarFarmaceutico(HttpServletRequest request, HttpServletResponse response, EntityManager em) {
        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("username");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");

        boolean registrado = RegistrarFarmaceutico.registrar(em, nombre, usuario, password, rol);

        if (registrado) {
            request.setAttribute("mensaje", "Farmacéutico registrado correctamente.");
        } else {
            request.setAttribute("error", "Error al registrar el farmacéutico.");
        }
    }

    public static FarmaceuticoJPA validarUsuario(EntityManager em, String usuario, String password) {
        System.out.println(">> Validando usuario: " + usuario);
        try {
            TypedQuery<FarmaceuticoJPA> query = em.createQuery(
                "SELECT f FROM FarmaceuticoJPA f WHERE f.usuario = :usuario", FarmaceuticoJPA.class);
            query.setParameter("usuario", usuario);

            FarmaceuticoJPA farmaceutico = query.getSingleResult();
            System.out.println(">> Usuario encontrado. Hash almacenado: " + farmaceutico.getPasswordHash());
            boolean coincide = HashGenerador.verificarPassword(password, farmaceutico.getPasswordHash());
            System.out.println(">> ¿Contraseña coincide?: " + coincide);

            if (coincide) return farmaceutico;
        } catch (NoResultException e) {
            System.out.println(">> No se encontró usuario con ese nombre.");
        } catch (Exception e) {
            System.out.println(">> ERROR inesperado:");
            e.printStackTrace();
        }
        return null;
    }

    
    public static void modificarFarmaceutico(HttpServletRequest request, int id, EntityManager em) throws Exception {
        String nuevoNombre = request.getParameter("nombre");
        String nuevoUsuario = request.getParameter("usuario");
        String nuevoRol = request.getParameter("rol");
        String nuevaPass = request.getParameter("nueva_contrasena");

        FarmaceuticoJPA user = em.find(FarmaceuticoJPA.class, id);
        if (user == null) {
            throw new Exception("Farmacéutico no encontrado.");
        }

        user.setNombre(nuevoNombre);
        user.setUsuario(nuevoUsuario);
        user.setRol(nuevoRol);

        if (nuevaPass != null && !nuevaPass.trim().isEmpty()) {
            String nuevoHash = HashGenerador.generarHash(nuevaPass);
            user.setPasswordHash(nuevoHash);
        }

        em.merge(user);
    }
}