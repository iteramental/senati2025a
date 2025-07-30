package far.jpa;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lotes_producto")
public class LoteProductoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private int idLote;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoJPA producto;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private FacturaRecibidaJPA factura;

    @Column(name = "fecha_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters y Setters

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public ProductoJPA getProducto() {
        return producto;
    }

    public void setProducto(ProductoJPA producto) {
        this.producto = producto;
    }

    public FacturaRecibidaJPA getFactura() {
        return factura;
    }

    public void setFactura(FacturaRecibidaJPA factura) {
        this.factura = factura;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
