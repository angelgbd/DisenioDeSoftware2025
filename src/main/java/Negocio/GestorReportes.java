package Negocio;

import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import Interfaces.IGestorReportes;
import DAOS.CitaDAO;
import Interfaces.ICitaDAO;
import java.util.List;
import Validaciones.ValidacionReportes;

/**
 * @author Angel Beltran
 */

public class GestorReportes implements IGestorReportes {

    private final ICitaDAO citaDAO;
    private final ValidacionReportes validador; 

    public GestorReportes() {
        this.citaDAO = new CitaDAO();
        this.validador = new ValidacionReportes();
    }

    @Override
    public List<ReporteHistorialDTO> obtenerHistorialPaciente(Long idPaciente) throws Exception {
        
        validador.validarId(idPaciente, "ID Paciente");
        
        try {
            return citaDAO.consultarHistorialPorPaciente(idPaciente);
        } catch (Exception e) {
            
            System.err.println("Error en capa negocio (Historial): " + e.getMessage());
            
            throw new Exception("Error al recuperar el historial médico.", e);
        }
    }

    @Override
    public List<ReporteAgendaDTO> obtenerAgendaDoctor(Long idDoctor) throws Exception {
        
        validador.validarId(idDoctor, "ID Doctor");
        
        try {
            return citaDAO.consultarAgendaPorDoctor(idDoctor);
        } catch (Exception e) {
            System.err.println("Error en capa negocio (Agenda): " + e.getMessage());
            throw new Exception("Error al recuperar la agenda del doctor.", e);
        }
    }
}
