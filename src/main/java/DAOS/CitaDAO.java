    package DAOS;

import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import Interfaces.ICitaDAO;

/**
 *
 * @author Angel Beltran
 */

public class CitaDAO implements ICitaDAO {

    /**
     * Caso de Uso: CONSULTAR HISTORIAL
     * Obtiene todas las citas pasadas de un paciente específico.
     * * @param pacienteId ID del paciente que ha iniciado sesión
     * @return Lista de DTOs lista para mostrar en la tabla
     */
    @Override
    public List<ReporteHistorialDTO> consultarHistorialPorPaciente(Long pacienteId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            // JPQL: Se contruye el objeto java en la consulta
            // Nota: Se usa el nombre de la clase
            String jpql = "SELECT new DTOs.ReporteHistorialDTO("
                        + "   c.id, "
                        + "   c.fechaHora, "
                        + "   c.paciente.nombre || ' ' || c.paciente.apellido, " // Se concatena nombre completo
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
            // Se ajusta para que el doctor pueda ver el nombre del paciente
            String jpql = "SELECT new DTOs.ReporteAgendaDTO("
                        + "   c.id, "
                        + "   c.fechaHora, "
                        + "   c.paciente.nombre || ' ' || c.paciente.apellido, " // Nombre Persona (Paciente)
                        + "   c.paciente.dni, " // Dato Auxiliar (DNI)
                        + "   c.estado"
                        + ") "
                        + "FROM Cita c "
                        + "WHERE c.doctor.id = :doctorId " //Se filtra para no ver citas viejas
                        + "ORDER BY c.fechaHora ASC";

            TypedQuery<ReporteAgendaDTO> query = em.createQuery(jpql, ReporteAgendaDTO.class);
            query.setParameter("doctorId", doctorId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
