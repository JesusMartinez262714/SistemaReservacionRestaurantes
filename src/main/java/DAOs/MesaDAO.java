package DAOs;

import DTOs.MesaDTO;
import Entitys.DiningTable;
import Entitys.Restaurant;
import Excepciones.ValidacionException;
import Interfaces.IMesaDAO;
import Mappers.MesaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz IMesaDAO para la gestión y acceso a datos de la entidad Mesa (DiningTable).
 * Se encarga de realizar las operaciones de persistencia en la base de datos utilizando JPA,
 * asegurando la correcta relación con la entidad Restaurante.
 * @author Jesus Manuel Martinez Cortez
 */
public class MesaDAO implements IMesaDAO {

    private EntityManagerFactory emf = Conexion.getEMF();

    /**
     * Registra una nueva mesa en la base de datos y la asocia a un restaurante existente.
     * Convierte el DTO a entidad, verifica la existencia del restaurante y persiste la información.
     *
     * @param dto El objeto {@link MesaDTO} que contiene la información de la nueva mesa.
     * @return El objeto {@link MesaDTO} con los datos persistidos y el ID generado automáticamente.
     * @throws ValidacionException Si el restaurante asociado no existe o si ocurre un error durante la transacción.
     */
    @Override
    public MesaDTO agregar(MesaDTO dto) throws ValidacionException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            DiningTable entidad = MesaMapper.toEntity(dto);

            Restaurant restaurante = em.find(Restaurant.class, dto.getRestaurante_id());
            if (restaurante == null) {
                throw new ValidacionException("El restaurante con ID " + dto.getRestaurante_id() + " no existe.");
            }
            entidad.setRestaurant(restaurante);

            em.persist(entidad);
            em.getTransaction().commit();

            return MesaMapper.toDTO(entidad);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            throw new ValidacionException("Error al guardar la mesa: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Recupera todas las mesas registradas en la base de datos.
     *
     * @return Una lista de objetos {@link MesaDTO} con la información de cada mesa existente.
     */
    @Override
    public List<MesaDTO> consultarTodos() {
        EntityManager em = emf.createEntityManager();
        List<MesaDTO> listaDTOs = new ArrayList<>();
        try {
            List<DiningTable> mesasBD = em.createQuery("SELECT m FROM DiningTable m", DiningTable.class).getResultList();
            for (DiningTable m : mesasBD) {
                listaDTOs.add(MesaMapper.toDTO(m));
            }
        } finally {
            em.close();
        }
        return listaDTOs;
    }

    /**
     * Busca una mesa específica en la base de datos utilizando su identificador único.
     *
     * @param id El identificador único de la mesa.
     * @return El objeto {@link MesaDTO} correspondiente si se encuentra, o {@code null} en caso contrario.
     */
    @Override
    public MesaDTO consultarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            DiningTable mesa = em.find(DiningTable.class, id);
            return MesaMapper.toDTO(mesa);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina físicamente una mesa de la base de datos.
     *
     * @param id El identificador único de la mesa que se desea eliminar.
     * @return {@code true} si la mesa fue encontrada y eliminada con éxito, {@code false} en caso contrario.
     */
    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DiningTable mesa = em.find(DiningTable.class, id);
            if (mesa != null) {
                em.remove(mesa);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza los datos de una mesa existente en la base de datos.
     * Permite modificar el número, la capacidad y reasignar la mesa a un restaurante diferente si es necesario.
     *
     * @param dto El objeto {@link MesaDTO} con los nuevos datos y el ID de la mesa a modificar.
     * @return El objeto {@link MesaDTO} actualizado.
     * @throws ValidacionException Si la mesa o el nuevo restaurante no existen, o si falla la transacción.
     */
    @Override
    public MesaDTO actualizar(MesaDTO dto) throws ValidacionException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            DiningTable existente = em.find(DiningTable.class, dto.getMesa_id());
            if (existente == null) throw new ValidacionException("Mesa no encontrada.");

            existente.setNumber(dto.getNumeroMesa());
            existente.setCapacity(dto.getCapacidad());

            if (existente.getRestaurant().getRestaurant_id() != dto.getRestaurante_id()) {

                Restaurant nuevoRest = em.find(Restaurant.class, dto.getRestaurante_id());
                if (nuevoRest == null) throw new ValidacionException("El nuevo restaurante no existe.");

                existente.setRestaurant(nuevoRest);
            }

            em.getTransaction().commit();
            return MesaMapper.toDTO(existente);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new ValidacionException("Error al actualizar mesa: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Valida que los atributos numéricos de la mesa cumplan con las reglas lógicas del negocio.
     *
     * @param dto El objeto {@link MesaDTO} a validar.
     * @throws ValidacionException Si el número de mesa, la capacidad o el ID del restaurante son menores o iguales a cero.
     */
    private void validarDatosMesa(MesaDTO dto) throws ValidacionException {
        if (dto.getNumeroMesa() <= 0) {
            throw new ValidacionException("El número debe ser mayor a 0.");
        }

        if (dto.getCapacidad() <= 0) {
            throw new ValidacionException("La capacidad de la mesa debe ser de al menos 1 persona.");
        }

        if (dto.getRestaurante_id() == null || dto.getRestaurante_id() <= 0) {
            throw new ValidacionException("Debe asignar la mesa a un restaurante válido.");
        }
    }
}