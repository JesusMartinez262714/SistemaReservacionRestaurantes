package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal que actúa como el menú de inicio y núcleo de navegación del Sistema de Reservación de Restaurantes.
 * Proporciona una interfaz centralizada desde la cual el usuario puede acceder a los diferentes
 * módulos de gestión (Clientes, Restaurantes, Mesas y Reservaciones) mediante botones de acción directa.
 * @author Jesus Manuel Martinez Cortez
 */
public class MenuPrincipal extends JFrame {

    /**
     * Construye y configura la ventana del menú principal.
     * Define las dimensiones, el comportamiento de cierre (EXIT_ON_CLOSE para terminar completamente la aplicación),
     * y utiliza un diseño de cuadrícula (GridLayout) para organizar los botones de acceso a los módulos
     * de manera vertical y uniforme.
     */
    public MenuPrincipal() {
        setTitle("Sistema de Reservación de Restaurantes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnRestaurantes = new JButton("Gestión de Restaurantes");
        JButton btnMesas = new JButton("Gestión de Mesas");
        JButton btnReservaciones = new JButton("Gestión de Reservaciones");
        JButton btnSalir = new JButton("Salir");

        btnClientes.addActionListener(e -> abrirClienteGUI());
        btnRestaurantes.addActionListener(e -> abrirRestauranteGUI());
        btnMesas.addActionListener(e -> abrirMesaGUI());
        btnReservaciones.addActionListener(e -> abrirReservacionGUI());
        btnSalir.addActionListener(e -> System.exit(0));

        btnClientes.setBackground(new Color(173, 216, 230));
        add(btnClientes);
        add(btnRestaurantes);
        add(btnMesas);
        add(btnReservaciones);
        add(btnSalir);
    }

    /**
     * Instancia y hace visible la ventana correspondiente al módulo de Gestión de Clientes.
     */
    private void abrirClienteGUI() {
        ClienteGUI clientePantalla = new ClienteGUI();
        clientePantalla.setVisible(true);
    }

    /**
     * Instancia y hace visible la ventana correspondiente al módulo de Gestión de Restaurantes y sus direcciones.
     */
    private void abrirRestauranteGUI() {
        RestauranteGUI restaurantePantalla = new RestauranteGUI();
        restaurantePantalla.setVisible(true);
    }

    /**
     * Instancia y hace visible la ventana correspondiente al módulo de Gestión de Mesas.
     */
    private void abrirMesaGUI() {
        MesaGUI mesaPantalla = new MesaGUI();
        mesaPantalla.setVisible(true);
    }

    /**
     * Instancia y hace visible la ventana correspondiente al módulo de Gestión de Reservaciones.
     */
    private void abrirReservacionGUI() {
        ReservacionGUI reservacionPantalla = new ReservacionGUI();
        reservacionPantalla.setVisible(true);
    }

    /**
     * Método principal (entry point) que arranca la ejecución de todo el sistema.
     * Utiliza {@code SwingUtilities.invokeLater} para asegurar que la creación y visualización
     * de la interfaz gráfica inicial se realice de forma segura en el hilo de eventos de Swing (EDT).
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}