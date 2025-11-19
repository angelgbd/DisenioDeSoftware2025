package Validaciones;

/**
 * @author Angel Beltran
 */
public class ValidacionReportes {

    /**
     * Valida que un ID sea obligatorio y positivo.
     * * @param id El identificador a validar.
     * @param nombreCampo El nombre del campo para el mensaje de error (ej: "ID Paciente").
     * @throws IllegalArgumentException Si el ID es nulo o menor/igual a cero.
     */
    public void validarId(Long id, String nombreCampo) {
        if (id == null) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' es obligatorio y no puede ser nulo.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser un número positivo válido.");
        }
    }

}
