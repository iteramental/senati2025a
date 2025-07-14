package far.jpa;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos_medicamento")
public class TipoMedicamentoJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_medicamento")
    private Integer idTipoMedicamento;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y Setters

    public Integer getIdTipoMedicamento() {
        return idTipoMedicamento;
    }

    public void setIdTipoMedicamento(Integer idTipoMedicamento) {
        this.idTipoMedicamento = idTipoMedicamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
