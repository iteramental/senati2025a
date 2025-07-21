package far.imp;

import far.jpa.RecetaCabeceraJPA;
import far.jpa.RecetaDetalleJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RecetaDAO {
    private EntityManager em;

    public RecetaDAO(EntityManager em) {
        this.em = em;
    }

    public RecetaCabeceraJPA buscarRecetaPorId(int idReceta) {
        return em.find(RecetaCabeceraJPA.class, idReceta);
    }

    public List<RecetaDetalleJPA> obtenerDetallesReceta(int idReceta) {
        TypedQuery<RecetaDetalleJPA> query = em.createQuery(
            "SELECT d FROM RecetaDetalleJPA d WHERE d.receta.idReceta = :idReceta", RecetaDetalleJPA.class);
        query.setParameter("idReceta", idReceta);
        return query.getResultList();
    }
}
