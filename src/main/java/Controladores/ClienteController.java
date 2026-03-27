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
     * Actualiza la información de un cliente existente en el sistema, permitiendo cambios
     * en sus datos básicos y en su estatus de membresía (Normal o Premium).
     * <p>
     * Este método es el encargado de orquestar la transición de tipos de cliente. Si el
     * parámetro {@code esPremium} es verdadero, se realiza la conversión de los valores
     * de puntos y nivel desde texto a enteros para integrarlos al registro; de lo
     * contrario, se procesa como un cliente regular.
     *
     * @param id         El identificador único del cliente a modificar (en formato String).
     * @param nombre     El nuevo nombre completo del cliente.
     * @param email      La nueva dirección de correo electrónico.
     * @param tel        El nuevo número de teléfono de contacto.
     * @param esPremium  Indica si el cliente debe ser tratado como Premium tras la actualización.
     * @param puntos     La cantidad de puntos acumulados (requerido si esPremium es true).
     * @param nivel      El nivel de lealtad alcanzado (requerido si esPremium es true).
     * * @throws ValidacionException   Si los datos proporcionados son insuficientes o el
     * cliente no existe en la base de datos.
     * @throws NumberFormatException Si el ID, los puntos o el nivel contienen caracteres
     * no numéricos inválidos.
     * @author Jesus Manuel Martinez Cortez
     */
    public void actualizarCliente(String id, String nombre, String email, String tel, boolean esPremium, String puntos, String nivel) {
        ClienteDTO dto = new ClienteDTO();
        dto.setcliente_id(Long.parseLong(id));
        dto.setName(nombre);
        dto.setEmail(email);
        dto.setTelefono(tel);
        dto.setEsPremium(esPremium);

        if (esPremium) {
            dto.setPuntos(Integer.parseInt(puntos));
            dto.setNivel(Integer.parseInt(nivel));
        }

        clienteDAO.actualizar(dto);
    }
}