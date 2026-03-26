package Mappers;

import DTOs.RestauranteDTO;
import Entitys.Restaurant;
import Entitys.Address;

/**
 * Clase utilitaria encargada de la transformación de datos entre la entidad persistente {@link Restaurant}
 * y el objeto de transferencia de datos {@link RestauranteDTO}.
 * <p>
 * Este mapper es clave para la simplificación de la interfaz de usuario, ya que realiza el "aplanamiento"
 * de la relación Uno a Uno con la entidad {@link Address}. En lugar de manejar objetos anidados en la GUI,
 * este componente extrae y agrupa todos los atributos de identidad y ubicación en un único DTO,
 * permitiendo una captura de datos más fluida y centralizada.
 * @author Jesus Manuel Martinez Cortez
 */
public class RestauranteMapper {

    /**
     * Convierte una entidad JPA {@link Restaurant} en un {@link RestauranteDTO}.
     * <p>
     * El método mapea los atributos generales del restaurante y, si la relación con la dirección
     * no es nula, procede a extraer cada campo de la entidad {@link Address} (calle, número, CP, etc.)
     * para asignarlos directamente a los campos planos del DTO.
     *
     * @param entidad La entidad recuperada de la base de datos.
     * @return Un objeto {@link RestauranteDTO} con la información unificada, o {@code null} si la entidad es nula.
     */
    public static RestauranteDTO toDTO(Restaurant entidad) {
        if (entidad == null) return null;

        RestauranteDTO dto = new RestauranteDTO();
        dto.setRestaurante_id(entidad.getRestaurant_id());
        dto.setNombre(entidad.getName());
        dto.setTelefono(entidad.getTelephone());
        dto.setTipoCocina(entidad.getKitchenType());

        // Aplanamiento de la relación OneToOne con la dirección
        if (entidad.getAddress() != null) {
            dto.setCalle(entidad.getAddress().getStreet());
            dto.setNumero(entidad.getAddress().getNumber());
            dto.setCp(entidad.getAddress().getCp());
            dto.setCiudad(entidad.getAddress().getCity());
            dto.setEstado(entidad.getAddress().getState());
        }

        return dto;
    }

    /**
     * Convierte un {@link RestauranteDTO} en una entidad persistente {@link Restaurant} lista para ser procesada por JPA.
     * <p>
     * Reconstituye la estructura jerárquica de la base de datos creando una nueva instancia de {@link Address}
     * con los datos de ubicación contenidos en el DTO y vinculándola al objeto {@link Restaurant}.
     * Esto asegura que JPA pueda persistir ambas entidades correctamente mediante la cascada definida.
     *
     * @param dto El objeto de transferencia con los datos unificados capturados en la GUI.
     * @return Una entidad {@link Restaurant} con su dirección asociada configurada, o {@code null} si el DTO es nulo.
     */
    public static Restaurant toEntity(RestauranteDTO dto) {
        if (dto == null) return null;

        Restaurant entidad = new Restaurant();
        if (dto.getRestaurante_id() != null) {
            entidad.setRestaurant_id(dto.getRestaurante_id());
        }
        entidad.setName(dto.getNombre());
        entidad.setTelephone(dto.getTelefono());
        entidad.setKitchenType(dto.getTipoCocina());

        Address direccion = new Address();
        direccion.setStreet(dto.getCalle());
        direccion.setNumber(dto.getNumero());
        direccion.setCp(dto.getCp());
        direccion.setCity(dto.getCiudad());
        direccion.setState(dto.getEstado());

        entidad.setAddress(direccion);

        return entidad;
    }
}