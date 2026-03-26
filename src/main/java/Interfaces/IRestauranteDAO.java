package Interfaces;

import DTOs.RestauranteDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Interfaz que define el contrato para las operaciones de acceso a datos (DAO) de la entidad Restaurante.
 * Establece los métodos necesarios para gestionar el ciclo de vida de los registros de restaurantes
 * y sus direcciones asociadas, garantizando que cualquier implementación (como RestauranteDAO)
 * mantenga la integridad de la información comercial y de ubicación.
 * @author Jesus Manuel Martinez Cortez
 */
public interface IRestauranteDAO {

    /**
     * Registra un nuevo restaurante en el sistema de persistencia, incluyendo la creación
     * automática de su dirección física vinculada.
     *
     * @param restaurante El objeto {@link RestauranteDTO} que contiene los datos de identidad y ubicación.
     * @return El {@link RestauranteDTO} con la información persistida y su identificador único generado.
     * @throws ValidacionException Si faltan datos obligatorios o si el formato del teléfono es incorrecto.
     */
    RestauranteDTO agregar(RestauranteDTO restaurante) throws ValidacionException;

    /**
     * Actualiza la información existente de un restaurante y su dirección en la base de datos.
     * Permite modificar tanto los datos generales como los detalles específicos de la ubicación.
     *
     * @param restaurante El objeto {@link RestauranteDTO} que contiene el ID del registro y los nuevos datos.
     * @return El {@link RestauranteDTO} con la información ya actualizada en el sistema.
     * @throws ValidacionException Si el restaurante no se encuentra registrado o si los nuevos datos son inválidos.
     */
    RestauranteDTO actualizar(RestauranteDTO restaurante) throws ValidacionException;

    /**
     * Elimina de forma definitiva el registro de un restaurante y su dirección de la base de datos.
     *
     * @param id El identificador único (ID) del restaurante que se desea eliminar.
     * @return {@code true} si el restaurante fue localizado y eliminado exitosamente; {@code false} en caso contrario.
     */
    boolean eliminar(Long id);

    /**
     * Recupera la información completa de un restaurante específico mediante su identificador único.
     *
     * @param id El identificador único del restaurante.
     * @return El {@link RestauranteDTO} correspondiente si existe, o {@code null} si no se encuentra el registro.
     */
    RestauranteDTO consultarPorId(Long id);

    /**
     * Obtiene una lista con todos los restaurantes registrados en el sistema de persistencia.
     *
     * @return Una {@link List} de {@link RestauranteDTO} con todos los registros encontrados.
     */
    List<RestauranteDTO> consultarTodos();
}