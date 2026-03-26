package Controladores;

import DAOs.*;
import DTOs.*;
import Excepciones.*;
import Interfaces.*;
import java.util.Date;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con las reservaciones.
 * Coordina la comunicación entre la interfaz gráfica y las capas de acceso a datos (DAO)
 * de Reservación, Cliente y Mesa.
 * @author Jesus Manuel Martinez Cortez
 */
public class ReservacionController {

    private IReservacionDAO reservacionDAO = new ReservacionDAO();
    private IClienteDAO clienteDAO = new ClienteDAO();
    private IMesaDAO mesaDAO = new MesaDAO();

    /**
     * Procesa y valida los datos capturados en la vista para registrar una nueva reservación.
     * Convierte los identificadores y cantidades de texto a sus tipos numéricos correspondientes
     * y delega la persistencia al DAO.
     *
     * @param fecha        La fecha para la cual se solicita la reservación.
     * @param hora         La hora específica de la reservación.
     * @param idClienteStr El identificador del cliente que realiza la reservación, en formato de texto.
     * @param idMesaStr    El identificador de la mesa solicitada, en formato de texto.
     * @param personasStr  La cantidad de personas que asistirán, en formato de texto.
     * @return Un objeto {@link ReservacionDTO} con los datos de la reservación generada y guardada.
     * @throws ValidacionException Si los datos ingresados son inválidos.
     * @throws ReglaNegocioException Si se infringe alguna regla del negocio (ej. la mesa no tiene capacidad suficiente).
     * @throws NumberFormatException Si los identificadores o la cantidad de personas no tienen un formato numérico válido.
     */
    public ReservacionDTO guardarReservacion(Date fecha, Date hora, String idClienteStr,
                                             String idMesaStr, String personasStr)
            throws ValidacionException, ReglaNegocioException, NumberFormatException {

        Long idCliente = Long.parseLong(idClienteStr.trim());
        Long idMesa = Long.parseLong(idMesaStr.trim());
        int personas = Integer.parseInt(personasStr.trim());

        ReservacionDTO dto = new ReservacionDTO();
        dto.setFecha(fecha);
        dto.setHora(hora);
        dto.setCliente_id(idCliente);
        dto.setMesa_id(idMesa);
        dto.setCantidadPersonas(personas);

        return reservacionDAO.agregar(dto);
    }

    /**
     * Recupera la lista completa de clientes registrados para su uso en la interfaz gráfica
     * (por ejemplo, para llenar un componente desplegable).
     *
     * @return Una lista de objetos {@link ClienteDTO} con la información de todos los clientes.
     */
    public List<ClienteDTO> obtenerClientes() {
        return clienteDAO.consultarTodos();
    }

    /**
     * Recupera la lista completa de mesas registradas en el sistema para su uso en la interfaz gráfica.
     *
     * @return Una lista de objetos {@link MesaDTO} con la información de todas las mesas.
     */
    public List<MesaDTO> obtenerMesas() {
        return mesaDAO.consultarTodos();
    }

    /**
     * Recupera la lista completa de todas las reservaciones almacenadas en el sistema.
     *
     * @return Una lista de objetos {@link ReservacionDTO} con la información de las reservaciones.
     */
    public List<ReservacionDTO> obtenerTodas() {
        return reservacionDAO.consultarTodos();
    }

    /**
     * Busca una reservación específica utilizando su identificador único.
     *
     * @param idStr El identificador de la reservación en formato de texto.
     * @return El objeto {@link ReservacionDTO} correspondiente, o {@code null} si el parámetro es nulo o está vacío.
     * @throws NumberFormatException Si el identificador proporcionado no es un número válido.
     */
    public ReservacionDTO buscarPorId(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return null;
        return reservacionDAO.consultarPorId(Long.parseLong(idStr.trim()));
    }

    /**
     * Cancela y elimina del sistema una reservación existente mediante su identificador.
     *
     * @param idStr El identificador de la reservación a cancelar, en formato de texto.
     * @return {@code true} si la reservación se canceló y eliminó correctamente, {@code false} en caso contrario.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public boolean cancelarReservacion(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return false;
        return reservacionDAO.eliminar(Long.parseLong(idStr.trim()));
    }
}