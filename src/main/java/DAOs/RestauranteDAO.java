package DAOs;

import DTOs.RestauranteDTO;
import Entitys.Address;
import Entitys.Restaurant;
import Excepciones.ValidacionException;
import Interfaces.IRestauranteDAO;
import Mappers.RestauranteMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz IRestauranteDAO para la gestión de datos de la entidad Restaurante.
 * Utiliza JPA para realizar operaciones de persistencia en la base de datos, gestionando tanto
 * los atributos básicos del restaurante como su dirección asociada.
 * @author Jesus Manuel Martinez Cortez
 */
public class RestauranteDAO implements IRestauranteDAO {

    private EntityManagerFactory emf = Conexion.getEMF();

    /**
     * Registra un nuevo restaurante en la base de datos tras validar que sus datos sean correctos.
     * Mapea la información del DTO a la entidad correspondiente y maneja la transacción de guardado.
     *
     * @param dto El objeto {@link RestauranteDTO} que contiene los datos del restaurante a guardar.
     * @return El objeto {@link RestauranteDTO} con los datos persistidos y el ID autogenerado.
     * @throws ValidacionException Si los datos ingresados no cumplen con las reglas de validación
     * o si ocurre un error durante el proceso de transacción.
     */
    @Override
    public RestauranteDTO agregar(RestauranteDTO dto) throws ValidacionException {
        validarDatosRestaurante(dto);

        EntityManager em = emf.createEntityManager();
        try {
            Restaurant entidad = RestauranteMapper.toEntity(dto);

            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();

            return RestauranteMapper.toDTO(entidad);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new ValidacionException("Error al guardar el restaurante: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Recupera una lista completa con todos los restaurantes registrados en la base de datos.
     *
     * @return Una lista de objetos {@link RestauranteDTO} que contiene la información de todos los restaurantes.
     */
    @Override
    public List<RestauranteDTO> consultarTodos(){
        EntityManager em = emf.createEntityManager();
        List<RestauranteDTO> listaDTOs = new ArrayList<>();
        try{
            List<Restaurant> restauranteBD = em.createQuery("SELECT r FROM Restaurant r",Restaurant.class).getResultList();
            for(Restaurant r : restauranteBD){
                listaDTOs.add(RestauranteMapper.toDTO(r));
            }
        }finally {
            em.close();
        }
        return listaDTOs;
    }

    /**
     * Busca un restaurante específico utilizando su identificador único.
     *
     * @param id El identificador único del restaurante en la base de datos.
     * @return El objeto {@link RestauranteDTO} si se encuentra el registro, o {@code null} en caso contrario.
     */
    @Override
    public RestauranteDTO consultarPorId(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            Restaurant restaurant = em.find(Restaurant.class,id);
            return RestauranteMapper.toDTO(restaurant);
        }finally {
            em.close();
        }
    }

    /**
     * Elimina físicamente un registro de restaurante de la base de datos.
     *
     * @param id El identificador único del restaurante que se desea eliminar.
     * @return {@code true} si el restaurante fue encontrado y eliminado con éxito, {@code false} en caso contrario.
     */
    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Restaurant restaurant = em.find(Restaurant.class, id);
            if (restaurant != null) {
                em.remove(restaurant);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de un restaurante existente, incluyendo los detalles de su dirección.
     * Busca el registro actual en la base de datos y modifica únicamente los campos especificados.
     *
     * @param dto El objeto {@link RestauranteDTO} con el ID existente y los nuevos datos a guardar.
     * @return El objeto {@link RestauranteDTO} con la información ya actualizada.
     * @throws ValidacionException Si el restaurante no existe en la base de datos o si falla la transacción.
     */
    @Override
    public RestauranteDTO actualizar(RestauranteDTO dto) throws ValidacionException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Restaurant existente = em.find(Restaurant.class, dto.getRestaurante_id());
            if (existente == null) throw new ValidacionException("Restaurante no encontrado.");

            existente.setName(dto.getNombre());
            existente.setTelephone(dto.getTelefono());
            existente.setKitchenType(dto.getTipoCocina());


            if (existente.getAddress() != null) {
                Address dir = existente.getAddress();
                dir.setStreet(dto.getCalle());
                dir.setNumber(dto.getNumero());
                dir.setCp(dto.getCp());
                dir.setCity(dto.getCiudad());
                dir.setState(dto.getEstado());
            }

            em.getTransaction().commit();
            return RestauranteMapper.toDTO(existente);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new ValidacionException("Error al actualizar restaurante: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Valida que los datos obligatorios del restaurante sean correctos antes de enviarlos a la base de datos.
     * Comprueba que el nombre y el tipo de cocina no estén vacíos, y que el teléfono cumpla con el formato.
     *
     * @param dto El objeto {@link RestauranteDTO} a validar.
     * @throws ValidacionException Si alguno de los campos es nulo, está vacío o tiene un formato incorrecto.
     */
    private void validarDatosRestaurante(RestauranteDTO dto) throws ValidacionException {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre del restaurante no puede estar vacío.");
        }
        if (dto.getTipoCocina() == null || dto.getTipoCocina().trim().isEmpty()) {
            throw new ValidacionException("Debe especificar el tipo de cocina.");
        }

        if (dto.getTelefono() == null || !dto.getTelefono().matches("^\\d{10}$")) {
            throw new ValidacionException("El teléfono debe tener exactamente 10 dígitos numéricos sin espacios ni guiones (Ejemplo: 6444010101).");
        }
    }
}