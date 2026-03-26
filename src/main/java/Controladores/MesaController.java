package Controladores;

import Interfaces.IMesaDAO;
import DAOs.MesaDAO;
import DTOs.MesaDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de la entidad Mesa.
 * Sirve como intermediario entre la interfaz gráfica (GUI) y la capa de acceso a datos (DAO),
 * procesando entradas de texto y convirtiéndolas a los tipos de datos requeridos por el sistema.
 * * @author Jesus Manuel Martinez Cortez
 */
public class MesaController {
    private IMesaDAO mesaDAO = new MesaDAO();

    /**
     * Procesa y valida los datos capturados en la vista para registrar una nueva mesa.
     * Convierte las cadenas de texto a valores numéricos antes de enviarlos a la capa de persistencia.
     *
     * @param num    El número asignado a la mesa en formato de texto.
     * @param cap    La capacidad de personas que admite la mesa en formato de texto.
     * @param idRest El identificador del restaurante al que pertenece la mesa en formato de texto.
     * @return Un objeto {@link MesaDTO} con los datos guardados y el ID generado por la base de datos.
     * @throws ValidacionException Si los datos ingresados no cumplen con las reglas de negocio (ej. el restaurante no existe).
     * @throws NumberFormatException Si alguno de los parámetros no es un número entero válido.
     */
    public MesaDTO guardarMesa(String num, String cap, String idRest) throws ValidacionException, NumberFormatException {
        int numeroMesa = Integer.parseInt(num.trim());
        int capacidad = Integer.parseInt(cap.trim());
        Long idRestaurante = Long.parseLong(idRest.trim());

        MesaDTO nuevaMesaDTO = new MesaDTO(null, numeroMesa, capacidad, idRestaurante);
        return mesaDAO.agregar(nuevaMesaDTO);
    }

    /**
     * Recupera la lista completa de todas las mesas registradas en el sistema.
     *
     * @return Una lista de objetos {@link MesaDTO} con la información de cada mesa.
     */
    public List<MesaDTO> obtenerTodas() {
        return mesaDAO.consultarTodos();
    }

    /**
     * Procesa y valida los datos capturados para actualizar la información de una mesa existente.
     *
     * @param idStr  El identificador único de la mesa a actualizar en formato de texto.
     * @param num    El nuevo número asignado a la mesa.
     * @param cap    La nueva capacidad de personas de la mesa.
     * @param idRest El identificador del restaurante al que estará asignada la mesa.
     * @return Un objeto {@link MesaDTO} reflejando los datos ya actualizados.
     * @throws ValidacionException Si los nuevos datos infringen las reglas de negocio.
     * @throws NumberFormatException Si alguno de los parámetros no tiene un formato numérico válido.
     */
    public MesaDTO actualizarMesa(String idStr, String num, String cap, String idRest)
            throws ValidacionException, NumberFormatException {

        Long id = Long.parseLong(idStr.trim());
        int numeroMesa = Integer.parseInt(num.trim());
        int capacidad = Integer.parseInt(cap.trim());
        Long idRestaurante = Long.parseLong(idRest.trim());

        MesaDTO dto = new MesaDTO(id, numeroMesa, capacidad, idRestaurante);
        return mesaDAO.actualizar(dto);
    }

    /**
     * Busca una mesa específica utilizando su identificador único.
     *
     * @param idStr El identificador de la mesa en formato de texto.
     * @return El objeto {@link MesaDTO} correspondiente al ID, o {@code null} si la cadena está vacía o es nula.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public MesaDTO buscarPorId(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return null;
        return mesaDAO.consultarPorId(Long.parseLong(idStr.trim()));
    }

    /**
     * Elimina un registro de mesa de la base de datos de manera definitiva.
     *
     * @param idStr El identificador de la mesa a eliminar en formato de texto.
     * @return {@code true} si la eliminación fue exitosa, {@code false} si el ID estaba vacío o no se encontró.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public boolean eliminarMesa(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return false;
        return mesaDAO.eliminar(Long.parseLong(idStr.trim()));
    }
}