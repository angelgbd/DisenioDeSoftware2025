package Interfaces;

import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import java.util.List;

/**
 *@author Angel Beltran
 */
public interface ICitaDAO {

    List<ReporteHistorialDTO> consultarHistorialPorPaciente(Long pacienteId);

    List<ReporteAgendaDTO> consultarAgendaPorDoctor(Long doctorId);
} 
