package DAOs;

import DTOs.ClienteDTO;
import Entitys.Customer;
import Entitys.CustomerPremium;
import Excepciones.ValidacionException;
import Interfaces.IClienteDAO;
import Mappers.ClienteMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz IClienteDAO que gestiona el acceso a datos para la entidad Customer.
 * Utiliza JPA (Java Persistence API) para realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * directamente en la base de datos relacional.
 * @author Jesus Manuel Martinez Cortez
 */
public class ClienteDAO implements IClienteDAO {

    private EntityManagerFactory emf = Conexion.getEMF();

    /**
     * Valida y guarda un nuevo cliente en la base de datos.
     * Mapea el objeto de transferencia (DTO) a una entidad JPA y gestiona la transacción.
     *
     * @param clienteDTO El objeto con los datos del cliente a persistir.
     * @return El objeto {@link ClienteDTO} con los datos guardados y el ID generado por la base de datos.
     * @throws ValidacionException Si los datos del cliente no cumplen con las validaciones obligatorias
     * o si ocurre un error durante la transacción con la base de datos.
     */
    @Override
    public ClienteDTO agregar(ClienteDTO clienteDTO) throws ValidacionException {
        validarDatosCliente(clienteDTO);
        EntityManager em = emf.createEntityManager();
        try {
            Customer entidad = ClienteMapper.toEntity(clienteDTO);

            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            return ClienteMapper.toDTO(entidad);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ValidacionException("Error al guardar en la base de datos");
        } finally {
            em.close();
        }
    }

    /**
     * Recupera una lista con todos los clientes registrados en la base de datos.
     *
     * @return Una lista de objetos {@link ClienteDTO} correspondientes a todos los registros de la tabla Customer.
     */
    @Override
    public List<ClienteDTO> consultarTodos() {
        EntityManager em = emf.createEntityManager();
        List<ClienteDTO> listaDTOs = new ArrayList<>();
        try {
            List<Customer> clientesBD = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            for (Customer c : clientesBD) {
                listaDTOs.add(ClienteMapper.toDTO(c));
            }
        } finally {
            em.close();
        }
        return listaDTOs;
    }

    /**
     * Busca un cliente en la base de datos mediante su identificador único.
     *
     * @param id El identificador único del cliente (Primary Key).
     * @return Un objeto {@link ClienteDTO} si se encuentra el cliente, o {@code null} si no existe.
     */
    @Override
    public ClienteDTO consultarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer cliente = em.find(Customer.class, id);
            return ClienteMapper.toDTO(cliente);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un cliente de la base de datos de manera física.
     *
     * @param id El identificador único del cliente a eliminar.
     * @return {@code true} si el cliente fue encontrado y eliminado exitosamente, {@code false} si no se encontró.
     */
    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer cliente = em.find(Customer.class, id);
            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza los datos básicos e información premium (si aplica) de un cliente ya existente.
     * Recupera la entidad gestionada por JPA para modificar únicamente los atributos necesarios.
     *
     * @param dto El objeto {@link ClienteDTO} que contiene el ID del cliente y los nuevos datos a actualizar.
     * @return Un objeto {@link ClienteDTO} que refleja la información actualizada.
     * @throws ValidacionException Si el cliente no existe en la base de datos o si ocurre un error en la transacción.
     */
    @Override
    public ClienteDTO actualizar(ClienteDTO dto) throws ValidacionException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer existente = em.find(Customer.class, dto.getcliente_id());
            if (existente == null) {
                throw new ValidacionException("El cliente no existe en la base de datos.");
            }
            existente.setName(dto.getName());
            existente.setEmail(dto.getEmail());

            if (existente instanceof CustomerPremium && dto.isEsPremium()) {
                ((CustomerPremium) existente).setPoints(dto.getPuntos());
                ((CustomerPremium) existente).setLevel(dto.getNivel());
            }

            if (dto.getTelefono() != null && !existente.getTelephones().isEmpty()) {
                existente.getTelephones().get(0).setTelephone(dto.getTelefono());
            }

            em.getTransaction().commit();

            return ClienteMapper.toDTO(existente);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            throw new ValidacionException("Error al actualizar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Valida de manera estricta los datos obligatorios y formatos antes de enviarlos a la base de datos.
     * Verifica que el nombre y correo no estén vacíos, además de validar el formato del correo mediante expresiones regulares.
     *
     * @param dto El objeto {@link ClienteDTO} a validar.
     * @throws ValidacionException Si alguno de los campos obligatorios es nulo, está vacío o su formato es incorrecto.
     */
    private void validarDatosCliente(ClienteDTO dto) throws ValidacionException {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ValidacionException("El campo 'Nombre' no puede estar vacío.");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new ValidacionException("El campo 'Email' no puede estar vacío.");
        }

        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidacionException("El formato del correo es incorrecto. Ejemplo válido: usuario@correo.com");
        }

        if (dto.getTelefono() != null && !dto.getTelefono().trim().isEmpty()) {
            if (!dto.getTelefono().matches("^\\d{10}$")) {
                throw new ValidacionException("El teléfono debe tener exactamente 10 dígitos numéricos sin espacios ni guiones (Ejemplo: 6444010101).");
            }
        }
    }
}