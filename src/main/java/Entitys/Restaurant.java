package Entitys;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entidad JPA que representa a un Restaurante en el sistema.
 * Esta clase está mapeada a la tabla "Restaurante" en la base de datos.
 * Gestiona la información básica del local comercial y establece relaciones complejas:
 * una relación uno a uno con su dirección (Address), una relación uno a muchos
 * con sus mesas (DiningTable) y una relación muchos a muchos con sus clientes (Customer).
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Table(name = "Restaurante")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long restaurant_id;

    @Column(name = "Nombre", nullable = false)
    private String name;

    @Column(name = "Telefono", length = 10)
    private String telephone;

    @Column(name = "TipoCocina", nullable = false)
    private String kitchenType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiningTable> diningTables;

    @ManyToMany
    @JoinTable(name = "restaurant_customer",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

    /**
     * Constructor que inicializa los datos básicos de un nuevo restaurante.
     * Ideal para crear instancias antes de ser persistidas y antes de asignar
     * relaciones complejas como la dirección o las mesas.
     *
     * @param name        El nombre comercial del restaurante.
     * @param telephone   El número de teléfono de contacto (máximo 10 caracteres).
     * @param kitchenType La especialidad gastronómica o tipo de cocina que ofrece.
     */
    public Restaurant(String name, String telephone, String kitchenType) {
        this.name = name;
        this.telephone = telephone;
        this.kitchenType = kitchenType;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad Restaurant.
     */
    public Restaurant() {
    }

    /**
     * Obtiene la dirección física del restaurante.
     *
     * @return El objeto {@link Address} asociado.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Establece la dirección física del restaurante.
     *
     * @param address La nueva dirección a asociar.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Obtiene la lista de mesas pertenecientes a este restaurante.
     *
     * @return Una lista de entidades {@link DiningTable}.
     */
    public List<DiningTable> getDiningTables() {
        return diningTables;
    }

    /**
     * Establece la lista de mesas del restaurante.
     *
     * @param diningTables La nueva lista de mesas a asociar.
     */
    public void setDiningTables(List<DiningTable> diningTables) {
        this.diningTables = diningTables;
    }

    /**
     * Obtiene la lista de clientes asociados o que han visitado este restaurante.
     *
     * @return Una lista de entidades {@link Customer}.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Establece la lista de clientes del restaurante.
     *
     * @param customers La nueva lista de clientes a asociar.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Obtiene el identificador único del restaurante.
     *
     * @return El ID del restaurante autogenerado por la base de datos.
     */
    public long getRestaurant_id() {
        return restaurant_id;
    }

    /**
     * Establece el identificador único del restaurante.
     *
     * @param restaurant_id El nuevo ID a asignar.
     */
    public void setRestaurant_id(long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    /**
     * Obtiene el nombre comercial del restaurante.
     *
     * @return El nombre del restaurante.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre comercial del restaurante.
     *
     * @param name El nuevo nombre a asignar.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el número de teléfono de contacto del restaurante.
     *
     * @return El número de teléfono.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Establece el número de teléfono de contacto del restaurante.
     *
     * @param telephone El nuevo número de teléfono a asignar.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Obtiene la especialidad gastronómica o tipo de cocina del restaurante.
     *
     * @return El tipo de cocina.
     */
    public String getKitchenType() {
        return kitchenType;
    }

    /**
     * Establece la especialidad gastronómica o tipo de cocina del restaurante.
     *
     * @param kitchenType El nuevo tipo de cocina a asignar.
     */
    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }
}