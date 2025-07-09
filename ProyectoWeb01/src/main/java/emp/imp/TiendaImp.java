package emp.imp;

import java.util.List;

import emp.jpa.TiendaJPA;
import emp.uti.Utilidades;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TiendaImp {

	public Boolean agregarTienda(HttpServletRequest request, HttpServletResponse response) {
		System.out.print("entro en agregarTienda");
		Boolean status=false;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoWeb01");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			TiendaJPA objTienda = new TiendaJPA();
			objTienda.setNombre(request.getParameter("nombre"));
			objTienda.setDireccion(request.getParameter("direccion"));
			objTienda.setUbigeo(request.getParameter("ubigeo"));
			em.persist(objTienda);
			em.getTransaction().commit();

			request.setAttribute("nombre", "**" + objTienda.getNombre());
			request.setAttribute("direccion", "**" + objTienda.getDireccion());
			request.setAttribute("ubigeo", "**" + objTienda.getUbigeo());
			
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

	
	public void cargarTiendas(HttpServletRequest request, HttpServletResponse response) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoWeb01");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			String vcJPQL = "SELECT t FROM Tienda t WHERE t.estado = true";
			
			List<TiendaJPA> tiendas = em.createQuery(vcJPQL, TiendaJPA.class).getResultList();
	        request.setAttribute("tiendas", tiendas);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
    }
}
