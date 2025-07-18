package far.srv;

import far.jpa.FarmaceuticoJPA;
import far.imp.GestionFarmaceutico;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.persistence.*;

import java.io.IOException;

@WebServlet("/farmacia")
public class FarmaciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("ProyectoWeb01");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en POST: " + accion);

        if (accion == null || accion.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
            return;
        }

        switch (accion.toLowerCase()) {
            case "login":
                String usuario = request.getParameter("username");
                String password = request.getParameter("password");

                EntityManager em = emf.createEntityManager();
                try {
                    FarmaceuticoJPA farmaceutico = GestionFarmaceutico.validarUsuario(em, usuario, password);
                    if (farmaceutico != null) {
                        // Sesión y redirección
                        request.getSession().setAttribute("usuarioLogueado", farmaceutico.getUsuario());
                        request.getSession().setAttribute("rolUsuario", farmaceutico.getRol());

                        response.sendRedirect("dashboard_farmacia.html");
                    } else {
                        // Credenciales incorrectas
                    	response.sendRedirect("inicio_sesion.html?error=1");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error interno del servidor.");
                    request.getRequestDispatcher("inicio_sesion.html").forward(request, response);
                } finally {
                    em.close();
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST no reconocida: " + accion);
                
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("sesion".equals(accion)) {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("usuarioLogueado") != null) {
                String usuario = (String) session.getAttribute("usuarioLogueado");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write("{\"usuario\": \"" + usuario + "\"}");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No hay sesión activa");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción GET no reconocida: " + accion);
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
