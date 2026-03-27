package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase utilitaria encargada de gestionar la conexión con la base de datos a través de JPA.
 * Implementa el patrón Singleton para asegurar que solo exista una instancia de
 * {@link EntityManagerFactory} en toda la aplicación, optimizando el uso de recursos.
 * <p>
 * El {@code EntityManagerFactory} es un objeto costoso de crear, por lo que esta clase
 * centraliza su ciclo de vida, permitiendo que todos los DAOs obtengan unidades de trabajo
 * (EntityManager) de forma eficiente y segura.
 * @author Jesus Manuel Martinez Cortez
 */
public class Conexion {

    /** * Instancia única de la fábrica de manejadores de entidades.
     */
    private static EntityManagerFactory emf;

    /**
     * Obtiene la instancia única de {@link EntityManagerFactory}.
     * Si la fábrica aún no ha sido creada, se inicializa utilizando la unidad de persistencia
     * definida en el archivo {@code persistence.xml} bajo el nombre "PersistenceUnit".
     *
     * @return La instancia de {@link EntityManagerFactory} configurada, o {@code null} si ocurre un fallo en la inicialización.
     */
    public static EntityManagerFactory getEMF() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("PersistenceUnit");
            } catch (Exception e) {
                System.err.println("Error crítico al inicializar la persistencia JPA: " + e.getMessage());
            }
        }
        return emf;
    }

    /**
     * Cierra de forma segura la fábrica de manejadores de entidades.
     * Este método debe ser invocado al finalizar la ejecución de la aplicación para
     * liberar correctamente el pool de conexiones y los recursos del sistema de persistencia.
     */
    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}