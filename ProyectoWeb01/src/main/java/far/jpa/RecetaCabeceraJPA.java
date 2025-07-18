package far.jpa;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "receta_cabecera")
public class RecetaCabeceraJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta")
    private int idReceta;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private PacienteJPA paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private MedicoJPA medico;

    @Column(name = "fecha_emision", nullable = false)
    private Timestamp fechaEmision;

    @Column(name = "atendida", length = 1)
    private String atendida = "N"; // Solo acepta "S" o "N"

    // Getters y Setters

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public PacienteJPA getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteJPA paciente) {
        this.paciente = paciente;
    }

    public MedicoJPA getMedico() {
        return medico;
    }

    public void setMedico(MedicoJPA medico) {
        this.medico = medico;
    }

    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getAtendida() {
        return atendida;
    }

    public void setAtendida(String atendida) {
        this.atendida = atendida;
    }
}
