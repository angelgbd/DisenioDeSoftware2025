package Vistas;

import Negocio.GestorReportes;
import DTOs.ReporteAgendaDTO;
import DTOs.ReporteHistorialDTO;
import Interfaces.IGestorReportes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrameReportes extends JFrame {

    // Componentes de la UI
    private JComboBox<String> cmbTipoUsuario;
    private JTextField txtIdUsuario;
    private JTable tblReportes;
    private DefaultTableModel modeloTabla;
    private JLabel lblTitulo;
    private final IGestorReportes gestorReportes; // Conexion con Negocio

    public FrameReportes() {
        // Se inicia la capa de Vistas
        this.gestorReportes = new GestorReportes();
        
        configurarVentana();
        iniciarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema Hospitalario - Módulo de Reportes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Se centra la pantalla
        setLayout(new BorderLayout());
    }

    private void iniciarComponentes() {
        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        panelSuperior.setBackground(new Color(240, 245, 250));
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Selector de Roles
        panelSuperior.add(new JLabel("Ver como:"));
        cmbTipoUsuario = new JComboBox<>(new String[]{"Paciente (Ver Historial)", "Doctor (Ver Agenda)"});
        panelSuperior.add(cmbTipoUsuario);

        // Campo de ID
        panelSuperior.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField(10);
        panelSuperior.add(txtIdUsuario);

        // Boton para consultar
        JButton btnConsultar = new JButton("Consultar Reportes");
        btnConsultar.setBackground(new Color(50, 120, 200));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFocusPainted(false);
        btnConsultar.addActionListener(e -> consultarReportes());
        panelSuperior.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(new EmptyBorder(10, 20, 20, 20));

        lblTitulo = new JLabel("Resultados de la búsqueda");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelCentral.add(lblTitulo, BorderLayout.NORTH);

        tblReportes = new JTable();
        tblReportes.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tblReportes);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);
    }

    private void consultarReportes() {
        // Se limpia la tabla actual
        modeloTabla = new DefaultTableModel();
        
        // Se obtienen los datos de entrada
        String seleccion = (String) cmbTipoUsuario.getSelectedItem();
        String textoId = txtIdUsuario.getText().trim();

        try {
            // Se hace un parseo del ID
            Long id = Long.parseLong(textoId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (seleccion.contains("Paciente")) {
                configurarTablaPaciente();
                // Se llama la capa de negocio
                List<ReporteHistorialDTO> lista = gestorReportes.obtenerHistorialPaciente(id);
                
                // Se llena la tabla
                for (ReporteHistorialDTO dto : lista) {
                    modeloTabla.addRow(new Object[]{
                        dto.getFechaHora().format(formatter),
                        dto.getNombreDoctor(),
                        dto.getEspecialidad(),
                        dto.getDiagnostico(),
                        dto.getEstado()
                    });
                }
                lblTitulo.setText("Historial Médico del Paciente (ID: " + id + ")");

            } else {
                configurarTablaDoctor();
                // Se llama a la capa de Negocio
                List<ReporteAgendaDTO> lista = gestorReportes.obtenerAgendaDoctor(id);
                
                // Se llena la tabla
                for (ReporteAgendaDTO dto : lista) {
                    modeloTabla.addRow(new Object[]{
                        dto.getFechaHora().format(formatter),
                        dto.getNombrePersona(), // Aquí es el nombre del paciente
                        dto.getDatoAuxiliar(),  // Aquí es el DNI
                        dto.getEstado()
                    });
                }
                lblTitulo.setText("Agenda del Doctor (ID: " + id + ")");
            }

            tblReportes.setModel(modeloTabla);

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron registros.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // Aqui caen las validaciones o errores de la base de datos
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error del Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos auxiliares segun el reporte
    private void configurarTablaPaciente() {
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Doctor");
        modeloTabla.addColumn("Especialidad");
        modeloTabla.addColumn("Diagnóstico"); 
        modeloTabla.addColumn("Estado");
    }

    private void configurarTablaDoctor() {
        modeloTabla.addColumn("Fecha y Hora");
        modeloTabla.addColumn("Paciente");
        modeloTabla.addColumn("DNI Paciente"); 
        modeloTabla.addColumn("Estado Cita");
    }
}
