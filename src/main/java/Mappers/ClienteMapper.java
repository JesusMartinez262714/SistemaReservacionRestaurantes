package Mappers;

import DTOs.ClienteDTO;
import Entitys.Customer;
import Entitys.CustomerPremium;
import Entitys.Telephone;

/**
 * Clase utilitaria encargada de la transformación de datos entre la entidad persistente (Customer/CustomerPremium)
 * y el objeto de transferencia de datos (ClienteDTO).
 * <p>
 * Este mapper es fundamental para mantener la integridad de la estrategia de herencia SINGLE_TABLE,
 * ya que se encarga de realizar el "casting" adecuado y la extracción de datos específicos según
 * el tipo de cliente (Normal o Premium), además de gestionar la conversión de la lista de teléfonos
 * a un campo simple para la vista.
 * @author Jesus Manuel Martinez Cortez
 */
public class ClienteMapper {

    /**
     * Convierte una entidad JPA {@link Customer} (o su subclase {@link CustomerPremium}) en un {@link ClienteDTO}.
     * <p>
     * El método detecta mediante {@code instanceof} si la entidad es de tipo Premium para mapear
     * los puntos y el nivel; de lo contrario, inicializa estos valores en cero. También extrae
     * el primer teléfono de la lista de la entidad para mostrarlo en el DTO.
     *
     * @param entidad La entidad recuperada de la base de datos.
     * @return Un objeto {@link ClienteDTO} con la información procesada, o {@code null} si la entidad es nula.
     */
    public static ClienteDTO toDTO(Customer entidad) {
        if (entidad == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setcliente_id(entidad.getCustomer_id());
        dto.setEmail(entidad.getEmail());
        dto.setName(entidad.getName());

        // Mapeo del teléfono: Extrae solo el primer registro de la relación OneToMany
        if (entidad.getTelephones() != null && !entidad.getTelephones().isEmpty()) {
            dto.setTelefono(entidad.getTelephones().get(0).telephone);
        }

        // Lógica de discriminación para tipos de cliente
        if (entidad instanceof CustomerPremium) {
            CustomerPremium premium = (CustomerPremium) entidad;
            dto.setEsPremium(true);
            dto.setPuntos(premium.getPoints());
            dto.setNivel(premium.getLevel());
        } else {
            dto.setEsPremium(false);
            dto.setPuntos(0);
            dto.setNivel(0);
        }
        return dto;
    }

    /**
     * Convierte un {@link ClienteDTO} en una entidad persistente lista para ser procesada por JPA.
     * <p>
     * Determina qué instancia crear ({@link Customer} o {@link CustomerPremium}) basándose en el
     * indicador booleano del DTO. Además, envuelve el teléfono proporcionado en una entidad
     * {@link Telephone} para cumplir con la relación de la base de datos.
     *
     * @param dto El objeto de transferencia con los datos capturados en la GUI.
     * @return Una entidad de tipo {@link Customer} (o subclase) configurada, o {@code null} si el DTO es nulo.
     */
    public static Customer toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Customer entidad;
        // Instanciación basada en el tipo de cliente seleccionado en la GUI
        if (dto.isEsPremium()) {
            CustomerPremium premium = new CustomerPremium();
            premium.setPoints(dto.getPuntos());
            premium.setLevel(dto.getNivel());
            entidad = premium;
        } else {
            entidad = new Customer();
        }

        entidad.setName(dto.getName());
        entidad.setEmail(dto.getEmail());

        // Conversión de String de la GUI a la lista de entidades Telephone requerida por JPA
        if (dto.getTelefono() != null && !dto.getTelefono().trim().isEmpty()) {
            java.util.List<Telephone> listaTel = new java.util.ArrayList<>();

            Telephone telEntity = new Telephone();
            telEntity.setTelephone(dto.getTelefono());

            listaTel.add(telEntity);
            entidad.setTelephones(listaTel);
        }

        return entidad;
    }
}