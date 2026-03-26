package GUI;

import Controladores.MesaController;
import DTOs.MesaDTO;
import Excepciones.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaz gráfica encargada de registrar y administrar el inventario de mesas físicas de cada restaurante.
 * <p>
 * Gestiona el alta, modificación, búsqueda y eliminación de las mesas. En el formulario se requiere
 * obligatoriamente el "ID Restaurante" para establecer la relación en la base de datos
 * (una mesa pertenece estrictamente a una sucursal). De esta manera, al persistir la información,
 * JPA enlaza correctamente la entidad DiningTable con su respectivo Restaurant
 * @author Jesus Manuel Martinez Cortez
 */
public class MesaGUI extends JFrame {

    private JTextField txtIdMesa, txtNumeroMesa, txtCapacidad, txtIdRestaurante;
    private JTable tablaMesas;
    private DefaultTableModel modeloTabla;
    private MesaController mesaController = new MesaController();

    /**
     * Construye y configura la ventana de gestión de mesas.
     * <p>
     * Establece las dimensiones, el centrado en pantalla y define el comportamiento
     * DISPOSE_ON_CLOSE para garantizar que, al cerrar este módulo, el sistema regrese
     * de forma segura al menú principal sin interrumpir la ejecución de la aplicación.
     */
    public MesaGUI() {
        setTitle("Gestión de Mesas");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        cargarMesasEnTabla();
    }

    /**
     * Inicializa y distribuye los componentes visuales de la interfaz.
     * <p>
     * Estructura la ventana en tres zonas:
     * <ul>
     * <li><b>Norte (Formulario):</b> Campos de captura para los datos de la mesa. El campo "ID Mesa"
     * permanece deshabilitado ya que la base de datos autogenera este identificador.</li>
     * <li><b>Centro (Controles):</b> Botones de acción (Agregar, Buscar, Eliminar, Limpiar).</li>
     * <li><b>Sur (Visualización):</b> Tabla dinámica que lista el inventario actual mostrando
     * el ID, número, capacidad y el restaurante al que están asignadas.</li>
     * </ul>
     */
    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Mesa"));

        panelFormulario.add(new JLabel("ID Mesa:"));
        txtIdMesa = new JTextField();
        txtIdMesa.setEnabled(false);
        panelFormulario.add(txtIdMesa);

        panelFormulario.add(new JLabel("Número de Mesa:"));
        txtNumeroMesa = new JTextField();
        panelFormulario.add(txtNumeroMesa);

        panelFormulario.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panelFormulario.add(txtCapacidad);

        panelFormulario.add(new JLabel("ID Restaurante:"));
        txtIdRestaurante = new JTextField();
        panelFormulario.add(txtIdRestaurante);

        add(panelFormulario, BorderLayout.NORTH);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarMesa());

        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.addActionListener(e -> buscarMesa());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarMesa());

        JButton btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.CENTER);

        String[] columnas = {"ID Mesa", "Número", "Capacidad", "ID Restaurante"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaMesas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMesas);
        scrollPane.setPreferredSize(new Dimension(580, 200));
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Recopila los datos del formulario y solicita al controlador la creación o actualización de una mesa.
     * Proporciona retroalimentación detallada al usuario tanto en casos de éxito como en errores de validación o formato.
     */
    private void agregarMesa() {
        try {
            String idActual = txtIdMesa.getText().trim();

            if (idActual.isEmpty()) {
                mesaController.guardarMesa(txtNumeroMesa.getText(), txtCapacidad.getText(), txtIdRestaurante.getText());
                JOptionPane.showMessageDialog(this, "La nueva mesa ha sido registrada y asignada al restaurante exitosamente.");
            } else {
                mesaController.actualizarMesa(idActual, txtNumeroMesa.getText(), txtCapacidad.getText(), txtIdRestaurante.getText());
                JOptionPane.showMessageDialog(this, "Los datos de la mesa han sido actualizados correctamente en el sistema.");
            }
            cargarMesasEnTabla();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: Asegúrese de que los campos 'Número de Mesa', 'Capacidad' e 'ID Restaurante' contengan únicamente números enteros válidos y sin espacios.", "Error de Tipificación", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException ex) {
            JOptionPane.showMessageDialog(this, "Aviso de validación de negocio:\n" + ex.getMessage(), "Faltan Datos o Restricción Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Consulta el inventario completo de mesas a través del controlador y refresca
     * la tabla de la interfaz gráfica con los datos más recientes.
     */
    private void cargarMesasEnTabla() {
        modeloTabla.setRowCount(0);
        java.util.List<MesaDTO> lista = mesaController.obtenerTodas();
        for (MesaDTO m : lista) {
            modeloTabla.addRow(new Object[]{m.getMesa_id(), m.getNumeroMesa(), m.getCapacidad(), m.getRestaurante_id()});
        }
    }

    /**
     * Solicita al usuario un identificador para localizar una mesa específica en el sistema.
     * Si la encuentra, rellena automáticamente los campos del formulario; de lo contrario, muestra una alerta descriptiva.
     */
    private void buscarMesa() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el número de ID de la mesa que desea localizar:");
        try {
            MesaDTO m = mesaController.buscarPorId(idStr);
            if (m != null) {
                txtIdMesa.setText(String.valueOf(m.getMesa_id()));
                txtNumeroMesa.setText(String.valueOf(m.getNumeroMesa()));
                txtCapacidad.setText(String.valueOf(m.getCapacidad()));
                txtIdRestaurante.setText(String.valueOf(m.getRestaurante_id()));
            } else if (idStr != null) {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna mesa registrada con el ID ingresado. Verifique el número e intente de nuevo.", "Mesa no localizada", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato: El identificador (ID) ingresado debe ser un número entero válido. No se permiten letras ni caracteres especiales.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita la confirmación del usuario para eliminar permanentemente el registro de una mesa, basándose en su ID.
     * Informa sobre el éxito de la operación o detalla el problema si el formato del ID es incorrecto.
     */
    private void eliminarMesa() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el número de ID de la mesa que desea eliminar de forma permanente:");
        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                if (JOptionPane.showConfirmDialog(this, "¿Está completamente seguro que desea eliminar la mesa con el ID " + idStr + "? Esta acción no se puede deshacer.", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (mesaController.eliminarMesa(idStr)) {
                        JOptionPane.showMessageDialog(this, "La mesa con ID " + idStr + " ha sido eliminada permanentemente del sistema.");
                        cargarMesasEnTabla();
                        limpiarCampos();
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato: El identificador (ID) para eliminar debe ser un número entero válido.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Restablece el estado visual del formulario limpiando el contenido de todas las cajas de texto.
     */
    private void limpiarCampos() {
        txtIdMesa.setText("");
        txtNumeroMesa.setText("");
        txtCapacidad.setText("");
        txtIdRestaurante.setText("");
    }
}