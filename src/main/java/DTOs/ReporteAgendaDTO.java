package DTOs;

import java.time.LocalDateTime;

/**
 *
 * @author angel
 */

public class ReporteAgendaDTO {

    private Long idCita;
    private LocalDateTime fechaHora;
    private String nombrePersona;   // Doctor ve Paciente y Paciente ve Doctor.
    private String datoAuxiliar;    // Identificacion del Paciente o Cedula del Doctor
    private String estado;

    public ReporteAgendaDTO(Long idCita, LocalDateTime fechaHora, 
                            String nombrePersona, String datoAuxiliar, Enum<?> estado) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.nombrePersona = nombrePersona;
        this.datoAuxiliar = datoAuxiliar;
        this.estado = estado.toString();
    }

    public Long getIdCita() { return idCita; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getNombrePersona() { return nombrePersona; }
    public String getDatoAuxiliar() { return datoAuxiliar; }
    public String getEstado() { return estado; }
}