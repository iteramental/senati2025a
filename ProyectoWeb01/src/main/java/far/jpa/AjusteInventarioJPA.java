package far.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ajustes_inventario")
public class AjusteInventarioJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ajuste")
    private Integer idAjuste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoJPA producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "tipo_ajuste", length = 50)
    private String tipoAjuste;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_farmaceutico")
    private FarmaceuticoJPA farmaceutico;

    @Lob
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters y Setters

    public Integer getIdAjuste() {
        return idAjuste;
    }

    public void setIdAjuste(Integer idAjuste) {
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
