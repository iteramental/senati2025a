package far.jpa;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "origen_producto")
public class OrigenProductoJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_origen")
    private Integer idOrigen;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y Setters

    public Integer getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
