package Mappers;

import DTOs.ReservacionDTO;
import Entitys.Reservation;
import Entitys.Customer;
import Entitys.DiningTable;

/**
 * Clase utilitaria encargada de la transformación de datos entre la entidad persistente {@link Reservation}
 * y el objeto de transferencia de datos {@link ReservacionDTO}.
 * <p>
 * Este mapper es el punto de unión lógico del sistema, ya que transforma las relaciones complejas de JPA
 * (objetos completos de Cliente y Mesa) en identificadores simples (IDs) que pueden ser manipulados
 * fácilmente por la interfaz gráfica. Facilita la persistencia al reconstruir las referencias necesarias
 * para que la base de datos mantenga la integridad referencial entre las tablas.
 * @author Jesus Manuel Martinez Cortez
 */
public class ReservacionMapper {

    /**
     * Convierte una entidad JPA {@link Reservation} en un {@link ReservacionDTO}.
     * <p>
     * Extrae los datos temporales (fecha y hora) y simplifica las relaciones de la reservación
     * obteniendo únicamente los identificadores únicos (IDs) del cliente y la mesa asociada.
     *
     * @param entidad La entidad recuperada de la base de datos que representa una reservación.
     * @return Un objeto {@link ReservacionDTO} con los datos procesados, o {@code null} si la entidad es nula.
     */
    public static ReservacionDTO toDTO(Reservation entidad) {
        if (entidad == null) return null;

        ReservacionDTO dto = new ReservacionDTO();
        dto.setReservacion_id(entidad.getReservation_id());
        dto.setFecha(entidad.getDate());
        dto.setHora(entidad.getHour());

        // Mapeo de relación con Cliente: Se extrae solo el ID
        if (entidad.getCustomer() != null) {
            dto.setCliente_id(entidad.getCustomer().getCustomer_id());
        }

        // Mapeo de relación con Mesa: Se extrae solo el ID
        if (entidad.getDiningTable() != null) {
            dto.setMesa_id(entidad.getDiningTable().getTable_id());
        }

        return dto;
    }

    /**
     * Convierte un {@link ReservacionDTO} en una entidad persistente {@link Reservation} lista para JPA.
     * <p>
     * Reconstituye el objeto de reservación asignando sus valores temporales. Para las relaciones,
     * crea instancias de referencia de {@link Customer} y {@link DiningTable} utilizando los IDs
     * proporcionados en el DTO, permitiendo que JPA establezca correctamente las llaves foráneas.
     *
     * @param dto El objeto de transferencia con los datos de la reservación capturados en la GUI.
     * @return Una entidad {@link Reservation} configurada para su persistencia, o {@code null} si el DTO es nulo.
     */
    public static Reservation toEntity(ReservacionDTO dto) {
        if (dto == null) return null;

        Reservation entidad = new Reservation();
        entidad.setReservation_id(dto.getReservacion_id());
        entidad.setDate(dto.getFecha());
        entidad.setHour(dto.getHora());

        // Reconstrucción de la relación con el Cliente basada en su ID
        if (dto.getCliente_id() != null) {
            Customer clienteRef = new Customer();
            clienteRef.setCustomer_id(dto.getCliente_id());
            entidad.setCustomer(clienteRef);
        }

        // Reconstrucción de la relación con la Mesa basada en su ID
        if (dto.getMesa_id() != null) {
            DiningTable mesaRef = new DiningTable();
            mesaRef.setTable_id(dto.getMesa_id());
            entidad.setDiningTable(mesaRef);
        }

        return entidad;
    }
}