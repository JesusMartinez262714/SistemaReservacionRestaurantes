package Interfaces;

import DTOs.ReservacionDTO;
import Excepciones.ReglaNegocioException;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Interfaz que define el contrato para las operaciones de acceso a datos (DAO) de la entidad Reservación.
 * Esta interfaz es el núcleo de la lógica de negocio, ya que establece los métodos necesarios
 * para gestionar la agenda de los restaurantes, vinculando clientes con mesas en tiempos específicos.
 * * Las implementaciones de esta interfaz deben garantizar que se cumplan tanto las validaciones
 * de datos básicos como las reglas de negocio complejas referentes a la capacidad de las mesas.
 * @author Jesus Manuel Martinez Cortez
 */
public interface IReservacionDAO {

    /**
     * Registra una nueva reservación en el sistema tras validar la integridad de los datos
     * y verificar el cumplimiento de las políticas de capacidad de las mesas.
     *
     * @param reservacion El objeto {@link ReservacionDTO} con los detalles de la cita (fecha, hora, cliente y mesa).
     * @return El {@link ReservacionDTO} persistido, incluyendo su identificador único generado por el sistema.
     * @throws ValidacionException Si faltan datos obligatorios o el formato de fecha/hora es incorrecto.
     * @throws ReglaNegocioException Si la reservación viola políticas lógicas (ej. exceder la capacidad de la mesa
     * o intentar reservar una mesa demasiado grande para pocos comensales).
     */
    ReservacionDTO agregar(ReservacionDTO reservacion) throws ValidacionException, ReglaNegocioException;

    /**
     * Localiza y recupera la información de una reservación específica mediante su identificador único.
     *
     * @param id El identificador único (ID) de la reservación.
     * @return El {@link ReservacionDTO} correspondiente si existe, o {@code null} si el registro no es localizado.
     */
    ReservacionDTO consultarPorId(Long id);

    /**
     * Obtiene el listado completo de reservaciones registradas en el historial del sistema.
     *
     * @return Una {@link List} de {@link ReservacionDTO} con todos los registros encontrados en la persistencia.
     */
    List<ReservacionDTO> consultarTodos();

    /**
     * Cancela y elimina de forma definitiva el registro de una reservación en la base de datos.
     *
     * @param id El identificador único de la reservación que se desea eliminar.
     * @return {@code true} si la reservación fue encontrada y eliminada con éxito; {@code false} en caso contrario.
     */
    boolean eliminar(Long id);
}