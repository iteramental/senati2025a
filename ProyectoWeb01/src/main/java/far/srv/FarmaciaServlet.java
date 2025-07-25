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
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

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

    	request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
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
        else if ("buscarMedicamento".equalsIgnoreCase(accion)) { // ← Añade este caso
            buscarMedicamento(request, response);
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
    private void buscarMedicamento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            
            String nombre = request.getParameter("nombre");
            System.out.println("Buscando medicamento: " + nombre);

            // CONSULTA CORREGIDA (versión simplificada y funcional)
            String jpql = "SELECT p FROM ProductoJPA p \r\n"
            		+ "WHERE (\r\n"
            		+ "  LOWER(p.nombreBase) LIKE LOWER(CONCAT('%', :nombre, '%')) \r\n"
            		+ "  OR LOWER(p.nombreComercial) LIKE LOWER(CONCAT('%', :nombre, '%'))\r\n"
            		+ ") \r\n"
            		+ "ORDER BY p.nombreComercial, p.nombreBase\r\n"
            		+ "";

            System.out.println("JPQL: " + jpql); // Para debug
            
            List<ProductoJPA> productos = em.createQuery(jpql, ProductoJPA.class)
                .setParameter("nombre", nombre)
                .getResultList();

            Gson gson = new Gson();
            JsonArray jsonArray = new JsonArray();
            
            for (ProductoJPA p : productos) {
                JsonObject json = new JsonObject();
                json.addProperty("idProducto", p.getIdProducto());
                json.addProperty("nombreBase", p.getNombreBase());
                json.addProperty("nombreComercial", p.getNombreComercial());
                json.addProperty("concentracion", p.getConcentracion());
                json.addProperty("precio", p.getPrecio());
                json.addProperty("stock", p.getStock());
                jsonArray.add(json);
            }

            response.getWriter().write(gson.toJson(jsonArray));

        } catch (Exception e) {
            System.err.println("Error al buscar medicamentos: " + e.getMessage());
            JsonObject error = new JsonObject();
            error.addProperty("error", "Error al buscar medicamentos: " + e.getMessage());
            response.getWriter().write(error.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            em.close();
        }
    }
    private void registrarVentaSinReceta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            // 1. Parsear los datos JSON
            String json = request.getReader().lines().collect(Collectors.joining());
            Gson gson = new Gson();
            Map<String, Object> ventaData = gson.fromJson(json, Map.class);

            // 2. Buscar paciente
            String dniPaciente = (String) ventaData.get("dniPaciente");
            PacienteJPA paciente = em.createQuery(
                "SELECT p FROM PacienteJPA p WHERE p.dni = :dni", PacienteJPA.class)
                .setParameter("dni", dniPaciente)
                .getSingleResult();

            // 3. Crear factura
            transaction.begin();
            
            FacturaEmitidaJPA factura = new FacturaEmitidaJPA();
            factura.setFechaEmision(new Date());
            factura.setPaciente(paciente);
            factura.setTotal(new BigDecimal(ventaData.get("total").toString()));
            factura.setFormaPago((String) ventaData.get("formaPago"));
            factura.setEstadoPago("Pagado");
            factura.setContabilizada("N");
            factura.setTipoVenta("Externo");
            factura.setCreatedAt(new Date());
            factura.setUpdatedAt(new Date());
            factura.setIdDepartamento(1); // Ajustar según tu lógica

            em.persist(factura);

            // 4. Procesar cada producto del carrito
            List<Map<String, Object>> productos = (List<Map<String, Object>>) ventaData.get("productos");
            for (Map<String, Object> item : productos) {
                int idProducto = ((Double) item.get("idProducto")).intValue();
                ProductoJPA producto = em.find(ProductoJPA.class, idProducto);
                
                // Validar stock
                int cantidad = ((Double) item.get("cantidad")).intValue();
                if (producto.getStock() < cantidad) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombreComercial());
                }

                // Crear detalle
                DetalleFacturaEmitidaJPA detalle = new DetalleFacturaEmitidaJPA();
                detalle.setFactura(factura);
                detalle.setTipoItem("Producto");
                detalle.setProducto(producto);
                detalle.setDescripcion(producto.getNombreComercial());
                detalle.setCantidad(cantidad);
                
                BigDecimal precioUnitario = new BigDecimal(item.get("precioUnitario").toString());
                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubtotal(precioUnitario.multiply(new BigDecimal(cantidad)));
                detalle.setCreatedAt(new Date());
                detalle.setUpdatedAt(new Date());

                em.persist(detalle);
                
                // Actualizar stock
                producto.setStock(producto.getStock() - cantidad);
                em.merge(producto);
            }

            transaction.commit();

            // Respuesta exitosa
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"idFactura\": " + factura.getIdFactura() + "}");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
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
