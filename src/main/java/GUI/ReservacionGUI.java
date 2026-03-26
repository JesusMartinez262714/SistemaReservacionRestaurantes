package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

import Controladores.ReservacionController;
import DTOs.*;
import Excepciones.*;
import com.toedter.calendar.JDateChooser;

/**
 * Interfaz gráfica principal para la gestión de reservaciones del restaurante.
 * <p>
 * Esta ventana actúa como el punto de integración del sistema, permitiendo coordinar
 * clientes y mesas para fechas y horas específicas. Utiliza componentes especializados
 * como {@link JDateChooser} para garantizar la integridad de las fechas y {@link JSpinner}
 * para el manejo preciso de horarios. Las listas desplegables (ComboBox) se alimentan
 * dinámicamente de la base de datos para evitar errores de referencia manual.
 * @author Jesus Manuel Martinez Cortez
 */
public class ReservacionGUI extends JFrame {

    private JTextField txtIdReservacion;
    private JDateChooser dateChooserFecha;
    private JTextField txtHora;
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbMesa;
    private JTable tablaReservaciones;
    private DefaultTableModel modeloTabla;
    private ReservacionController resController = new ReservacionController();
    private JTextField txtCantidadPersonas;
    private JSpinner spnHora;

    /**
     * Construye y configura la ventana de gestión de reservaciones.
     * <p>
     * Establece las dimensiones, el centrado en pantalla y el comportamiento de cierre
     * seguro (DISPOSE_ON_CLOSE). Además, inicializa la carga de componentes y la
     * sincronización de datos con la base de datos para los catálogos y la tabla principal.
     */
    public ReservacionGUI() {
        setTitle("Gestión de Reservaciones");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        inicializarComponentes();
        cargarCombos();
        cargarTabla();
    }

    /**
     * Inicializa y distribuye los componentes visuales de la interfaz.
     * <p>
     * Organiza la ventana en:
     * <ul>
     * <li><b>Formulario (Norte):</b> Captura de ID (bloqueado), fecha, hora, cantidad de personas
     * y selección de cliente/mesa mediante menús desplegables.</li>
     * <li><b>Acciones (Centro):</b> Botones para registrar, buscar, eliminar y limpiar el formulario.</li>
     * <li><b>Listado (Sur):</b> Tabla detallada que muestra el historial de reservaciones activas.</li>
     * </ul>
     */
    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reservación"));

        panelFormulario.add(new JLabel("ID Reservación:"));
        txtIdReservacion = new JTextField();
        txtIdReservacion.setEnabled(false);
        panelFormulario.add(txtIdReservacion);

        panelFormulario.add(new JLabel("Fecha de Reservación:"));
        dateChooserFecha = new JDateChooser();
        dateChooserFecha.setDateFormatString("yyyy-MM-dd");
        panelFormulario.add(dateChooserFecha);

        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        spnHora = new JSpinner(new SpinnerDateModel());
        spnHora.setEditor(new JSpinner.DateEditor(spnHora, "HH:mm"));
        panelFormulario.add(spnHora);

        panelFormulario.add(new JLabel("Cantidad de Personas:"));
        txtCantidadPersonas = new JTextField();
        panelFormulario.add(txtCantidadPersonas);

        panelFormulario.add(new JLabel("Seleccionar Cliente:"));
        cmbCliente = new JComboBox<>();
        panelFormulario.add(cmbCliente);

