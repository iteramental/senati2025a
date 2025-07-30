package far.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_producto")
public class TipoProductoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto")
    private int idTipoProducto;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y setters
    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
