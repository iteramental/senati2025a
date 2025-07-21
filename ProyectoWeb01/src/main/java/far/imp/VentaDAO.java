package far.imp;

import far.jpa.FacturaEmitidaJPA;
import far.jpa.DetalleFacturaEmitidaJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VentaDAO {
    private EntityManager em;

    public VentaDAO(EntityManager em) {
        this.em = em;
    }

    public void registrarVenta(FacturaEmitidaJPA factura, List<DetalleFacturaEmitidaJPA> detalles) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(factura);
            for (DetalleFacturaEmitidaJPA detalle : detalles) {
                detalle.setFactura(factura);
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
}