        panelFormulario.add(new JLabel("Seleccionar Mesa:"));
        cmbMesa = new JComboBox<>();
        panelFormulario.add(cmbMesa);
        add(panelFormulario, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarReservacion());

        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.addActionListener(e -> buscarReservacion());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarReservacion());

        JButton btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.CENTER);

        String[] columnas = {"ID Reservación", "Fecha", "Hora", "Cliente", "Mesa"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReservaciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReservaciones);
        scrollPane.setPreferredSize(new Dimension(630, 200));
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Consulta al controlador para obtener las listas de clientes y mesas registradas
     * y actualiza los ComboBox de la interfaz para asegurar que solo se seleccionen datos válidos.
     */
    private void cargarCombos() {
        cmbCliente.removeAllItems();
        cmbMesa.removeAllItems();

        for (ClienteDTO c : resController.obtenerClientes()) {
            cmbCliente.addItem(c.getcliente_id() + " - " + c.getName());
        }

        for (MesaDTO m : resController.obtenerMesas()) {
            cmbMesa.addItem(m.getMesa_id() + " - Mesa #" + m.getNumeroMesa() + " (Cap: " + m.getCapacidad() + ")");
        }
    }

    /**
     * Extrae el identificador numérico de la cadena de texto mostrada en los ComboBox.
     * * @param textoCombo La cadena con formato "ID - Descripción".
     * @return El ID en formato String.
     */
    private String extraerId(String textoCombo) {
        if (textoCombo == null) return "0";
        return textoCombo.split(" - ")[0];
    }

    /**
     * Recopila la información de fecha, hora, cliente, mesa y comensales para procesar
     * una nueva reservación a través del controlador.
     * <p>
     * Notifica detalladamente si ocurre una infracción de reglas de negocio (como mesas
     * con capacidad insuficiente o desperdicio de espacio) o errores de validación.
     */
    private void agregarReservacion() {
        try {
            Date fecha = dateChooserFecha.getDate();
            Date hora = (Date) spnHora.getValue();
            String idCliente = extraerId((String) cmbCliente.getSelectedItem());
            String idMesa = extraerId((String) cmbMesa.getSelectedItem());
            String personas = txtCantidadPersonas.getText();

            resController.guardarReservacion(fecha, hora, idCliente, idMesa, personas);

            JOptionPane.showMessageDialog(this, "¡La reservación ha sido confirmada y guardada exitosamente!");
            cargarTabla();
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato: El campo 'Cantidad de Personas' debe ser un número entero válido.", "Error de Tipificación", JOptionPane.ERROR_MESSAGE);
        } catch (ReglaNegocioException | ValidacionException ex) {
            JOptionPane.showMessageDialog(this, "Aviso de restricción del sistema:\n" + ex.getMessage(), "Validación de Negocio", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Refresca la tabla de visualización con el listado completo de reservaciones
     * obtenido desde el controlador.
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        java.util.List<ReservacionDTO> lista = resController.obtenerTodas();
        for (ReservacionDTO r : lista) {
            modeloTabla.addRow(new Object[]{
                    r.getReservacion_id(),
                    r.getFecha(),
                    r.getHora(),
                    r.getCliente_id(),
                    r.getMesa_id()
            });
        }
    }

    /**
     * Busca una reservación por su ID. Si la localiza, rellena los componentes del
     * formulario con la información recuperada, incluyendo la selección automática en los ComboBox.
     */
    private void buscarReservacion() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID de la reservación que desea consultar:");
        try {
            ReservacionDTO res = resController.buscarPorId(input);

            if (res != null) {
                txtIdReservacion.setText(String.valueOf(res.getReservacion_id()));
                dateChooserFecha.setDate(res.getFecha());
                spnHora.setValue(res.getHora());
                txtCantidadPersonas.setText(String.valueOf(res.getCantidadPersonas()));

                seleccionarEnCombo(cmbCliente, res.getCliente_id());
                seleccionarEnCombo(cmbMesa, res.getMesa_id());
            } else if (input != null) {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna reservación con el ID: " + input + ". Verifique el número e intente de nuevo.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de búsqueda: El identificador (ID) debe ser un número entero válido.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita confirmación para cancelar (eliminar) una reservación del sistema.
     * Notifica el éxito de la operación o informa si el ID proporcionado no es válido.
     */
    private void eliminarReservacion() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID de la reservación que desea cancelar:");
        try {
            if (input != null && !input.trim().isEmpty()) {
                if (JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar la reservación con el ID " + input + "? Esta acción eliminará el registro permanentemente.", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (resController.cancelarReservacion(input)) {
                        JOptionPane.showMessageDialog(this, "La reservación ha sido cancelada y removida del sistema correctamente.");
                        cargarTabla();
                        limpiarCampos();
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato: El identificador (ID) para cancelar debe ser un número entero válido.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Restablece todos los componentes del formulario a su estado inicial.
     */
    private void limpiarCampos() {
        txtIdReservacion.setText("");
        dateChooserFecha.setDate(null);
        spnHora.setValue(new Date());
        txtCantidadPersonas.setText("");
        if (cmbCliente.getItemCount() > 0) cmbCliente.setSelectedIndex(0);
        if (cmbMesa.getItemCount() > 0) cmbMesa.setSelectedIndex(0);
    }

    /**
     * Método auxiliar para sincronizar la selección de un ComboBox basado en un ID numérico.
     * * @param combo El componente JComboBox a manipular.
     * @param id El identificador único a buscar en los elementos del combo.
     */
    private void seleccionarEnCombo(JComboBox<String> combo, Long id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(id + " - ")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
}