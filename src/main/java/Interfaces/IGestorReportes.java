package Interfaces;

import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import java.util.List;

/**
 * @author Angel Beltran
 */
public interface IGestorReportes {

    /**
     * Obtiene el historial médico de un paciente.
     * Puede incluir validaciones de negocio 
     */
    List<ReporteHistorialDTO> obtenerHistorialPaciente(Long idPaciente) throws Exception;

    /**
     * Obtiene la agenda de citas de un doctor.
     */
    List<ReporteAgendaDTO> obtenerAgendaDoctor(Long idDoctor) throws Exception;
    
}
