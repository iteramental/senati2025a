package far.jpa;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "factura_recibida")
public class FacturaRecibidaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    @Column(name = "id_departamento", nullable = false)
    private int idDepartamento;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorJPA proveedor;

    @Column(name = "fecha_emision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;

    @Column(name = "forma_pago", nullable = true)
    private String formaPago; // ENUM: 'Efectivo', 'Crédito', 'Débito'

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago; // ENUM: 'Pendiente', 'Pagado', 'Anulada'

    @Column(name = "contabilizada", nullable = false, length = 1)
    private String contabilizada; // 'S' o 'N'

    @Column(name = "glosa")
    private String glosa;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters y Setters

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public ProveedorJPA getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorJPA proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getContabilizada() {
        return contabilizada;
    }

    public void setContabilizada(String contabilizada) {
        this.contabilizada = contabilizada;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
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