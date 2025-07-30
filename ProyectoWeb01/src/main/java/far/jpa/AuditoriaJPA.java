package far.jpa;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auditoria")
public class AuditoriaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private int idLog;

    @ManyToOne
    @JoinColumn(name = "id_farmaceutico")
    private FarmaceuticoJPA farmaceutico;

    @Column(name = "accion", length = 100)
    private String accion;

    @Column(name = "tabla_afectada", length = 100)
    private String tablaAfectada;

    @Column(name = "id_registro")
    private int idRegistro;

    @Column(name = "fecha_hora", insertable = false, updatable = false)
    private Timestamp fechaHora;

    // Getters y Setters

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public FarmaceuticoJPA getFarmaceutico() {
        return farmaceutico;
    }

    public void setFarmaceutico(FarmaceuticoJPA farmaceutico) {
        this.farmaceutico = farmaceutico;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }
}