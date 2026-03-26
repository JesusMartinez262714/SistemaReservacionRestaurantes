package DTOs;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de un Restaurante.
 * Sirve para transportar los datos entre la capa de presentación (GUI) y la capa de acceso a datos.
 * Este DTO "aplana" la estructura, combinando los datos básicos del restaurante (nombre, teléfono, tipo de cocina)
 * junto con los atributos de su dirección física (calle, número, CP, ciudad, estado) en un solo objeto para facilitar su manejo.
 * @author Jesus Manuel Martinez Cortez
 */
public class RestauranteDTO {

    private Long restaurante_id;
    private String nombre;
    private String telefono;
    private String tipoCocina;

    private String calle;
    private int numero;
    private int cp;
    private String ciudad;
    private String estado;

    /**
     * Constructor que inicializa todos los atributos del restaurante, incluyendo su dirección.
     *
     * @param restaurante_id El identificador único del restaurante (puede ser nulo si es un registro nuevo).
     * @param nombre         El nombre comercial del restaurante.
     * @param telefono       El número de teléfono de contacto (típicamente de 10 dígitos).
     * @param tipoCocina     La especialidad gastronómica que ofrece el restaurante.
     * @param calle          El nombre de la calle donde se ubica el local.
     * @param numero         El número exterior (y/o interior) del local.
     * @param cp             El código postal de la ubicación.
     * @param ciudad         La ciudad donde se encuentra el restaurante.
     * @param estado         El estado o entidad federativa.
     */
    public RestauranteDTO(Long restaurante_id, String nombre, String telefono, String tipoCocina, String calle, int numero, int cp, String ciudad, String estado) {
        this.restaurante_id = restaurante_id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipoCocina = tipoCocina;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.ciudad = ciudad;
        this.estado = estado;
    }

    /**
     * Constructor por defecto.
     * Crea una instancia vacía de RestauranteDTO sin inicializar sus atributos.
     */
    public RestauranteDTO() {
    }

    /**
     * Obtiene el identificador único del restaurante.
     *
     * @return El ID del restaurante.
     */
    public Long getRestaurante_id() {
        return restaurante_id;
    }

    /**
     * Establece el identificador único del restaurante.
     *
     * @param restaurante_id El nuevo ID a asignar al restaurante.
     */
    public void setRestaurante_id(Long restaurante_id) {
        this.restaurante_id = restaurante_id;
    }

    /**
     * Obtiene el nombre comercial del restaurante.
     *
     * @return El nombre del restaurante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre comercial del restaurante.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de teléfono de contacto del restaurante.
     *
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono de contacto del restaurante.
     *
     * @param telefono El nuevo número de teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la especialidad gastronómica o tipo de cocina del restaurante.
     *
     * @return El tipo de cocina.
     */
    public String getTipoCocina() {
        return tipoCocina;
    }

    /**
     * Establece la especialidad gastronómica o tipo de cocina del restaurante.
     *
     * @param tipoCocina El nuevo tipo de cocina a asignar.
     */
    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    /**
     * Obtiene el nombre de la calle de la dirección física del restaurante.
     *
     * @return El nombre de la calle.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece el nombre de la calle de la dirección física del restaurante.
     *
     * @param calle La nueva calle a asignar.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el número exterior del local en su dirección física.
     *
     * @return El número de la dirección.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número exterior del local en su dirección física.
     *
     * @param numero El nuevo número a asignar.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el código postal de la ubicación del restaurante.
     *
     * @return El código postal.
     */
    public int getCp() {
        return cp;
    }

    /**
     * Establece el código postal de la ubicación del restaurante.
     *
     * @param cp El nuevo código postal a asignar.
     */
    public void setCp(int cp) {
        this.cp = cp;
    }

    /**
     * Obtiene la ciudad donde se localiza el restaurante.
     *
     * @return El nombre de la ciudad.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se localiza el restaurante.
     *
     * @param ciudad El nuevo nombre de la ciudad a asignar.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el estado o entidad federativa de la ubicación del restaurante.
     *
     * @return El estado de ubicación.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado o entidad federativa de la ubicación del restaurante.
     *
     * @param estado El nuevo estado a asignar.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}