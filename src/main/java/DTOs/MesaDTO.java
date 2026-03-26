package DTOs;

/**
 * Objeto de Transferencia de Datos (DTO) que representa una Mesa (Dining Table).
 * Se utiliza para transportar la información de las mesas entre la interfaz gráfica (GUI)
 * y las capas de lógica o acceso a datos. Mantiene la referencia al restaurante mediante
 * su ID en lugar de cargar toda la entidad completa.
 * @author Jesus Manuel Martinez Cortez
 */
public class MesaDTO {

    private Long mesa_id;
    private int numeroMesa;
    private int capacidad;
    private Long restaurante_id;

    /**
     * Constructor que inicializa todos los atributos de la mesa.
     *
     * @param mesa_id        El identificador único de la mesa en la base de datos (puede ser nulo si la mesa es nueva).
     * @param numeroMesa     El número físico o lógico asignado a la mesa dentro del restaurante.
     * @param capacidad      La cantidad máxima de personas que pueden ocupar la mesa.
     * @param restaurante_id El identificador único del restaurante al que pertenece esta mesa.
     */
    public MesaDTO(Long mesa_id, int numeroMesa, int capacidad, Long restaurante_id) {
        this.mesa_id = mesa_id;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.restaurante_id = restaurante_id;
    }

    /**
     * Constructor por defecto.
     * Crea una instancia vacía de MesaDTO sin inicializar sus atributos.
     */
    public MesaDTO() {
    }

    /**
     * Obtiene el identificador único de la mesa.
     *
     * @return El ID de la mesa.
     */
    public Long getMesa_id() {
        return mesa_id;
    }

    /**
     * Establece el identificador único de la mesa.
     *
     * @param mesa_id El nuevo ID a asignar a la mesa.
     */
    public void setMesa_id(Long mesa_id) {
        this.mesa_id = mesa_id;
    }

    /**
     * Obtiene el número físico o de identificación asignado a la mesa.
     *
     * @return El número de la mesa.
     */
    public int getNumeroMesa() {
        return numeroMesa;
    }

    /**
     * Establece el número físico o de identificación de la mesa.
     *
     * @param numeroMesa El nuevo número a asignar.
     */
    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /**
     * Obtiene la capacidad máxima de comensales que la mesa puede acomodar.
     *
     * @return La capacidad de la mesa.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad máxima de comensales para la mesa.
     *
     * @param capacidad La nueva capacidad de la mesa.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Obtiene el identificador del restaurante al que está vinculada la mesa.
     *
     * @return El ID del restaurante.
     */
    public Long getRestaurante_id() {
        return restaurante_id;
    }

    /**
     * Establece el identificador del restaurante al que se vinculará la mesa.
     *
     * @param restaurante_id El nuevo ID del restaurante correspondiente.
     */
    public void setRestaurante_id(Long restaurante_id) {
        this.restaurante_id = restaurante_id;
    }
}