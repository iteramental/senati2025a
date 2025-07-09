package emp.srv;

import java.io.IOException;

import emp.jpa.VendedorJPA;
import emp.uti.Utilidades;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistroVendedores
 */
@WebServlet("/RegistroVendedores")
public class RegistroVendedores extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistroVendedores() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Acá solo se deben hacer llamadas a funcionalidades encapsuladas en otras clases.
		// -------------------------------------------------------------------------------
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoWeb01");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			VendedorJPA objVendedor = new VendedorJPA();
			objVendedor.setNombre(request.getParameter("nombre"));
			objVendedor.setDireccion(request.getParameter("direccion"));
			objVendedor.setTipoDocumento(Utilidades.esNumerico(request.getParameter("tipodocumento"))? Integer.parseInt(request.getParameter("tipodocumento")):0);
			objVendedor.setNumeroDocumento(request.getParameter("numerodocumento"));
			em.persist(objVendedor);
			em.getTransaction().commit();

			request.setAttribute("nombre", "**" + objVendedor.getNombre());
			request.setAttribute("direccion", "**" + objVendedor.getDireccion());
			request.setAttribute("tipodocumento", "**" + objVendedor.getTipoDocumento());
			request.setAttribute("numerodocumento", "**" + objVendedor.getNumeroDocumento());

			request.getRequestDispatcher("formulario.jsp").forward(request, response);

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
