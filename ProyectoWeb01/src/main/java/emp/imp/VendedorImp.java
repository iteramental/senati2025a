package emp.imp;

import emp.jpa.VendedorJPA;
import emp.uti.Utilidades;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VendedorImp {

	public Boolean agregarVendedor(HttpServletRequest request, HttpServletResponse response) {
		System.out.print("entro en agregarVendedor");
		Boolean status=false;
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
			
			status=true;

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return status;
	}

}
