package far.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class ProductoJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_base", nullable = false)
    private String nombreBase;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "concentracion")
    private String concentracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_producto", nullable = false)
    private TipoProductoJPA tipoProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_medicamento")
    private TipoMedicamentoJPA tipoMedicamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_origen", nullable = false)
    private OrigenProductoJPA origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorJPA proveedor;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private FarmaceuticoJPA creadoPor;

    // Getters y Setters

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public TipoProductoJPA getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoJPA tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public TipoMedicamentoJPA getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(TipoMedicamentoJPA tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public OrigenProductoJPA getOrigen() {
        return origen;
    }

    public void setOrigen(OrigenProductoJPA origen) {
        this.origen = origen;
    }

    public ProveedorJPA getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorJPA proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FarmaceuticoJPA getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(FarmaceuticoJPA creadoPor) {
        this.creadoPor = creadoPor;
    }
}
