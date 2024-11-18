package edu.segundo.vista;

import edu.segundo.conexion.AutoRepositorio;
import edu.segundo.conexion.CotizacionRepositorio;
import edu.segundo.modelos.Automovil;
import edu.segundo.modelos.Cotizacion;
import edu.segundo.utils.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("all")
public class formCotizador extends JFrame {

    private final CotizacionRepositorio cotizacionRepositorio;

    private final AutoRepositorio autoRepositorio;

    private final JComboBox<String> cmbMarcas, cmbModelo, cmbVersion, cmbPlazos;

    private final JTextArea txaDescripcion;

    private final JTextField txtPrecio, txtDescuento, txtEnganche, txtFinanciamiento, txtInteres;

    private final JButton btnCalcular, btnGuardar;

    private final DefaultTableModel modelo;

    private Automovil automovil;

    private BigDecimal enganche;

    public formCotizador() {

        cotizacionRepositorio = new CotizacionRepositorio();
        autoRepositorio = new AutoRepositorio();

        setLayout(null);
        setBounds(0, 0, 800, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlPrincipal = new JPanel(null);
        pnlPrincipal.setBounds(0, 0, 800, 700);
        pnlPrincipal.setBackground(new Color(229, 217, 242));
        add(pnlPrincipal);

        JLabel lblTitulo = new Etiqueta("Cotizador de automoviles", 10, 10, 180);
        pnlPrincipal.add(lblTitulo);

        JLabel lblFecha = new Etiqueta("Fecha:", 620, 15, 70);
        pnlPrincipal.add(lblFecha);

        JTextField txtFecha = new Campo(670, 10, 100, false);
        txtFecha.setText(LocalDate.now().toString());
        pnlPrincipal.add(txtFecha);

        JLabel lblNumeroCotizacion = new Etiqueta("# Cotización:", 575, 45, 100);
        pnlPrincipal.add(lblNumeroCotizacion);

        JTextField txtNumeroCotizacion = new Campo(670, 40, 100, false);
        txtNumeroCotizacion.setText(String.valueOf(cotizacionRepositorio.obtenerNumeroCotizacion() + 1));
        pnlPrincipal.add(txtNumeroCotizacion);

        JPanel pnlCliente = new JPanel(null);
        pnlCliente.setBounds(10, 70, 760, 125);
        pnlCliente.setBackground(new Color(205, 193, 255));
        pnlPrincipal.add(pnlCliente);

        JLabel lblTituloCliente = new Etiqueta("Cliente", 5, 5, 70);
        pnlCliente.add(lblTituloCliente);

        JLabel lblNombre = new Etiqueta("Nombre:", 5, 35, 60);
        pnlCliente.add(lblNombre);

        JTextField txtNombre = new Campo(70, 30, 200, true);
        txtNombre.addKeyListener(new ValidarLongitud());
        pnlCliente.add(txtNombre);

        JLabel lblDireccion = new Etiqueta("Dirección:", 280, 35, 80);
        pnlCliente.add(lblDireccion);

        JTextField txtDireccion = new Campo(355, 30, 300, true);
        txtDireccion.addKeyListener(new ValidarLongitud());
        pnlCliente.add(txtDireccion);

        JLabel lblOcupacion = new Etiqueta("Ocupación:", 5, 65, 80);
        pnlCliente.add(lblOcupacion);

        ButtonGroup bgOcupacion = new ButtonGroup();

        JRadioButton rbtnEmpleado = new JRadioButton("Empleado");
        rbtnEmpleado.setBounds(100, 65, 100, 15);
        rbtnEmpleado.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rbtnEmpleado.setBackground(new Color(205, 193, 255));
        rbtnEmpleado.setSelected(true);

        JRadioButton rbtnEmpresario = new JRadioButton("Empresario");
        rbtnEmpresario.setBounds(250, 65, 120, 15);
        rbtnEmpresario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rbtnEmpresario.setBackground(new Color(205, 193, 255));

        bgOcupacion.add(rbtnEmpleado);
        bgOcupacion.add(rbtnEmpresario);

        pnlCliente.add(rbtnEmpleado);
        pnlCliente.add(rbtnEmpresario);

        JLabel lblEmpresa = new Etiqueta("Empresa:", 5, 95, 80);
        pnlCliente.add(lblEmpresa);

        JTextField txtEmpresa = new Campo(80, 90, 230, true);
        txtEmpresa.addKeyListener(new ValidarLongitud());
        pnlCliente.add(txtEmpresa);

        JLabel lblTelefono = new Etiqueta("Teléfono:", 335, 95, 80);
        pnlCliente.add(lblTelefono);

        JTextField txtTelefono = new Campo(405, 90, 200, true);
        pnlCliente.add(txtTelefono);
        txtTelefono.addKeyListener(new EsDigito());

        JPanel pnlAutos = new JPanel(null);
        pnlAutos.setBounds(10, 205, 760, 200);
        pnlAutos.setBackground(new Color(245, 239, 255));
        pnlPrincipal.add(pnlAutos);

        JLabel lblTituloAuto = new Etiqueta("Autos", 5, 5, 60);
        pnlAutos.add(lblTituloAuto);

        JLabel lblMarcas = new Etiqueta("Marca:", 5, 35, 50);
        pnlAutos.add(lblMarcas);

        cmbMarcas = new Selector(55, 30, 180);
        rellenarMarcas();
        pnlAutos.add(cmbMarcas);

        JLabel lblModelo = new Etiqueta("Modelo:", 270, 35, 60);
        pnlAutos.add(lblModelo);

        cmbModelo = new Selector(330, 30, 180);
        cmbModelo.setEnabled(false);
        pnlAutos.add(cmbModelo);

        JLabel lblVersion = new Etiqueta("Versión:", 540, 35, 60);
        pnlAutos.add(lblVersion);

        cmbVersion = new Selector(600, 30, 150);
        cmbVersion.setEnabled(false);
        pnlAutos.add(cmbVersion);

        cmbMarcas.addItemListener(e -> {

            if (cmbMarcas.getSelectedIndex() > 0) {

                rellenarModelos(cmbMarcas.getSelectedItem().toString());
                cmbModelo.setEnabled(true);
                return;
            }

            cmbModelo.setEnabled(false);
            cmbVersion.setEnabled(false);
            limpiarCamposAuto();
        });

        cmbModelo.addItemListener(e -> {
            if (cmbModelo.getSelectedIndex() > 0) {

                rellenarVersiones(cmbModelo.getSelectedItem().toString());
                cmbVersion.setEnabled(true);
                return;
            }
            limpiarCamposAuto();
            cmbVersion.setEnabled(false);
        });

        JLabel lblDescripcion = new Etiqueta("Carácteristicas:", 5, 65, 125);
        pnlAutos.add(lblDescripcion);

        txaDescripcion = new JTextArea();
        txaDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txaDescripcion.setEditable(false);

        JScrollPane spDescripcion = new JScrollPane(txaDescripcion);
        spDescripcion.setBounds(130, 65, 500, 60);
        spDescripcion.setViewportView(txaDescripcion);
        pnlAutos.add(spDescripcion);

        JLabel lblPrecio = new Etiqueta("Precio:", 5, 135, 60);
        pnlAutos.add(lblPrecio);

        txtPrecio = new Campo(55, 130, 160, false);
        pnlAutos.add(txtPrecio);

        cmbVersion.addItemListener(e -> {
            if (cmbVersion.getSelectedIndex() > 0) {
                rellenarAutomovil(cmbVersion.getSelectedItem().toString());
                return;
            }
            limpiarCamposAuto();
        });

        JLabel lblDescuento = new Etiqueta("Descuento:", 230, 135, 80);
        pnlAutos.add(lblDescuento);

        txtDescuento = new Campo(310, 130, 160, true);
        txtDescuento.setText("0");
        txtDescuento.addKeyListener(new EsDigito());
        pnlAutos.add(txtDescuento);

        JLabel lblEnganche = new Etiqueta("Enganche 35%:", 490, 135, 110);
        pnlAutos.add(lblEnganche);

        txtEnganche = new Campo(600, 130, 150, true);
        txtEnganche.addKeyListener(new EsDigito());
        pnlAutos.add(txtEnganche);

        JLabel lblFinanciamiento = new Etiqueta("Financiamiento:", 5, 170, 115);
        pnlAutos.add(lblFinanciamiento);

        txtFinanciamiento = new Campo(115, 165, 130, false);
        pnlAutos.add(txtFinanciamiento);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarEnganche();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarEnganche();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarEnganche();
            }
        };

