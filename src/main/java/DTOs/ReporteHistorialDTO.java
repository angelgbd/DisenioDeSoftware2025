package DTOs;

import java.time.LocalDateTime;


/**
 *
 * @author angel
 */

public class ReporteHistorialDTO {

    private Long idCita;            
    private LocalDateTime fechaHora;
    private String nombrePaciente;  
    private String nombreDoctor;    
    private String especialidad;   
    private String diagnostico;     
    private String estado;          

    // Constructor que usará JPA (JPQL)
    public ReporteHistorialDTO(Long idCita, LocalDateTime fechaHora, 
                               String nombrePaciente, String nombreDoctor, 
                               String especialidad, String diagnostico, Enum<?> estado) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.nombrePaciente = nombrePaciente;
        this.nombreDoctor = nombreDoctor;
        this.especialidad = especialidad;
        this.diagnostico = diagnostico;
        this.estado = estado.toString(); 
    }

    // Getters
    public Long getIdCita() { 
        return idCita; }
    public LocalDateTime getFechaHora() {
        return fechaHora; }
    public String getNombrePaciente() {
        return nombrePaciente; }
    public String getNombreDoctor() {
        return nombreDoctor; }
    public String getEspecialidad() {
        return especialidad; }
    public String getDiagnostico() {
        return diagnostico; }
    public String getEstado() {
        return estado; }
    
    @Override
    public String toString() {
        return "Cita del " + fechaHora + ": " + diagnostico;
    }
}