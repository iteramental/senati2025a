package far.jpa;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "cuentas_contables")
public class CuentaContableJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private int idCuenta;

    @Column(name = "descripcion", length = 4000)
    private String descripcion;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "tipo", nullable = false)
    private String tipo; // "Ingreso" o "Egreso"

    @Column(name = "entidad", length = 100)
    private String entidad;

    @Column(name = "estado", nullable = false)
    private String estado = "Pendiente"; // "Pendiente" o "Pagado"

    @ManyToOne
    @JoinColumn(name = "codigo")
    private PlanContableJPA planContable;

    @ManyToOne
    @JoinColumn(name = "id_comprobante")
    private FacturaEmitidaJPA facturaEmitida;

    // Getters y Setters

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PlanContableJPA getPlanContable() {
        return planContable;
    }

    public void setPlanContable(PlanContableJPA planContable) {
        this.planContable = planContable;
    }

    public FacturaEmitidaJPA getFacturaEmitida() {
        return facturaEmitida;
    }

    public void setFacturaEmitida(FacturaEmitidaJPA facturaEmitida) {
        this.facturaEmitida = facturaEmitida;
    }
}
