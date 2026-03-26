package DTOs;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de una Reservación.
 * Sirve para transportar los datos desde la interfaz gráfica (GUI) hacia la capa de negocio y de acceso a datos,
 * almacenando las referencias a la mesa y al cliente a través de sus identificadores (IDs).
 * @author Jesus Manuel Martinez Cortez
 */
public class ReservacionDTO {

    private Long reservacion_id;
    private Date fecha;
    private Date hora;
    private Long cliente_id;
    private Long mesa_id;
    private int cantidadPersonas;

    /**
     * Constructor por defecto.
     * Crea una instancia vacía de ReservacionDTO sin inicializar sus atributos.
     */
    public ReservacionDTO() {
    }

    /**
     * Constructor que inicializa los atributos principales de la reservación, a excepción de la cantidad de personas.
     *
     * @param reservacion_id El identificador único de la reservación (puede ser nulo si es una nueva).
     * @param fecha          La fecha programada para la reservación.
     * @param hora           La hora programada para la reservación.
     * @param cliente_id     El identificador del cliente que realiza la reservación.
     * @param mesa_id        El identificador de la mesa asignada a la reservación.
     */
    public ReservacionDTO(Long reservacion_id, Date fecha, Date hora, Long cliente_id, Long mesa_id) {
        this.reservacion_id = reservacion_id;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente_id = cliente_id;
        this.mesa_id = mesa_id;
    }

    /**
     * Obtiene la cantidad de personas que asistirán a la reservación.
     *
     * @return El número de personas.
     */
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    /**
     * Establece la cantidad de personas que asistirán a la reservación.
     *
     * @param cantidadPersonas El número de personas a asignar.
     */
    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    /**
     * Obtiene el identificador único de la reservación.
     *
     * @return El ID de la reservación.
     */
    public Long getReservacion_id() {
        return reservacion_id;
    }

    /**
     * Establece el identificador único de la reservación.
     *
     * @param reservacion_id El nuevo ID a asignar a la reservación.
     */
    public void setReservacion_id(Long reservacion_id) {
        this.reservacion_id = reservacion_id;
    }

    /**
     * Obtiene la fecha programada para la reservación.
     *
     * @return La fecha de la reservación.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha programada para la reservación.
     *
     * @param fecha La nueva fecha a asignar.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora programada para la reservación.
     *
     * @return La hora de la reservación.
     */
    public Date getHora() {
        return hora;
    }

    /**
     * Establece la hora programada para la reservación.
     *
     * @param hora La nueva hora a asignar.
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    /**
     * Obtiene el identificador del cliente vinculado a esta reservación.
     *
     * @return El ID del cliente.
     */
    public Long getCliente_id() {
        return cliente_id;
    }

    /**
     * Establece el identificador del cliente que realiza la reservación.
     *
     * @param cliente_id El nuevo ID del cliente correspondiente.
     */
    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    /**
     * Obtiene el identificador de la mesa asignada a la reservación.
     *
     * @return El ID de la mesa.
     */
    public Long getMesa_id() {
        return mesa_id;
    }

    /**
     * Establece el identificador de la mesa asignada a la reservación.
     *
     * @param mesa_id El nuevo ID de la mesa correspondiente.
     */
    public void setMesa_id(Long mesa_id) {
        this.mesa_id = mesa_id;
    }
}