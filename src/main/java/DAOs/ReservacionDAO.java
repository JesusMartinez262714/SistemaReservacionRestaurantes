package DAOs;

import DTOs.ReservacionDTO;
import Entitys.DiningTable;
import Entitys.Reservation;
import Excepciones.ReglaNegocioException;
import Excepciones.ValidacionException;
import Interfaces.IReservacionDAO;
import Mappers.ReservacionMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz IReservacionDAO para la gestión de datos de la entidad Reservación.
 * Se encarga de procesar las operaciones CRUD utilizando JPA y aplica las reglas de negocio específicas
 * sobre la capacidad y disponibilidad de las mesas.
 * @author Jesus Manuel Martinez Cortez
 */
public class ReservacionDAO implements IReservacionDAO {

    private EntityManagerFactory emf = Conexion.getEMF();

    /**
     * Registra una nueva reservación en la base de datos tras validar los datos y aplicar las reglas de negocio.
     * Verifica que la mesa solicitada exista y que la cantidad de personas se ajuste a la capacidad de la mesa
     * (ni excediendo el límite ni desperdiciando espacio en mesas demasiado grandes).
     *
     * @param dto El objeto {@link ReservacionDTO} con los detalles de la reservación (fecha, hora, cliente, mesa y personas).
     * @return El objeto {@link ReservacionDTO} ya persistido con su ID generado.
     * @throws ValidacionException Si los datos básicos están incompletos o la mesa no existe.
     * @throws ReglaNegocioException Si la cantidad de personas excede la capacidad de la mesa o si es demasiado pequeña para justificar una mesa grande.
     */
    @Override
    public ReservacionDTO agregar(ReservacionDTO dto) throws ValidacionException, ReglaNegocioException {
        validarDatosBasicos(dto);

        EntityManager em = emf.createEntityManager();
        try {
            DiningTable mesaAsignada = em.find(DiningTable.class, dto.getMesa_id());

            if (mesaAsignada == null) {
                throw new ValidacionException("La mesa seleccionada no existe.");
            }

            int capacidadDeLaMesa = mesaAsignada.getCapacity();
            int personasQueVan = dto.getCantidadPersonas();

            if (personasQueVan > capacidadDeLaMesa) {
                throw new ReglaNegocioException("La mesa seleccionada tiene capacidad máxima para "
                        + capacidadDeLaMesa + " personas. No se puede reservar para " + personasQueVan + " personas.");
            }

            if (personasQueVan < (capacidadDeLaMesa - 2)) {
                throw new ReglaNegocioException("La mesa es demasiado grande ("
                        + capacidadDeLaMesa + " personas). Para " + personasQueVan +
                        " personas, el sistema le solicita elegir una mesa más pequeña para optimizar el espacio.");
            }

            Reservation entidad = ReservacionMapper.toEntity(dto);
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();

            return ReservacionMapper.toDTO(entidad);

        } catch (ValidacionException | ReglaNegocioException e) {
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new ValidacionException("Error en la base de datos: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Recupera el historial completo de reservaciones almacenadas en el sistema.
     *
     * @return Una lista de objetos {@link ReservacionDTO} con la información de todas las reservaciones.
     */
    @Override
    public List<ReservacionDTO> consultarTodos(){
        EntityManager em = emf.createEntityManager();
        List<ReservacionDTO> listaDTOs = new ArrayList<>();
        try{
            List<Reservation> reservacionBD = em.createQuery("SELECT r FROM Reservation r",Reservation.class).getResultList();
            for(Reservation r : reservacionBD){
                listaDTOs.add(ReservacionMapper.toDTO(r));
            }
        }finally {
            em.close();
        }
        return listaDTOs;
    }

    /**
     * Busca una reservación específica utilizando su identificador único.
     *
     * @param id El identificador único de la reservación.
     * @return El objeto {@link ReservacionDTO} si la reservación existe, o {@code null} en caso contrario.
     */
    @Override
    public ReservacionDTO consultarPorId(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            Reservation reservation = em.find(Reservation.class,id);
            return ReservacionMapper.toDTO(reservation);
        }finally {
            em.close();
        }
    }

    /**
     * Elimina físicamente una reservación (cancelación) de la base de datos.
     *
     * @param id El identificador único de la reservación a eliminar.
     * @return {@code true} si la reservación se eliminó correctamente, {@code false} si no fue encontrada.
     */
    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Reservation reservacion = em.find(Reservation.class, id);
            if (reservacion != null) {
                em.remove(reservacion);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Realiza una validación inicial de los campos obligatorios antes de intentar procesar la reservación.
     *
     * @param dto El objeto {@link ReservacionDTO} que contiene los datos capturados.
     * @throws ValidacionException Si faltan datos críticos como fecha, hora, IDs de relación o si la cantidad de personas es inválida.
     */
    private void validarDatosBasicos(ReservacionDTO dto) throws ValidacionException {
        if (dto.getFecha() == null) {
            throw new ValidacionException("La fecha de reservación es obligatoria.");
        }
        if (dto.getHora() == null) {
            throw new ValidacionException("La hora de reservación es obligatoria.");
        }
        if (dto.getCliente_id() == null || dto.getMesa_id() == null) {
            throw new ValidacionException("Debe seleccionar un cliente y una mesa válidos.");
        }
        if (dto.getCantidadPersonas() <= 0) {
            throw new ValidacionException("La cantidad de personas debe ser mayor a cero.");
        }
    }
}