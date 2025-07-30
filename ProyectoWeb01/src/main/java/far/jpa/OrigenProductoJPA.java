package far.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "origen_producto")
public class OrigenProductoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_origen")
    private int idOrigen;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y Setters
    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}