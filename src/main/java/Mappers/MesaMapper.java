package Mappers;

import DTOs.MesaDTO;
import Entitys.DiningTable;
import Entitys.Restaurant;

/**
 * Clase utilitaria encargada de la transformación de datos entre la entidad persistente {@link DiningTable}
 * y el objeto de transferencia de datos {@link MesaDTO}.
 * <p>
 * Este mapper facilita el desacoplamiento entre las capas de persistencia y presentación,
 * permitiendo que la interfaz gráfica maneje identificadores numéricos simples (IDs)
 * mientras que la lógica de JPA opera con referencias completas a objetos de tipo {@link Restaurant}.
 * @author Jesus Manuel Martinez Cortez
 */
public class MesaMapper {

    /**
     * Convierte una entidad JPA {@link DiningTable} en un {@link MesaDTO}.
     * <p>
     * Extrae los atributos básicos de la mesa y, si existe una relación establecida con un restaurante,
     * recupera únicamente su identificador único para ser transportado al DTO.
     *
     * @param entidad La entidad de la base de datos que representa una mesa.
     * @return Un objeto {@link MesaDTO} con los datos procesados, o {@code null} si la entidad proporcionada es nula.
     */
    public static MesaDTO toDTO(DiningTable entidad) {
        if (entidad == null) return null;

        MesaDTO dto = new MesaDTO();
        dto.setMesa_id(entidad.getTable_id());
        dto.setNumeroMesa(entidad.getNumber());
        dto.setCapacidad(entidad.getCapacity());

        // Mapeo de la relación: Extrae solo el ID del objeto Restaurante asociado
        if (entidad.getRestaurant() != null) {
            dto.setRestaurante_id(entidad.getRestaurant().getRestaurant_id());
        }

        return dto;
    }

    /**
     * Convierte un {@link MesaDTO} en una entidad persistente {@link DiningTable} lista para ser procesada por JPA.
     * <p>
     * Reconstituye la estructura de la entidad asignando el número y capacidad. En el caso de la relación
     * con el restaurante, crea una instancia de referencia de {@link Restaurant} con el ID proporcionado
     * para que JPA pueda establecer el vínculo de llave foránea correctamente.
     *
     * @param dto El objeto de transferencia con los datos de la mesa capturados.
     * @return Una entidad {@link DiningTable} configurada para su persistencia, o {@code null} si el DTO es nulo.
     */
    public static DiningTable toEntity(MesaDTO dto) {
        if (dto == null) return null;

        DiningTable entidad = new DiningTable();
        // Si el DTO ya tiene ID, se asigna para operaciones de actualización
        if (dto.getMesa_id() != null) {
            entidad.setTable_id(dto.getMesa_id());
        }
        entidad.setNumber(dto.getNumeroMesa());
        entidad.setCapacity(dto.getCapacidad());

        // Reconstrucción de la relación basada en el ID del restaurante
        if (dto.getRestaurante_id() != null) {
            Restaurant restauranteRef = new Restaurant();
            restauranteRef.setRestaurant_id(dto.getRestaurante_id());
            entidad.setRestaurant(restauranteRef);
        }

        return entidad;
    }
}