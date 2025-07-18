package far.srv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/farmacia")
public class FarmaciaServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoWeb01");
            EntityManager em = emf.createEntityManager();

            if (em.isOpen()) {
                out.println("<h2>✅ Conexión exitosa con MySQL desde el módulo de Farmacia.</h2>");
            } else {
                out.println("<h2>❌ No se pudo abrir conexión.</h2>");
            }

            em.close();
            emf.close();
        } catch (Exception e) {
            out.println("<h2>⚠️ Error de conexión:</h2><pre>" + e.getMessage() + "</pre>");
        }
    }
}
