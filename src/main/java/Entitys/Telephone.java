package Entitys;

import jakarta.persistence.*;

import java.beans.ConstructorProperties;

/**
 * Entidad JPA que representa un número de teléfono asociado a un cliente.
 * Esta clase está mapeada a la tabla "Telefono" en la base de datos.
 * Funciona como una entidad independiente para permitir que un cliente
 * pueda tener múltiples números de teléfono registrados en el sistema.
 * @author Jesus Manuel Martinez Cortez
 */
@Entity
@Table(name = "Telefono")
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long telephone_id;

    @Column(name = "Telefono", length = 10)
    public String telephone;

    /**
     * Constructor que inicializa el número de teléfono.
     * Ideal para crear nuevas instancias antes de vincularlas a un cliente y persistirlas.
     *
     * @param telephone El número de teléfono a registrar (máximo 10 caracteres).
     */
    public Telephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de la entidad Telephone.
     */
    public Telephone() {
    }

    /**
     * Obtiene el identificador único del registro de teléfono.
     *
     * @return El ID del teléfono autogenerado por la base de datos.
     */
    public long getTelephone_id() {
        return telephone_id;
    }

    /**
     * Establece el identificador único del registro de teléfono.
     *
     * @param telephone_id El nuevo ID a asignar.
     */
    public void setTelephone_id(long telephone_id) {
        this.telephone_id = telephone_id;
    }

    /**
     * Obtiene el número de teléfono.
     *
     * @return El número de teléfono almacenado.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Establece el número de teléfono.
     *
     * @param telephone El nuevo número de teléfono a asignar.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}