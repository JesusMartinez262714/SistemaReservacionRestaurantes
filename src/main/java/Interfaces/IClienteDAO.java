package Interfaces;

import DTOs.ClienteDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO) para la entidad Cliente.
 * Establece el contrato que cualquier implementación (como ClienteDAO) debe cumplir
 * para gestionar la persistencia de clientes regulares y premium.
 * * Al utilizar DTOs en las firmas de los métodos, se asegura que la capa de persistencia
 * permanezca desacoplada de la interfaz gráfica, permitiendo una transferencia
 * eficiente de la información.
 * @author Jesus Manuel Martinez Cortez
 */
public interface IClienteDAO {

    /**
     * Registra un nuevo cliente en el sistema de persistencia.
     *
     * @param cliente El objeto {@link ClienteDTO} con la información del cliente a registrar.
     * @return El {@link ClienteDTO} persistido, incluyendo su identificador único generado.
     * @throws ValidacionException Si los datos del cliente no cumplen con los requisitos de integridad.
     */
    ClienteDTO agregar(ClienteDTO cliente) throws ValidacionException;

    /**
     * Recupera la información de un cliente específico a través de su identificador único.
     *
     * @param id El identificador único (ID) del cliente.
     * @return El {@link ClienteDTO} correspondiente al ID proporcionado, o {@code null} si no existe.
     */
    ClienteDTO consultarPorId(Long id);

    /**
     * Obtiene una lista con todos los clientes registrados en la base de datos.
     *
     * @return Una {@link List} de {@link ClienteDTO} con todos los registros encontrados.
     */
    List<ClienteDTO> consultarTodos();

    /**
     * Elimina de forma permanente un registro de cliente de la base de datos.
     *
     * @param id El identificador único del cliente a eliminar.
     * @return {@code true} si el cliente fue encontrado y eliminado con éxito; {@code false} en caso contrario.
     */
    boolean eliminar(Long id);

    /**
     * Actualiza la información existente de un cliente en la base de datos.
     *
     * @param cliente El objeto {@link ClienteDTO} que contiene los nuevos datos y el ID del registro a modificar.
     * @return El {@link ClienteDTO} con la información ya actualizada.
     * @throws ValidacionException Si el cliente no existe o si los nuevos datos son inválidos.
     */
    ClienteDTO actualizar(ClienteDTO cliente) throws ValidacionException;
}