package far.jpa;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos_producto")
public class TipoProductoJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto")
    private Integer idTipoProducto;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y Setters

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