        txtPrecio.getDocument().addDocumentListener(documentListener);

        txtDescuento.getDocument().addDocumentListener(documentListener);

        txtEnganche.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarFinanciamiento();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarFinanciamiento();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarFinanciamiento();
            }

            private void actualizarFinanciamiento() {

                String engancheText = txtEnganche.getText();

                if (!engancheText.isBlank()) {

                    double precio = Double.parseDouble(txtPrecio.getText());
                    double enganche = Double.parseDouble(engancheText);
                    double descuento = 0;

                    String descuentoText = txtDescuento.getText();

                    if (!descuentoText.isBlank()) {
                        try {
                            descuento = Double.parseDouble(descuentoText);
                        } catch (NumberFormatException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    BigDecimal precioFinanciamiento = BigDecimal.valueOf(precio);

                    precioFinanciamiento = precioFinanciamiento
                            .subtract(BigDecimal.valueOf(descuento))
                            .subtract(BigDecimal.valueOf(enganche))
                            .setScale(2, RoundingMode.HALF_UP);

                    txtFinanciamiento.setText(precioFinanciamiento.toString());
                }
            }
        });

        JLabel lblPlazos = new Etiqueta("Plazos:", 260, 170, 60);
        pnlAutos.add(lblPlazos);

        cmbPlazos = new Selector(310, 165, 150);
        cmbPlazos.addItem("");
        cmbPlazos.addItem(Plazos.DOCE.toString().toLowerCase());
        cmbPlazos.addItem(Plazos.VEINTICUATRO.toString().toLowerCase());
        cmbPlazos.addItem(Plazos.TREINTA_Y_SEIS.toString().toLowerCase().replace("_", " "));
        cmbPlazos.setEnabled(false);
        pnlAutos.add(cmbPlazos);

        JLabel lblInteres = new Etiqueta("Interes:", 470, 170, 70);
        pnlAutos.add(lblInteres);

        txtInteres = new Campo(525, 165, 100, false);
        pnlAutos.add(txtInteres);

        btnCalcular = new Boton("Calcular", 630, 165, 120, Boton.AZUL);
        btnCalcular.setEnabled(false);
        pnlAutos.add(btnCalcular);

        JTable tblCalculo = new JTable();
        tblCalculo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCalculo.setCellSelectionEnabled(false);
        tblCalculo.setDefaultEditor(Object.class, null);

        modelo = (DefaultTableModel) tblCalculo.getModel();

        modelo.addColumn("#PAGO");
        modelo.addColumn("CAPITAL");
        modelo.addColumn("INTERES");
        modelo.addColumn("PAGO TOTAL");
        modelo.addColumn("MONTO FINAL");
        modelo.addColumn("FECHA PAGO");

        tblCalculo.setModel(modelo);

        JScrollPane spTabla = new JScrollPane(tblCalculo);
        spTabla.setBounds(10, 415, 760, 200);
        pnlPrincipal.add(spTabla);

        btnGuardar = new Boton("Guardar", 500, 625, 120, Boton.AZUL);
        btnGuardar.setEnabled(false);
        pnlPrincipal.add(btnGuardar);

        JButton btnCancelar = new Boton("Cancelar", 650, 625, 120, Boton.ROJO);
        pnlPrincipal.add(btnCancelar);
        btnCancelar.addActionListener(e -> cmbMarcas.setSelectedIndex(0));

        cmbPlazos.addItemListener(e -> {
            modelo.getDataVector().clear();
            tblCalculo.repaint();
            btnGuardar.setEnabled(false);

            if (cmbPlazos.getSelectedIndex() > 0) {

                double engancheActualizado = Double.parseDouble(txtEnganche.getText());

                if (Double.compare(engancheActualizado, enganche.doubleValue()) < 0) {
                    JOptionPane.showMessageDialog(null,
                            "El enganche no debe de ser menor a: " + enganche, "Enganche incorrecto", JOptionPane.ERROR_MESSAGE);

                    cmbPlazos.setSelectedIndex(-1);
                    txtEnganche.setText(enganche.toString());
                    return;
                }

                Plazos plazos = obtenerPlazos();

                txtInteres.setText(plazos.getInteres() + "%");
                btnCalcular.setEnabled(true);
                txtDescuento.setEditable(false);
                txtEnganche.setEditable(false);
                return;
            }

            txtInteres.setText("");
            txtDescuento.setEditable(true);
            btnCalcular.setEnabled(false);
            txtEnganche.setEditable(true);

        });

        btnCalcular.addActionListener(e -> {

            double financiamiento = Double.parseDouble(txtFinanciamiento.getText());

            Plazos numeroPlazos = obtenerPlazos();

            BigDecimal capital = BigDecimal.valueOf(financiamiento / numeroPlazos.getPlazos());

            double interesTotal = calcularInteres(numeroPlazos, financiamiento);
            financiamiento += interesTotal;

            BigDecimal interes = BigDecimal.valueOf(interesTotal / numeroPlazos.getPlazos());

            BigDecimal pagoTotal = BigDecimal.valueOf(capital.doubleValue() + interes.doubleValue())
                    .setScale(2, RoundingMode.HALF_UP);

            LocalDate fechaActual = LocalDate.now();

            modelo.getDataVector().clear();

            for (int i = 1; i <= numeroPlazos.getPlazos(); i++) {
                financiamiento -= pagoTotal.doubleValue();

                modelo.addRow(new Object[]{
                        i,
                        capital.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                        interes.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                        pagoTotal,
                        BigDecimal.valueOf(financiamiento).setScale(2, RoundingMode.HALF_UP).doubleValue(),
                        fechaActual.plusMonths(i)
                });
            }

            btnGuardar.setEnabled(true);
        });

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String ocupacion = rbtnEmpleado.isSelected() ? rbtnEmpleado.getText() : rbtnEmpresario.getText();
            String empresa = txtEmpresa.getText();
            String telefono = txtTelefono.getText();

            if (nombre.isBlank() || direccion.isBlank() || ocupacion.isBlank() || empresa.isBlank() || telefono.isBlank()) {
                JOptionPane.showMessageDialog(
                        this, "Debes de llenar todos los campos del cliente", "DATOS CLIENTE", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numeroCotizacion = Integer.parseInt(txtNumeroCotizacion.getText());
            LocalDate fecha = LocalDate.parse(txtFecha.getText());
            double precio = automovil.precio();
            double descuento = Double.parseDouble(txtDescuento.getText());
            double enganche = Double.parseDouble(txtEnganche.getText());
            double montoFinanciar = Double.parseDouble(txtFinanciamiento.getText());
            Plazos plazos = obtenerPlazos();
            double interes = calcularInteres(plazos, montoFinanciar);

            Cotizacion cotizacion = new Cotizacion(numeroCotizacion, fecha, precio,
                    descuento, enganche, montoFinanciar, plazos.getPlazos(), interes, nombre,
                    direccion, ocupacion, empresa, telefono, automovil.id()
            );

            int respuesta = cotizacionRepositorio.crear(cotizacion);

            if (respuesta > 0) {
                JOptionPane.showMessageDialog(this, "Cotización guardada correctamente", "COTIZACIÓN", JOptionPane.INFORMATION_MESSAGE);
                numeroCotizacion++;

                txtNumeroCotizacion.setText(String.valueOf(numeroCotizacion));
                cmbMarcas.setSelectedIndex(0);
                txtNombre.setText("");
                txtDireccion.setText("");
                rbtnEmpleado.setSelected(true);
                txtEmpresa.setText("");
                txtTelefono.setText("");
                return;
            }

            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardad, favor de intentarlo más tarde.", "COTIZACIÓN", JOptionPane.ERROR_MESSAGE);
        });

        addWindowListener(new CerrarConexion());
    }

    private Plazos obtenerPlazos() {
        return Plazos.valueOf(cmbPlazos.getSelectedItem().toString()
                .toUpperCase()
                .replace(" ", "_"));
    }

    private double calcularInteres(Plazos plazos, double financiamiento) {

        return financiamiento * (plazos.getInteres() / 100.00);
    }

    private void actualizarEnganche() {
        try {
            String precioText = txtPrecio.getText();

            if (!precioText.isBlank()) {

                double precio = Double.parseDouble(precioText);
                double descuento = 0;
                String descuentoText = txtDescuento.getText();

                if (!descuentoText.isBlank()) {
                    try {
                        descuento = Double.parseDouble(descuentoText);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                BigDecimal precioBigDecimal = BigDecimal.valueOf(precio - descuento);
                enganche = precioBigDecimal.multiply(new BigDecimal("0.35"));
                enganche = enganche.setScale(2, RoundingMode.HALF_UP);
                txtEnganche.setText(enganche.toString());

                cmbPlazos.setEnabled(true);
            } else {
                txtEnganche.setText("");
                cmbPlazos.setEnabled(false);

            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void rellenarAutomovil(String version) {
        automovil = autoRepositorio.obtenerAutomovil(version);

        txaDescripcion.setText(automovil.obtenerCaracteristicas());
        txtPrecio.setText(String.valueOf(automovil.precio()));
    }

    private void rellenarVersiones(String modelo) {
        List<String> versiones = autoRepositorio.obtenerVersiones(modelo);
        cmbVersion.removeAllItems();
        cmbVersion.addItem("");
        versiones.forEach(cmbVersion::addItem);
    }

    private void rellenarModelos(String marca) {
        List<String> modelos = autoRepositorio.obtenerModelos(marca);

        cmbModelo.removeAllItems();
        cmbModelo.addItem("");
        modelos.forEach(cmbModelo::addItem);
    }

    private void rellenarMarcas() {

        List<String> marcas = autoRepositorio.obtenerMarcas();

        cmbMarcas.addItem("");
        marcas.forEach(cmbMarcas::addItem);
    }

    private void limpiarCamposAuto() {
        txaDescripcion.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("0");
        txtEnganche.setText("");
        txtFinanciamiento.setText("");
        cmbPlazos.setSelectedIndex(0);
        txtInteres.setText("");
        btnCalcular.setEnabled(false);
    }
}
