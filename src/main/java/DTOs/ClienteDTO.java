package DTOs;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de un Cliente.
 * Se utiliza para transportar los datos entre la capa de presentación (GUI) y la capa de control o persistencia,
 * agrupando tanto los atributos de un cliente regular como los de un cliente premium en una sola estructura unificada.
 * @author Jesus Manuel Martinez Cortez
 */
public class ClienteDTO {

    private Long cliente_id;
    private String name;
    private String email;
    private String telefono;

    private boolean esPremium;
    private int puntos;
    private int nivel;

    /**
     * Constructor por defecto.
     * Crea una instancia vacía de ClienteDTO sin inicializar sus atributos.
     */
    public ClienteDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos del cliente.
     *
     * @param cliente_id El identificador único del cliente (puede ser nulo si el cliente aún no se registra en la base de datos).
     * @param name       El nombre completo del cliente.
     * @param email      El correo electrónico de contacto del cliente.
     * @param telefono   El número de teléfono del cliente a 10 dígitos.
     * @param esPremium  Indicador booleano que define si el cliente cuenta con membresía premium.
     * @param puntos     La cantidad de puntos acumulados por el cliente (relevante si es premium).
     * @param nivel      El nivel actual de la membresía del cliente (relevante si es premium).
     */
    public ClienteDTO(Long cliente_id, String name, String email, String telefono, boolean esPremium, int puntos, int nivel) {
        this.cliente_id = cliente_id;
        this.name = name;
        this.email = email;
        this.telefono = telefono;
        this.esPremium = esPremium;
        this.puntos = puntos;
        this.nivel = nivel;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return El ID del cliente.
     */
    public Long getcliente_id() {
        return cliente_id;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param cliente_id El nuevo ID a asignar al cliente.
     */
    public void setcliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre completo del cliente.
     *
     * @param name El nuevo nombre a asignar al cliente.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return El correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param email El nuevo correo electrónico a asignar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono El nuevo número de teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Verifica si el cliente tiene estatus premium.
     *
     * @return {@code true} si el cliente es premium, {@code false} en caso contrario.
     */
    public boolean isEsPremium() {
        return esPremium;
    }

    /**
     * Establece el estatus premium del cliente.
     *
     * @param esPremium {@code true} para marcar al cliente como premium, {@code false} para cliente regular.
     */
    public void setEsPremium(boolean esPremium) {
        this.esPremium = esPremium;
    }

    /**
     * Obtiene la cantidad de puntos acumulados por el cliente premium.
     *
     * @return Los puntos del cliente.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Establece la cantidad de puntos para el cliente premium.
     *
     * @param puntos Los nuevos puntos a asignar.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Obtiene el nivel de membresía del cliente premium.
     *
     * @return El nivel de membresía.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel de membresía del cliente premium.
     *
     * @param nivel El nuevo nivel a asignar.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}