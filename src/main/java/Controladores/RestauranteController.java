package Controladores;

import Interfaces.IRestauranteDAO;
import DAOs.RestauranteDAO;
import DTOs.RestauranteDTO;
import Excepciones.ValidacionException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de la entidad Restaurante y su respectiva dirección.
 * Actúa como puente de comunicación entre la interfaz gráfica (GUI) y la capa de acceso a datos (DAO),
 * procesando y convirtiendo las cadenas de texto capturadas en los tipos de datos correspondientes.
 * @author Jesus Manuel Martinez Cortez
 */
public class RestauranteController {

    private IRestauranteDAO restauranteDAO = new RestauranteDAO();

    /**
     * Procesa y valida los datos capturados en la vista para registrar un nuevo restaurante.
     * Convierte los campos de texto a valores numéricos y construye el objeto de transferencia de datos.
     *
     * @param nombre     El nombre del restaurante.
     * @param telefono   El número de teléfono de contacto.
     * @param tipoCocina La especialidad gastronómica o tipo de cocina.
     * @param calle      El nombre de la calle donde se ubica el restaurante.
     * @param numeroStr  El número exterior de la dirección, en formato de texto.
     * @param cpStr      El código postal, en formato de texto.
     * @param ciudad     La ciudad de ubicación.
     * @param estado     El estado o entidad federativa.
     * @return Un objeto {@link RestauranteDTO} con los datos del restaurante guardado y su ID generado.
     * @throws ValidacionException Si los datos proporcionados no cumplen con las reglas del negocio.
     * @throws NumberFormatException Si el número de dirección o el código postal no son números enteros válidos.
     */
    public RestauranteDTO guardarRestaurante(String nombre, String telefono, String tipoCocina,
                                             String calle, String numeroStr, String cpStr,
                                             String ciudad, String estado)
            throws ValidacionException, NumberFormatException {

        int numero = Integer.parseInt(numeroStr.trim());
        int cp = Integer.parseInt(cpStr.trim());

        RestauranteDTO dto = new RestauranteDTO(null, nombre, telefono, tipoCocina, calle, numero, cp, ciudad, estado);

        return restauranteDAO.agregar(dto);
    }

    /**
     * Procesa y valida los datos para actualizar la información de un restaurante existente.
     *
     * @param idStr      El identificador único del restaurante a actualizar, en formato de texto.
     * @param nombre     El nuevo nombre del restaurante.
     * @param tel        El nuevo número de teléfono.
     * @param cocina     El nuevo tipo de cocina.
     * @param calle      La nueva calle de la dirección.
     * @param num        El nuevo número exterior de la dirección, en formato de texto.
     * @param cp         El nuevo código postal, en formato de texto.
     * @param ciudad     La nueva ciudad.
     * @param edo        El nuevo estado.
     * @return Un objeto {@link RestauranteDTO} con los datos ya actualizados en la base de datos.
     * @throws ValidacionException Si la información actualizada infringe alguna regla de negocio.
     * @throws NumberFormatException Si el ID, número de dirección o código postal no son numéricos válidos.
     */
    public RestauranteDTO actualizarRestaurante(String idStr, String nombre, String tel, String cocina,
                                                String calle, String num, String cp, String ciudad, String edo)
            throws ValidacionException, NumberFormatException {
        Long id = Long.parseLong(idStr.trim());
        int numero = Integer.parseInt(num.trim());
        int codigoP = Integer.parseInt(cp.trim());

        RestauranteDTO dto = new RestauranteDTO(id, nombre, tel, cocina, calle, numero, codigoP, ciudad, edo);
        return restauranteDAO.actualizar(dto);
    }

    /**
     * Recupera la lista completa de todos los restaurantes registrados en el sistema.
     *
     * @return Una lista de objetos {@link RestauranteDTO} con la información de los restaurantes.
     */
    public List<RestauranteDTO> obtenerTodos() {
        return restauranteDAO.consultarTodos();
    }

    /**
     * Busca un restaurante específico utilizando su identificador único.
     *
     * @param idStr El identificador del restaurante en formato de texto.
     * @return El objeto {@link RestauranteDTO} correspondiente, o {@code null} si la cadena proporcionada está vacía.
     * @throws NumberFormatException Si el identificador no tiene un formato numérico válido.
     */
    public RestauranteDTO buscarPorId(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return null;
        return restauranteDAO.consultarPorId(Long.parseLong(idStr.trim()));
    }

    /**
     * Elimina un registro de restaurante de la base de datos de manera definitiva.
     *
     * @param idStr El identificador del restaurante a eliminar en formato de texto.
     * @return {@code true} si la eliminación se realizó con éxito, {@code false} si el ID estaba vacío o no se encontró.
     * @throws NumberFormatException Si el identificador proporcionado no es numérico.
     */
    public boolean eliminarRestaurante(String idStr) throws NumberFormatException {
        if (idStr == null || idStr.trim().isEmpty()) return false;
        return restauranteDAO.eliminar(Long.parseLong(idStr.trim()));
    }
}