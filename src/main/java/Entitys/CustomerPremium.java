package Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

/**
 * Entidad JPA que representa a un cliente con membresía premium en el sistema.
 * Hereda de la entidad base {@link Customer} bajo la estrategia de Tabla Única (SINGLE_TABLE).
 * Dado que esta clase hija tiene muy pocos atributos adicionales específicos (puntos y nivel),
 * el impacto de dejar espacios en blanco (valores nulos en la base de datos para clientes normales)
 * es mínimo. No se desperdicia casi nada de espacio y, a cambio, se gana mucho rendimiento
 * en las consultas al evitar unir tablas con JOIN.
 * Gracias a la columna discriminadora, JPA sabe por sí solo si el registro es normal o premium
 * armando este objeto automáticamente bajo el valor "PREMIUM", sin necesidad de código extra de mapeo.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@DiscriminatorValue("PREMIUM")
public class CustomerPremium extends Customer {

    @Column(name = "Puntos")
    private int points;

    @Column(name = "Nivel")
    private int level;

    /**
     * Constructor que inicializa un nuevo cliente premium con todos sus datos y los heredados de la clase base.
     *
     * @param name       El nombre completo del cliente.
     * @param email      El correo electrónico del cliente.
     * @param telephones La lista de números de teléfono asociados.
     * @param points     La cantidad inicial de puntos acumulados por la membresía.
     * @param level      El nivel asignado al cliente premium.
     */
    public CustomerPremium(String name, String email, List<Telephone> telephones, int points, int level) {
        super(name, email, telephones);
        this.points = points;
        this.level = level;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad CustomerPremium.
     */
    public CustomerPremium() {
    }

    /**
     * Obtiene la cantidad de puntos acumulados por el cliente.
     *
     * @return Los puntos de la membresía premium.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Establece la cantidad de puntos para el cliente.
     *
     * @param points Los nuevos puntos a asignar.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Obtiene el nivel de la membresía premium del cliente.
     *
     * @return El nivel actual.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Establece el nivel de la membresía premium.
     *
     * @param level El nuevo nivel a asignar.
     */
    public void setLevel(int level) {
        this.level = level;
    }
}