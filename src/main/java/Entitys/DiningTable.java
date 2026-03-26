package Entitys;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa una mesa física (DiningTable) dentro de un restaurante.
 * Esta clase está mapeada a la tabla "Mesa" en la base de datos.
 * Gestiona la información básica de la mesa como su número y capacidad,
 * así como sus relaciones con el restaurante al que pertenece y la reservación asignada.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Table(name = "Mesa")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long table_id;

    @Column(name = "Numero", nullable = false)
    private int number;

    @Column(name = "Capacidad", nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "diningTable")
    private Reservation reservation;

    /**
     * Constructor que inicializa los datos básicos de una nueva mesa.
     * Ideal para crear instancias antes de ser persistidas en la base de datos.
     *
     * @param number   El número físico o lógico asignado a la mesa.
     * @param capacity La capacidad máxima de comensales que la mesa puede acomodar.
     */
    public DiningTable(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad DiningTable.
     */
    public DiningTable() {
    }

    /**
     * Obtiene el restaurante al que pertenece esta mesa.
     *
     * @return El objeto {@link Restaurant} asociado.
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Establece el restaurante al que pertenece esta mesa.
     *
     * @param restaurant El nuevo restaurante a asociar.
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Obtiene la reservación asignada actualmente a esta mesa.
     *
     * @return El objeto {@link Reservation} asociado, o {@code null} si no tiene reservación.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Establece la reservación para esta mesa.
     *
     * @param reservation La nueva reservación a vincular.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Obtiene el identificador único de la mesa.
     *
     * @return El ID de la mesa autogenerado por la base de datos.
     */
    public long getTable_id() {
        return table_id;
    }

    /**
     * Establece el identificador único de la mesa.
     *
     * @param table_id El nuevo ID a asignar.
     */
    public void setTable_id(long table_id) {
        this.table_id = table_id;
    }

    /**
     * Obtiene el número de identificación de la mesa.
     *
     * @return El número de la mesa.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Establece el número de identificación de la mesa.
     *
     * @param number El nuevo número a asignar.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Obtiene la capacidad máxima de la mesa.
     *
     * @return La cantidad de comensales permitidos.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Establece la capacidad máxima de la mesa.
     *
     * @param capacity La nueva capacidad a asignar.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}