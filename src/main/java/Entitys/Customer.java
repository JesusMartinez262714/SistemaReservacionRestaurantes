package Entitys;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entidad JPA que representa a un cliente base en el sistema.
 * Implementa la estrategia de herencia de Tabla Única (SINGLE_TABLE), lo que permite
 * almacenar toda la jerarquía de clientes (regulares y premium) en una sola tabla.
 * Esta arquitectura favorece la simplicidad y aumenta el rendimiento de lectura,
 * ya que las consultas son más rápidas al evitar la unión de tablas mediante JOIN.
 * * Utiliza la columna discriminadora "tipo_cliente" (con valor "NORMAL" por defecto)
 * para que JPA identifique e instancie automáticamente el tipo correcto de objeto
 * sin necesidad de escribir código adicional de mapeo.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("NORMAL")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long customer_id;

    @Column(name = "Nombre", length = 50, nullable = false)
    protected String name;

    @Column(name = "Correo", length = 100, nullable = false)
    protected String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    protected List<Telephone> telephones;

    @ManyToMany(mappedBy = "customers")
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Reservation> reservations;

    /**
     * Constructor que inicializa los datos básicos de un nuevo cliente.
     * Ideal para crear instancias antes de ser persistidas, excluyendo relaciones complejas como reservaciones.
     *
     * @param name       El nombre completo del cliente (máximo 50 caracteres).
     * @param email      El correo electrónico de contacto (máximo 100 caracteres).
     * @param telephones La lista de números de teléfono asociados al cliente.
     */
    public Customer(String name, String email, List<Telephone> telephones) {
        this.name = name;
        this.email = email;
        this.telephones = telephones;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad Customer.
     */
    public Customer() {
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return El ID del cliente generado por la base de datos.
     */
    public long getCustomer_id() {
        return customer_id;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param customer_id El nuevo ID a asignar.
     */
    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
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
     * @param name El nuevo nombre a asignar.
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
     * Obtiene la lista de teléfonos asociados a este cliente.
     *
     * @return Una lista de entidades {@link Telephone}.
     */
    public List<Telephone> getTelephones() {
        return telephones;
    }

    /**
     * Establece la lista de teléfonos del cliente.
     *
     * @param telephones La nueva lista de teléfonos a asociar.
     */
    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }
}