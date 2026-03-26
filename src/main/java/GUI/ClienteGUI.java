package GUI;

import Controladores.ClienteController;
import DTOs.ClienteDTO;
import Excepciones.ValidacionException;
import Interfaces.IClienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaz gráfica encargada de la gestión integral de clientes (registro, búsqueda, actualización y eliminación).
 * Al centralizar la captura de clientes regulares y premium en un solo formulario dinámico (controlado por un CheckBox),
 * esta vista complementa a la perfección la arquitectura de base de datos basada en Tabla Única (SINGLE_TABLE).
 * Este enfoque unificado no solo simplifica el diseño visual y el flujo de uso, sino que se alinea con la rapidez
 * de la persistencia al evitar cruces complejos de datos, permitiendo que el sistema interprete la jerarquía de
 * objetos automáticamente con un alto rendimiento.
 * @author Jesus Manuel Martinez Cortez
 */
public class ClienteGUI extends JFrame {

    private JTextField txtId, txtNombre, txtEmail, txtTelefono, txtPuntos, txtNivel;
    private JCheckBox chkPremium;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteController clienteController = new ClienteController();

    /**
     * Construye la ventana principal de gestión de clientes.
     * Configura las dimensiones, el comportamiento de cierre seguro (DISPOSE_ON_CLOSE)
     * para no interrumpir el flujo del menú principal, e inicializa la carga de datos.
     */
    public ClienteGUI() {
        setTitle("Gestión de Clientes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        cargarClientesEnTabla();
    }

    /**
     * Inicializa y distribuye los componentes visuales de la interfaz.
     * Estructura la ventana en tres zonas principales: formulario de captura en la parte superior,
     * barra de herramientas de acciones en el centro, y tabla de visualización de registros en la parte inferior.
     */
    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        panelFormulario.add(new JLabel("ID Cliente:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("¿Es Cliente Premium?"));
        chkPremium = new JCheckBox();
        chkPremium.addActionListener(e -> cambiarCamposPremium());
        panelFormulario.add(chkPremium);

        panelFormulario.add(new JLabel("Puntos:"));
        txtPuntos = new JTextField();
        txtPuntos.setEnabled(false);
        panelFormulario.add(txtPuntos);

        panelFormulario.add(new JLabel("Nivel:"));
        txtNivel = new JTextField();
        txtNivel.setEnabled(false);
        panelFormulario.add(txtNivel);

        add(panelFormulario, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarCliente());

        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.addActionListener(e -> buscarCliente());
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarCliente());

        JButton btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.CENTER);

        String[] columnas = {"ID", "Nombre", "Email", "Teléfono", "Tipo", "Puntos", "Nivel"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setPreferredSize(new Dimension(680, 200));

        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Alterna la habilitación de los campos exclusivos para clientes premium.
     * Si la casilla es desmarcada, bloquea los campos de puntos y nivel y limpia sus valores
     * para prevenir la persistencia de datos erróneos en clientes de tipo estándar.
     */
    private void cambiarCamposPremium() {
        boolean esPremium = chkPremium.isSelected();
        txtPuntos.setEnabled(esPremium);
        txtNivel.setEnabled(esPremium);
        if (!esPremium) {
            txtPuntos.setText("");
            txtNivel.setText("");
        }
    }

    /**
     * Restablece todos los campos del formulario a su estado original vacío
     * y recarga la tabla de datos para asegurar una vista limpia.
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtPuntos.setText("");
        txtNivel.setText("");
        chkPremium.setSelected(false);
        cambiarCamposPremium();

        cargarClientesEnTabla();
    }

    /**
     * Consulta el controlador para obtener la lista actualizada de clientes
     * y renderiza la información en la tabla de la interfaz.
     */
    private void cargarClientesEnTabla() {
        modeloTabla.setRowCount(0);

        java.util.List<ClienteDTO> lista = clienteController.obtenerTodos();

        for (ClienteDTO c : lista) {
            Object[] fila = {
                    c.getcliente_id(),
                    c.getName(),
                    c.getEmail(),
                    c.getTelefono(),
                    c.isEsPremium() ? "Premium" : "Normal",
                    c.isEsPremium() ? c.getPuntos() : "N/A",
                    c.isEsPremium() ? c.getNivel() : "N/A"
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Recopila los datos del formulario para delegar la creación o actualización de un cliente al controlador.
     * Captura y notifica detalladamente al usuario en caso de que ocurran errores de validación o conversión de datos.
     */
    private void agregarCliente() {
        try {
            String idActual = txtId.getText().trim();

            if (idActual.isEmpty()) {
                clienteController.guardarCliente(
                        txtNombre.getText(), txtEmail.getText(), txtTelefono.getText(),
                        chkPremium.isSelected(), txtPuntos.getText(), txtNivel.getText()
                );
                JOptionPane.showMessageDialog(this, "El nuevo cliente ha sido registrado y guardado exitosamente en el sistema.");
            } else {
                clienteController.actualizarCliente(
                        idActual,
                        txtNombre.getText(),
                        txtEmail.getText(),
                        txtTelefono.getText(),
                        chkPremium.isSelected(),
                        txtPuntos.getText(),
                        txtNivel.getText()
                );
                JOptionPane.showMessageDialog(this, "Los datos del cliente con ID " + idActual + " se han actualizado correctamente.");
            }

            cargarClientesEnTabla();
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: Asegúrese de que los campos 'Puntos' y 'Nivel' contengan únicamente valores numéricos enteros enteros válidos y sin espacios.", "Error de Tipificación", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException ex) {
            JOptionPane.showMessageDialog(this, "Aviso de validación de negocio:\n" + ex.getMessage(), "Faltan Datos o Formato Inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Solicita al usuario un identificador y busca un registro específico de cliente.
     * Si lo encuentra, rellena el formulario y aísla el registro en la tabla; de lo contrario, informa el fallo.
     */
    private void buscarCliente() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID del cliente que desea localizar:");
        try {
            ClienteDTO cliente = clienteController.buscarPorId(input);

            if (cliente != null) {
                txtId.setText(String.valueOf(cliente.getcliente_id()));
                txtNombre.setText(cliente.getName());
                txtEmail.setText(cliente.getEmail());
                txtTelefono.setText(cliente.getTelefono());
                chkPremium.setSelected(cliente.isEsPremium());
                cambiarCamposPremium();

                if (cliente.isEsPremium()) {
                    txtPuntos.setText(String.valueOf(cliente.getPuntos()));
                    txtNivel.setText(String.valueOf(cliente.getNivel()));
                }

                modeloTabla.setRowCount(0);

                Object[] fila = {
                        cliente.getcliente_id(),
                        cliente.getName(),
                        cliente.getEmail(),
                        cliente.getTelefono(),
                        cliente.isEsPremium() ? "Premium" : "Normal",
                        cliente.isEsPremium() ? cliente.getPuntos() : "N/A",
                        cliente.isEsPremium() ? cliente.getNivel() : "N/A"
                };
                modeloTabla.addRow(fila);

            } else if (input != null) {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro asociado al ID: " + input + ". Verifique el número e intente de nuevo.", "Cliente no localizado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de búsqueda: El identificador (ID) ingresado debe ser un número entero válido. No se permiten letras ni caracteres especiales.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita la confirmación del usuario para eliminar permanentemente un registro de cliente basado en su ID.
     * Notifica el resultado de la operación o los errores de formato si la entrada es incorrecta.
     */
    private void eliminarCliente() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID del cliente que desea eliminar de forma permanente:");
        try {
            if (input != null && !input.trim().isEmpty()) {
                int confirmar = JOptionPane.showConfirmDialog(this, "¿Está completamente seguro que desea eliminar al cliente con el ID " + input + "? Esta acción no se puede deshacer.", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    boolean exito = clienteController.eliminarCliente(input);

                    if (exito) {
                        JOptionPane.showMessageDialog(this, "El cliente con ID " + input + " ha sido eliminado permanentemente del sistema.");
                        limpiarCampos();
                        cargarClientesEnTabla();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error en la eliminación: No existe ningún cliente registrado con el ID " + input + " en la base de datos.", "Eliminación Fallida", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: El identificador (ID) para eliminar debe ser un número entero válido.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }
}