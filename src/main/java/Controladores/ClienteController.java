package Controladores;

import DAOs.ClienteDAO;
import Interfaces.IClienteDAO;
import DTOs.ClienteDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de la entidad Cliente.
 * Actúa como intermediario entre la interfaz gráfica (GUI) y la capa de acceso a datos (DAO),
 * recibiendo cadenas de texto, validando los formatos numéricos y construyendo los objetos DTO.
 * @author Jesus Manuel Martinez Cortez
 */
public class ClienteController {

    private IClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Procesa los datos capturados en la vista para registrar un nuevo cliente.
     * Convierte los valores numéricos correspondientes si el cliente cuenta con membresía premium.
     *
     * @param nombre    El nombre completo del cliente.
     * @param email     El correo electrónico del cliente.
     * @param telefono  El número de teléfono de contacto.
     * @param esPremium Indicador booleano sobre si el cliente es premium.
     * @param puntosStr La cantidad de puntos en formato de texto (se ignora si no es premium).
     * @param nivelStr  El nivel del cliente en formato de texto (se ignora si no es premium).
     * @return Un objeto {@link ClienteDTO} con los datos persistidos y el ID generado.
     * @throws ValidacionException Si los datos ingresados no cumplen con las reglas de negocio.
     * @throws NumberFormatException Si los campos de puntos o nivel no son números enteros válidos.
     */
    public ClienteDTO guardarCliente(String nombre, String email, String telefono,
                                     boolean esPremium, String puntosStr, String nivelStr)
            throws ValidacionException, NumberFormatException {

        int puntos = 0;
        int nivel = 0;

        if (esPremium) {
            puntos = Integer.parseInt(puntosStr.trim());
            nivel = Integer.parseInt(nivelStr.trim());
        }

        ClienteDTO dto = new ClienteDTO(null, nombre, email, telefono, esPremium, puntos, nivel);

        return clienteDAO.agregar(dto);
    }

    /**
     * Recupera la lista completa de clientes registrados en el sistema.
     *
     * @return Una lista de objetos {@link ClienteDTO} con la información de todos los clientes.
     */
    public List<ClienteDTO> obtenerTodos() {
        return clienteDAO.consultarTodos();
    }

    /**
     * Busca un cliente específico utilizando su identificador único.
     *
     * @param idStr El identificador del cliente en formato de texto.
     * @return El objeto {@link ClienteDTO} correspondiente al ID, o {@code null} si el campo está vacío o no se encuentra.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public ClienteDTO buscarPorId(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return null;

        Long id = Long.parseLong(idStr.trim());
        return clienteDAO.consultarPorId(id);
    }

    /**
     * Elimina un registro de cliente de la base de datos de manera definitiva.
     *
     * @param idStr El identificador del cliente a eliminar en formato de texto.
     * @return {@code true} si la eliminación fue exitosa, {@code false} si el ID estaba vacío o no se encontró.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public boolean eliminarCliente(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return false;

        Long id = Long.parseLong(idStr.trim());
        return clienteDAO.eliminar(id);
    }

    /**
     * Actualiza la información de un cliente previamente registrado.
     * Convierte el ID y los atributos premium desde sus representaciones en texto antes de delegar la actualización.
     *
     * @param idStr     El identificador único del cliente existente.
     * @param nombre    El nombre actualizado.
     * @param email     El correo electrónico actualizado.
     * @param telefono  El número de teléfono actualizado.
     * @param esPremium Estado actualizado de la membresía premium.
     * @param puntosStr Los puntos actualizados en formato de texto.
     * @param nivelStr  El nivel actualizado en formato de texto.
     * @return Un objeto {@link ClienteDTO} reflejando los datos ya actualizados en la persistencia.
     * @throws ValidacionException Si los nuevos datos infringen las reglas de negocio.
     * @throws NumberFormatException Si el ID, los puntos o el nivel no tienen un formato numérico válido.
     */
    public ClienteDTO actualizarCliente(String idStr, String nombre, String email, String telefono,
                                        boolean esPremium, String puntosStr, String nivelStr)
            throws ValidacionException, NumberFormatException {

        Long id = Long.parseLong(idStr.trim());
        int puntos = esPremium ? Integer.parseInt(puntosStr.trim()) : 0;
        int nivel = esPremium ? Integer.parseInt(nivelStr.trim()) : 0;

        ClienteDTO dto = new ClienteDTO(id, nombre, email, telefono, esPremium, puntos, nivel);

        return clienteDAO.actualizar(dto);
    }
}