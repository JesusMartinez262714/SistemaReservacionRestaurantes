package Entitys;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;

/**
 * Entidad JPA que representa una reservación en el sistema.
 * Esta clase está mapeada a la tabla "Reservacion" en la base de datos.
 * Gestiona la relación obligatoria entre un cliente (Customer) y una mesa (DiningTable)
 * para una fecha y hora específicas.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Table(name = "Reservacion")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @Column(name = "Fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "Hora", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date hour;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private DiningTable diningTable;

    /**
     * Constructor que inicializa los datos de fecha y hora de una nueva reservación.
     * Ideal para crear instancias antes de ser persistidas y antes de asignar sus relaciones correspondientes.
     *
     * @param date La fecha en la que se programa la reservación.
     * @param hour La hora exacta de la reservación.
     */
    public Reservation(Date date, Date hour) {
        this.date = date;
        this.hour = hour;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad Reservation.
     */
    public Reservation() {
    }

    /**
     * Obtiene el cliente que realizó la reservación.
     *
     * @return El objeto {@link Customer} asociado.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Establece el cliente que realiza la reservación.
     *
     * @param customer El nuevo cliente a asociar.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Obtiene la mesa asignada para esta reservación.
     *
     * @return El objeto {@link DiningTable} asociado.
     */
    public DiningTable getDiningTable() {
        return diningTable;
    }

    /**
     * Establece la mesa que será ocupada en esta reservación.
     *
     * @param diningTable La nueva mesa a asignar.
     */
    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    /**
     * Obtiene el identificador único de la reservación.
     *
     * @return El ID de la reservación autogenerado por la base de datos.
     */
    public Long getReservation_id() {
        return reservation_id;
    }

    /**
     * Establece el identificador único de la reservación.
     *
     * @param reservation_id El nuevo ID a asignar.
     */
    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    /**
     * Obtiene la fecha programada de la reservación.
     *
     * @return La fecha de la reservación.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Establece la fecha programada de la reservación.
     *
     * @param date La nueva fecha a asignar.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Obtiene la hora programada de la reservación.
     *
     * @return La hora de la reservación.
     */
    public Date getHour() {
        return hour;
    }

    /**
     * Establece la hora programada de la reservación.
     *
     * @param hour La nueva hora a asignar.
     */
    public void setHour(Date hour) {
        this.hour = hour;
    }
}