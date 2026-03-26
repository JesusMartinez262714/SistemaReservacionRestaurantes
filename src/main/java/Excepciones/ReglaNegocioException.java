package Excepciones;

/**
 * Excepción personalizada de tipo no comprobada (Runtime) utilizada para indicar
 * que se ha violado una regla de negocio específica de la aplicación.
 * Se lanza en situaciones donde los datos pueden ser válidos en su formato,
 * pero no cumplen con las políticas lógicas o restricciones del sistema
 * (por ejemplo, exceder la capacidad máxima de una mesa o desperdiciar espacio).
 * @author Jesus Manuel Martinez Cortez
 */
public class ReglaNegocioException extends RuntimeException {

    /**
     * Construye una nueva excepción de regla de negocio con el mensaje de detalle especificado.
     *
     * @param message El mensaje explicativo que describe detalladamente la regla de negocio que se ha infringido.
     */
    public ReglaNegocioException(String message) {
        super(message);
    }
}