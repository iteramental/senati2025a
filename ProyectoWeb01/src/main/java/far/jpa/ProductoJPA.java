package far.jpa;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "productos")
public class ProductoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "nombre_base", nullable = false)
    private String nombreBase;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "concentracion")
    private String concentracion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_producto", nullable = false)
    private TipoProductoJPA tipoProducto;

    @ManyToOne
    @JoinColumn(name = "id_tipo_medicamento")
    private TipoMedicamentoJPA tipoMedicamento;

    @ManyToOne
    @JoinColumn(name = "id_origen", nullable = false)
    private OrigenProductoJPA origen;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorJPA proveedor;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
    
    @Column(name = "stock")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private FarmaceuticoJPA creadoPor;

    // Getters y setters

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public FarmaceuticoJPA getCreadoPor() {
        return creadoPor;
    }
    public void setCreadoPor(FarmaceuticoJPA creadoPor) {
        this.creadoPor = creadoPor;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
