// far.imp.LoginHandler.java
package far.imp;

import far.jpa.FarmaceuticoJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.*;

public class LoginHandler implements FarmaciaAccionHandler {

    @Override
    public void procesar(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory emf) throws Exception {
        String usuario = request.getParameter("username");
        String password = request.getParameter("password");

        EntityManager em = emf.createEntityManager();

        try {
            FarmaceuticoJPA farmaceutico = GestionFarmaceutico.validarUsuario(em, usuario, password);
            if (farmaceutico != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", farmaceutico.getUsuario());
                session.setAttribute("rolUsuario", farmaceutico.getRol());
                response.sendRedirect("dashboard_farmacia.html");
            } else {
                response.sendRedirect("inicio_sesion.html?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("inicio_sesion.html?error=500");
        } finally {
            em.close();
        }
    }
}