package Excepciones;

/**
 * Excepción personalizada de tipo no comprobada (Runtime) utilizada para señalar
 * errores de validación en los datos de entrada proporcionados por el usuario.
 * Se lanza típicamente cuando los campos obligatorios están vacíos,
 * tienen formatos incorrectos (como correos o teléfonos inválidos) o
 * no cumplen con los requisitos mínimos antes de ser procesados por la base de datos.
 * @author Jesus Manuel Martinez Cortez
 */
public class ValidacionException extends RuntimeException {

    /**
     * Construye una nueva excepción de validación con el mensaje de detalle especificado.
     *
     * @param message El mensaje explicativo que describe exactamente qué validación ha fallado.
     */
    public ValidacionException(String message) {
        super(message);
    }
}