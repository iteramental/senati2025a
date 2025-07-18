package far.jpa;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "factura_emitida")
public class FacturaEmitidaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    @Column(name = "fecha_emision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;

    @Column(name = "id_departamento", nullable = false)
    private int idDepartamento;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private PacienteJPA paciente;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "forma_pago")
    private String formaPago; // Efectivo, Crédito, Débito

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago; // Pendiente, Pagado, Anulada

    @Column(name = "contabilizada", nullable = false, length = 1)
    private String contabilizada; // S o N

    @Column(name = "glosa")
    private String glosa;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "tipo_venta", nullable = false)
    private String tipoVenta; // Receta o Externo

    // Getters y Setters

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public PacienteJPA getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteJPA paciente) {
        this.paciente = paciente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }
}
