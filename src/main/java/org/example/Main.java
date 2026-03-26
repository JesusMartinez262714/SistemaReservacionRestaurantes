package org.example;

import GUI.MenuPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase de entrada principal (Entry Point) para el Sistema de Reservación de Restaurantes.
 * Esta clase se encarga de configurar el entorno visual inicial y lanzar la interfaz
 * de usuario en el hilo adecuado.
 * * Al centralizar el arranque aquí, se asegura que todas las dependencias y el
 * LookAndFeel se carguen antes de mostrar cualquier ventana al usuario.
 * @author Jesus Manuel Martinez Cortez
 */
public class Main {

    /**
     * Punto de inicio de la aplicación.
     * <p>
     * Realiza las siguientes tareas críticas de arranque:
     * <ul>
     * <li><b>Configuración Estética:</b> Intenta establecer el "Look and Feel" del sistema
     * operativo anfitrión para que la aplicación luzca como una aplicación nativa (Windows, Mac o Linux).</li>
     * <li><b>Gestión de Hilos:</b> Utiliza {@code SwingUtilities.invokeLater} para garantizar
     * que la creación de la GUI ocurra en el Event Dispatch Thread (EDT), evitando errores
     * de concurrencia y bloqueos en la interfaz.</li>
     * <li><b>Lanzamiento:</b> Instancia y despliega el {@link MenuPrincipal} como raíz de la navegación.</li>
     * </ul>
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta versión).
     */
    public static void main(String[] args) {
        try {
            // Ajusta la apariencia de las ventanas para que coincida con el Sistema Operativo del usuario
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla el LookAndFeel nativo, la aplicación inicia con el tema por defecto de Java (Metal)
            System.err.println("No se pudo cargar el estilo visual nativo: " + e.getMessage());
        }

        // Ejecución segura de la interfaz gráfica en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}