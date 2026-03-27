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
     * <li><b>Lanzamiento:</b> Instancia y despliega el {@link MenuPrincipal} como raíz de la navegación.</li>
     * </ul>
     *
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el estilo visual nativo: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}