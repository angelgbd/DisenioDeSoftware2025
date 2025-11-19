package DAOS;

import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import Interfaces.ICitaDAO;

/**
 *
 * @author angel
 */

public class CitaDAO implements ICitaDAO {

    /**
     * Caso de Uso: CONSULTAR HISTORIAL (Vista Paciente)
     * Obtiene todas las citas pasadas de un paciente específico.
     * * @param pacienteId ID del paciente que ha iniciado sesión
     * @return Lista de DTOs lista para mostrar en la tabla
     */
    @Override
    public List<ReporteHistorialDTO> consultarHistorialPorPaciente(Long pacienteId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            // JPQL: Construimos el objeto Java directamente en la consulta
            // Nota: Usamos el nombre completo de la clase (paquete incluido)
            String jpql = "SELECT new antesDTOs.ReporteHistorialDTO("
                        + "   c.id, "
                        + "   c.fechaHora, "
                        + "   c.paciente.nombre || ' ' || c.paciente.apellido, " // Concatenamos nombre completo
                        + "   c.doctor.nombre || ' ' || c.doctor.apellido, "
                        + "   c.doctor.especialidad, "
                        + "   c.diagnostico, "
                        + "   c.estado"
                        + ") "
                        + "FROM Cita c "
                        + "WHERE c.paciente.id = :pacienteId "
                        + "ORDER BY c.fechaHora DESC";

            TypedQuery<ReporteHistorialDTO> query = em.createQuery(jpql, ReporteHistorialDTO.class);
            query.setParameter("pacienteId", pacienteId);
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Caso de Uso: CONSULTAR AGENDA (Vista Doctor)
     * Obtiene las citas futuras o del día para un doctor.
     * * @param doctorId ID del doctor que consulta
     * @return Lista de DTOs de agenda
     */
    @Override
    public List<ReporteAgendaDTO> consultarAgendaPorDoctor(Long doctorId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            // Para la agenda, al doctor le interesa ver el nombre del PACIENTE
            String jpql = "SELECT new DTOs.ReporteAgendaDTO("
                        + "   c.id, "
                        + "   c.fechaHora, "
                        + "   c.paciente.nombre || ' ' || c.paciente.apellido, " // Nombre Persona (Paciente)
                        + "   c.paciente.dni, " // Dato Auxiliar (DNI)
                        + "   c.estado"
                        + ") "
                        + "FROM Cita c "
                        + "WHERE c.doctor.id = :doctorId "
                        // Filtramos para no ver citas viejas, opcional:
                        // + "AND c.fechaHora >= CURRENT_DATE " 
                        + "ORDER BY c.fechaHora ASC";

            TypedQuery<ReporteAgendaDTO> query = em.createQuery(jpql, ReporteAgendaDTO.class);
            query.setParameter("doctorId", doctorId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
