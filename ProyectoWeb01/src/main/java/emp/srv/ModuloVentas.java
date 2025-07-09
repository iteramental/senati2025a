package emp.srv;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import emp.imp.VendedorImp;
import emp.imp.TiendaImp;
/**
 * Servlet implementation class RegistroVendedores
 */
@WebServlet("/ModuloVentas")
public class ModuloVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModuloVentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vcFormulario=request.getParameter("formulario");
		if (vcFormulario.equalsIgnoreCase("vendedor")) {
			TiendaImp tiendaImp = new TiendaImp();
			tiendaImp.cargarTiendas(request, response);
			request.getRequestDispatcher("vendedor.jsp").forward(request, response);
		}
		if (vcFormulario.equalsIgnoreCase("datosvendedor")) {
			VendedorImp vendedorImp = new VendedorImp();
			if (vendedorImp.agregarVendedor(request, response)) request.getRequestDispatcher("vendedor.jsp").forward(request, response);
		}
		if (vcFormulario.equalsIgnoreCase("datostienda")) {
			TiendaImp tiendaImp = new TiendaImp();
			if (tiendaImp.agregarTienda(request, response)) request.getRequestDispatcher("tienda.jsp").forward(request, response);
		}
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
