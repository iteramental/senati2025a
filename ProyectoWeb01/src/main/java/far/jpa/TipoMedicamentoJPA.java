package far.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_medicamento")
public class TipoMedicamentoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_medicamento")
    private int idTipoMedicamento;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // Getters y Setters
    public int getIdTipoMedicamento() {
        return idTipoMedicamento;
    }

    public void setIdTipoMedicamento(int idTipoMedicamento) {
        this.idTipoMedicamento = idTipoMedicamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

