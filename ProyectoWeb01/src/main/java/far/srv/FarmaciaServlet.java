package far.srv;

import far.imp.LoginHandler;
import far.imp.RecetaDAO;
import far.imp.VentaDAO;
import far.jpa.FacturaEmitidaJPA;
import far.jpa.DetalleFacturaEmitidaJPA;
import far.jpa.PacienteJPA;
import far.jpa.RecetaCabeceraJPA;
import far.jpa.RecetaDetalleJPA;
import far.jpa.ProductoJPA;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;

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
        if (accion == null || accion.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
            return;
        }

        try {
            switch (accion.toLowerCase()) {
                case "login":
                    new LoginHandler().procesar(request, response, emf);
                    break;
                case "cerrar":
                    HttpSession sesion = request.getSession(false);
                    if (sesion != null) {
                        sesion.invalidate();
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "registrarventa":
                    registrarVenta(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST no reconocida: " + accion);
                case "registrarventasinreceta":
                    registrarVentaSinReceta(request, response);
                    break;
                    
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la acción.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Acción no especificada\"}");
        } 
        else if ("sesion".equalsIgnoreCase(accion)) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("usuarioLogueado") != null) {
                String usuario = (String) session.getAttribute("usuarioLogueado");
                String rol = (String) session.getAttribute("rolUsuario");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String json = String.format("{\"usuario\": \"%s\", \"rol\": \"%s\"}", usuario, rol);
                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Sesión no activa\"}");
            }
        } 
        else if ("buscarreceta".equalsIgnoreCase(accion)) {
            buscarReceta(request, response);
        } 
        else if ("buscarpaciente".equalsIgnoreCase(accion)) {
            buscarPaciente(request, response);
        } 
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Acción GET no reconocida\"}");
        }
    }

    private void registrarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            // Obtener datos de la venta desde el request
            int idReceta = Integer.parseInt(request.getParameter("idReceta"));
            // Aquí deberías obtener los detalles de la receta y crear la factura
            RecetaDAO recetaDAO = new RecetaDAO(em);
            RecetaCabeceraJPA receta = recetaDAO.buscarRecetaPorId(idReceta);
            
            if (receta == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Receta no encontrada.");
                return;
            }

            List<RecetaDetalleJPA> detalles = recetaDAO.obtenerDetallesReceta(idReceta);
            
            // Crear la factura
            FacturaEmitidaJPA factura = new FacturaEmitidaJPA();
            factura.setFechaEmision(new Date());
            factura.setPaciente(receta.getPaciente());
            factura.setTotal(calcularTotal(detalles)); // Implementa este método para calcular el total
            factura.setFormaPago(request.getParameter("formaPago"));
            factura.setEstadoPago("Pendiente");
            factura.setTipoVenta("Receta");

            // Registrar la venta
            VentaDAO ventaDAO = new VentaDAO(em);
            ventaDAO.registrarVenta(factura, detalles);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Venta registrada con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar la venta.");
        } finally {
            em.close();
        }
    }

    private BigDecimal calcularTotal(List<RecetaDetalleJPA> detalles) {
    	    BigDecimal total = BigDecimal.ZERO;
    	    for (RecetaDetalleJPA detalle : detalles) {
    	        BigDecimal subtotal = detalle.getProducto().getPrecio().multiply(new BigDecimal(detalle.getCantidad()));
    	        total = total.add(subtotal);
    	    }
    	    return total;  
	}
    
    private void buscarReceta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            int idReceta = Integer.parseInt(request.getParameter("idReceta"));
            RecetaDAO recetaDAO = new RecetaDAO(em);
            RecetaCabeceraJPA receta = recetaDAO.buscarRecetaPorId(idReceta);
            
            if (receta == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Receta no encontrada.");
                return;
            }
            // Convertir la receta a JSON y enviarla como respuesta
            String json = new Gson().toJson(receta); // Asegúrate de tener Gson en tus dependencias
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de receta inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al buscar la receta.");
        } finally {
            em.close();
        }
    }
    private void buscarPaciente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = null;
        try {
            // Configuración CORS
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            String dni = request.getParameter("dni");
            System.out.println("Buscando paciente con DNI: " + dni); // Log para depuración
            
            if (dni == null || dni.trim().isEmpty()) {
                response.getWriter().write("{\"error\":\"DNI no proporcionado\"}");
                return;
            }

            em = emf.createEntityManager();
            
            // Consulta optimizada
            PacienteJPA paciente = em.createQuery(
                "SELECT p FROM PacienteJPA p WHERE p.dni = :dni", PacienteJPA.class)
                .setParameter("dni", dni)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (paciente == null) {
                response.getWriter().write("{\"error\":\"Paciente no encontrado\"}");
                return;
            }

            // Formatear fecha correctamente
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimiento = paciente.getFechaNacimiento() != null ? 
                sdf.format(paciente.getFechaNacimiento()) : "";

            // Crear respuesta JSON
            String json = String.format(
                "{\"dni\":\"%s\", \"nombre\":\"%s\", \"apellido\":\"%s\", " +
                "\"telefono\":\"%s\", \"direccion\":\"%s\", \"correo\":\"%s\", " +
                "\"fecha_nacimiento\":\"%s\"}",
                paciente.getDni(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getTelefono() != null ? paciente.getTelefono() : "",
                paciente.getDireccion() != null ? paciente.getDireccion() : "",
                paciente.getCorreo() != null ? paciente.getCorreo() : "",
                fechaNacimiento
            );
            
            response.getWriter().write(json);
            
        } catch (Exception e) {
            System.err.println("Error al buscar paciente: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    private void registrarVentaSinReceta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            // Recibe datos desde el formulario HTML (AJAX o post clásico)
            String productoNombre = request.getParameter("producto");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String formaPago = request.getParameter("formaPago");

            // Buscar producto por nombre o ID (ajustar según tu modelo)
            ProductoJPA producto = em.createQuery(
                "SELECT p FROM ProductoJPA p WHERE p.nombre = :nombre", ProductoJPA.class)
                .setParameter("nombre", productoNombre)
                .getSingleResult();

            if (producto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado.");
                return;
            }

            // Crear factura
            FacturaEmitidaJPA factura = new FacturaEmitidaJPA();
            factura.setFechaEmision(new Date());
            factura.setPaciente(null); // No hay paciente para venta sin receta
            factura.setFormaPago(formaPago);
            factura.setEstadoPago("Pendiente");
            factura.setTipoVenta("Sin receta");
            factura.setTotal(producto.getPrecio().multiply(new BigDecimal(cantidad)));

            // Crear detalle
            DetalleFacturaEmitidaJPA detalle = new DetalleFacturaEmitidaJPA();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setFactura(factura);

            // Registrar venta
            VentaDAO ventaDAO = new VentaDAO(em);
            ventaDAO.registrarVentaSinReceta(factura, detalle); // Ver más abajo

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensaje\":\"Venta sin receta registrada con éxito.\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar la venta sin receta.");
        } finally {
            em.close();
        }
    }

	@Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
