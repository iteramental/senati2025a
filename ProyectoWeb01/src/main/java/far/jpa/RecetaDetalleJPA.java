package far.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "receta_detalle")
public class RecetaDetalleJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_receta", nullable = false)
    private RecetaCabeceraJPA receta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoJPA producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "indicacion", length = 255)
    private String indicacion;

    @Column(name = "entregado", length = 1)
    private String entregado = "N"; // Solo "S" o "N"

    // Getters y setters

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public RecetaCabeceraJPA getReceta() {
        return receta;
    }

    public void setReceta(RecetaCabeceraJPA receta) {
        this.receta = receta;
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

    public String getIndicacion() {
        return indicacion;
    }

    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }
}