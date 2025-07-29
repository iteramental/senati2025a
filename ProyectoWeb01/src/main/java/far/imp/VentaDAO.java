package far.imp;

import java.util.List;

import far.jpa.FacturaEmitidaJPA;
import far.jpa.RecetaDetalleJPA;
import far.jpa.DetalleFacturaEmitidaJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VentaDAO {
    private EntityManager em;
    
    public List<FacturaEmitidaJPA> listarVentas() {
        return em.createQuery("SELECT f FROM FacturaEmitidaJPA f ORDER BY f.fechaEmision DESC", FacturaEmitidaJPA.class)
                 .getResultList();
    }

    public VentaDAO(EntityManager em) {
        this.em = em;
    }

    public void registrarVenta(FacturaEmitidaJPA factura, List<RecetaDetalleJPA> detalles) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // Asegura que contabilizada nunca sea nulo
            if (factura.getContabilizada() == null || factura.getContabilizada().trim().isEmpty()) {
                factura.setContabilizada("N");
            }
            em.persist(factura);
            for (RecetaDetalleJPA recetaDetalle : detalles) {
                DetalleFacturaEmitidaJPA detalle = new DetalleFacturaEmitidaJPA();
                detalle.setFactura(factura);
                detalle.setProducto(recetaDetalle.getProducto());
                detalle.setCantidad(recetaDetalle.getCantidad());
                detalle.setPrecioUnitario(recetaDetalle.getProducto().getPrecio()); // O como lo calcules
                em.persist(detalle);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Propagar la excepción
        }
    }
    
    public void registrarVentaSinReceta(FacturaEmitidaJPA factura, DetalleFacturaEmitidaJPA detalle) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (factura.getContabilizada() == null || factura.getContabilizada().trim().isEmpty()) {
                factura.setContabilizada("N");
            }
            em.persist(factura);
            em.persist(detalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
