package Entitys;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la dirección física de un restaurante.
 * Esta clase está mapeada a la tabla "Direccion" en la base de datos
 * y se encarga de almacenar los datos de ubicación.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Table(name = "Direccion")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long address_id;

    @Column(name = "Calle", length = 100)
    private String street;

    @Column(name = "Estado", length = 100)
    private String state;

    @Column(name = "CodigoPostal", nullable = false)
    private int cp;

    @Column(name = "Ciudad", length = 100)
    private String city;

    @Column(name = "Numero", nullable = false)
    private int number;

    /**
     * Constructor que inicializa los atributos de la dirección sin el identificador.
     * Ideal para crear nuevas direcciones antes de ser persistidas en la base de datos.
     *
     * @param street El nombre de la calle (máximo 100 caracteres).
     * @param state  El estado o entidad federativa (máximo 100 caracteres).
     * @param cp     El código postal de la ubicación (obligatorio).
     * @param city   La ciudad de la ubicación (máximo 100 caracteres).
     * @param number El número exterior y/o interior del local (obligatorio).
     */
    public Address(String street, String state, int cp, String city, int number) {
        this.street = street;
        this.state = state;
        this.cp = cp;
        this.city = city;
        this.number = number;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de Address.
     */
    public Address() {
    }

    /**
     * Obtiene el identificador único de la dirección (Llave primaria).
     *
     * @return El ID de la dirección autogenerado por la base de datos.
     */
    public long getAddress_id() {
        return address_id;
    }

    /**
     * Establece el identificador único de la dirección.
     *
     * @param address_id El nuevo ID a asignar.
     */
    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    /**
     * Obtiene el nombre de la calle.
     *
     * @return El nombre de la calle.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Establece el nombre de la calle.
     *
     * @param street El nuevo nombre de la calle a asignar.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Obtiene el estado o entidad federativa.
     *
     * @return El nombre del estado.
     */
    public String getState() {
        return state;
    }

    /**
     * Establece el estado o entidad federativa.
     *
     * @param state El nuevo estado a asignar.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtiene el código postal de la dirección.
     *
     * @return El código postal.
     */
    public int getCp() {
        return cp;
    }

    /**
     * Establece el código postal de la dirección.
     *
     * @param cp El nuevo código postal a asignar.
     */
    public void setCp(int cp) {
        this.cp = cp;
    }

    /**
     * Obtiene el nombre de la ciudad.
     *
     * @return El nombre de la ciudad.
     */
    public String getCity() {
        return city;
    }

    /**
     * Establece el nombre de la ciudad.
     *
     * @param city El nuevo nombre de la ciudad a asignar.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtiene el número exterior (e interior si aplica) de la dirección.
     *
     * @return El número de la dirección.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Establece el número de la dirección.
     *
     * @param number El nuevo número a asignar.
     */
    public void setNumber(int number) {
        this.number = number;
    }
}