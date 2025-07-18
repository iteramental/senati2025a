package far.jpa;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ajustes_inventario")
public class AjusteInventarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ajuste")
    private int idAjuste;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoJPA producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "tipo_ajuste", length = 50)
    private String tipoAjuste;

    @Column(name = "fecha", nullable = false)
    private Timestamp fecha;

    @ManyToOne
    @JoinColumn(name = "id_farmaceutico")
    private FarmaceuticoJPA farmaceutico;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    // Getters y Setters

    public int getIdAjuste() {
        return idAjuste;
    }

    public void setIdAjuste(int idAjuste) {
        this.idAjuste = idAjuste;
    }

    public ProductoJPA getProducto() {
        return producto;
    }

    public void setProducto(ProductoJPA producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public FarmaceuticoJPA getFarmaceutico() {
        return farmaceutico;
    }

    public void setFarmaceutico(FarmaceuticoJPA farmaceutico) {
        this.farmaceutico = farmaceutico;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}