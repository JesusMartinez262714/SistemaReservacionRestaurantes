package GUI;

import Controladores.RestauranteController;
import DTOs.RestauranteDTO;
import Excepciones.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaz gráfica encargada de la gestión administrativa de los restaurantes y su ubicación física.
 * <p>
 * Esta vista unifica la captura de datos generales del restaurante (nombre, teléfono y especialidad)
 * con los detalles de su dirección (calle, número, CP, ciudad y estado) en un solo formulario.
 * Esta integración facilita la experiencia de usuario al permitir el registro completo de una
 * sucursal sin navegar por múltiples ventanas, asegurando que la información de contacto y
 * ubicación se mantenga sincronizada desde el primer momento.
 * @author Jesus Manuel Martinez Cortez
 */
public class RestauranteGUI extends JFrame {

    private JTextField txtIdRestaurante, txtNombre, txtTelefono, txtTipoCocina;
    private JTextField txtCalle, txtNumero, txtColonia, txtCp, txtCiudad, txtEstado;
    private JTable tablaRestaurantes;
    private DefaultTableModel modeloTabla;
    private RestauranteController restauranteController = new RestauranteController();

    /**
     * Construye y configura la ventana de gestión de restaurantes.
     * <p>
     * Define las dimensiones, el centrado en pantalla y el comportamiento DISPOSE_ON_CLOSE
     * para permitir un retorno fluido al menú principal tras cerrar este módulo.
     */
    public RestauranteGUI() {
        setTitle("Gestión de Restaurantes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        cargarRestaurantesEnTabla();
    }

    /**
     * Inicializa y organiza los componentes visuales de la interfaz.
     * <p>
     * La ventana se divide en tres secciones estratégicas:
     * <ul>
     * <li><b>Norte (Formulario):</b> Panel con diseño de rejilla para capturar simultáneamente
     * los datos de identidad y de localización. El ID permanece bloqueado por ser autogenerado.</li>
     * <li><b>Centro (Acciones):</b> Fila de botones para las operaciones CRUD (Agregar, Buscar, Eliminar).</li>
     * <li><b>Sur (Historial):</b> Tabla interactiva que muestra el listado de restaurantes registrados
     * con sus datos más relevantes para una consulta rápida.</li>
     * </ul>
     */
    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 4, 10, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Restaurante y Dirección"));

        panelFormulario.add(new JLabel("ID Restaurante:"));
        txtIdRestaurante = new JTextField();
        txtIdRestaurante.setEnabled(false);
        panelFormulario.add(txtIdRestaurante);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Teléfono (10 dígitos):"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Tipo de Cocina:"));
        txtTipoCocina = new JTextField();
        panelFormulario.add(txtTipoCocina);

        panelFormulario.add(new JLabel("Calle:"));
        txtCalle = new JTextField();
        panelFormulario.add(txtCalle);

        panelFormulario.add(new JLabel("Número:"));
        txtNumero = new JTextField();
        panelFormulario.add(txtNumero);

        panelFormulario.add(new JLabel("C.P.:"));
        txtCp = new JTextField();
        panelFormulario.add(txtCp);

        panelFormulario.add(new JLabel("Ciudad:"));
        txtCiudad = new JTextField();
        panelFormulario.add(txtCiudad);

        panelFormulario.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panelFormulario.add(txtEstado);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));

        add(panelFormulario, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar Campos");

        btnAgregar.addActionListener(e -> agregarRestaurante());
        btnBuscar.addActionListener(e -> buscarRestaurante());
        btnEliminar.addActionListener(e -> eliminarRestaurante());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.CENTER);

        String[] columnas = {"ID", "Nombre", "Teléfono", "Tipo Cocina", "Calle", "Número", "Ciudad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaRestaurantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRestaurantes);
        scrollPane.setPreferredSize(new Dimension(780, 250));
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Recopila los datos del formulario de restaurante y dirección para su persistencia.
     * <p>
     * Detecta si se trata de un registro nuevo o una actualización basándose en el campo ID.
     * Notifica detalladamente al usuario en caso de errores de validación o fallos en el proceso.
     */
    private void agregarRestaurante() {
        try {
            String idActual = txtIdRestaurante.getText().trim();

            if (idActual.isEmpty()) {
                restauranteController.guardarRestaurante(txtNombre.getText(), txtTelefono.getText(),
                        txtTipoCocina.getText(), txtCalle.getText(), txtNumero.getText(),
                        txtCp.getText(), txtCiudad.getText(), txtEstado.getText());
                JOptionPane.showMessageDialog(this, "El restaurante y su dirección han sido registrados exitosamente.");
            } else {
                restauranteController.actualizarRestaurante(idActual, txtNombre.getText(), txtTelefono.getText(),
                        txtTipoCocina.getText(), txtCalle.getText(), txtNumero.getText(),
                        txtCp.getText(), txtCiudad.getText(), txtEstado.getText());
                JOptionPane.showMessageDialog(this, "La información del restaurante con ID " + idActual + " ha sido actualizada correctamente.");
            }
            cargarRestaurantesEnTabla();
            limpiarCampos();
        } catch (ValidacionException ex) {
            JOptionPane.showMessageDialog(this, "Aviso de validación:\n" + ex.getMessage(), "Faltan Datos", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: Los campos 'Número' y 'Código Postal' deben contener únicamente valores numéricos enteros.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Se ha producido un error inesperado al procesar la solicitud: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Consulta el catálogo completo de restaurantes y refresca la tabla visual
     * con los datos más recientes de la base de datos.
     */
    private void cargarRestaurantesEnTabla() {
        modeloTabla.setRowCount(0);
        java.util.List<RestauranteDTO> lista = restauranteController.obtenerTodos();
        for (RestauranteDTO r : lista) {
            Object[] fila = {
                    r.getRestaurante_id(),
                    r.getNombre(),
                    r.getTelefono(),
                    r.getTipoCocina(),
                    r.getCalle(),
                    r.getNumero(),
                    r.getCiudad()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Solicita al usuario un ID para buscar un restaurante específico.
     * Si se localiza, rellena todos los campos del formulario con la información
     * del restaurante y su dirección asociada para su edición.
     */
    private void buscarRestaurante() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID del restaurante que desea localizar:");
        try {
            RestauranteDTO r = restauranteController.buscarPorId(input);
            if (r != null) {
                txtIdRestaurante.setText(String.valueOf(r.getRestaurante_id()));
                txtNombre.setText(r.getNombre());
                txtTelefono.setText(r.getTelefono());
                txtTipoCocina.setText(r.getTipoCocina());
                txtCalle.setText(r.getCalle());
                txtNumero.setText(String.valueOf(r.getNumero()));
                txtCp.setText(String.valueOf(r.getCp()));
                txtCiudad.setText(r.getCiudad());
                txtEstado.setText(r.getEstado());
            } else if (input != null) {
                JOptionPane.showMessageDialog(this, "No se encontró ningún restaurante registrado con el ID: " + input + ". Verifique el número e intente de nuevo.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de búsqueda: El identificador (ID) debe ser un número entero válido sin letras ni símbolos.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita confirmación para eliminar de forma permanente un restaurante del sistema.
     * Informa sobre el resultado de la operación o sobre errores en el formato del ID ingresado.
     */
    private void eliminarRestaurante() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de ID del restaurante que desea eliminar permanentemente:");
        try {
            if (input != null && !input.trim().isEmpty()) {
                if (JOptionPane.showConfirmDialog(this, "¿Está completamente seguro de eliminar el restaurante con ID " + input + "? Esta acción borrará también su dirección y no puede deshacerse.", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (restauranteController.eliminarRestaurante(input)) {
                        JOptionPane.showMessageDialog(this, "El restaurante ha sido eliminado correctamente del sistema.");
                        limpiarCampos();
                        cargarRestaurantesEnTabla();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo realizar la eliminación. Verifique que el restaurante con ID " + input + " aún exista.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: El identificador (ID) para eliminar debe ser un número entero válido.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia el contenido de todas las cajas de texto en el formulario para permitir una nueva captura.
     */
    private void limpiarCampos() {
        txtIdRestaurante.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtTipoCocina.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtCp.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
    }
}