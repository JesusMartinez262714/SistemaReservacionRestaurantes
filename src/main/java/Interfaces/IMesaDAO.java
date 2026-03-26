package Interfaces;

import DTOs.MesaDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Interfaz que define el contrato para las operaciones de acceso a datos (DAO) de la entidad Mesa.
 * Proporciona los métodos necesarios para gestionar el inventario de mesas físicas de los
 * restaurantes, asegurando que cualquier implementación (como MesaDAO) maneje correctamente
 * la persistencia y la integridad de los datos.
 * @author Jesus Manuel Martinez Cortez
 */
public interface IMesaDAO {

    /**
     * Registra una nueva mesa en el sistema de persistencia y la vincula a un restaurante.
     *
     * @param mesa El objeto {@link MesaDTO} que contiene la información de la mesa a registrar.
     * @return El {@link MesaDTO} con la información persistida, incluyendo su ID autogenerado.
     * @throws ValidacionException Si los datos de la mesa son inconsistentes o el restaurante no existe.
     */
    MesaDTO agregar(MesaDTO mesa) throws ValidacionException;

    /**
     * Actualiza la información de una mesa ya existente en la base de datos.
     * Permite modificar atributos como el número de mesa o su capacidad de comensales.
     *
     * @param mesa El objeto {@link MesaDTO} que contiene el ID de la mesa y los nuevos datos a guardar.
     * @return El {@link MesaDTO} con la información ya actualizada en el sistema.
     * @throws ValidacionException Si la mesa no se encuentra en el sistema o si los nuevos datos son inválidos.
     */
    MesaDTO actualizar(MesaDTO mesa) throws ValidacionException;

    /**
     * Elimina de forma definitiva el registro de una mesa en la base de datos.
     *
     * @param id El identificador único (ID) de la mesa que se desea eliminar.
     * @return {@code true} si la mesa fue localizada y eliminada exitosamente; {@code false} en caso contrario.
     */
    boolean eliminar(Long id);

    /**
     * Recupera la información detallada de una mesa específica mediante su identificador.
     *
     * @param id El identificador único de la mesa.
     * @return El {@link MesaDTO} correspondiente si existe, o {@code null} si no se encuentra el registro.
     */
    MesaDTO consultarPorId(Long id);

    /**
     * Obtiene la lista completa de todas las mesas registradas en el sistema, sin importar el restaurante.
     *
     * @return Una {@link List} de {@link MesaDTO} con todos los registros de mesas encontrados.
     */
    List<MesaDTO> consultarTodos();
}