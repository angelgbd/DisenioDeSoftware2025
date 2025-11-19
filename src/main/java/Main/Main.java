package Main;

import Vistas.FrameReportes;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el diseño del sistema, usando default.");
        }

        
        SwingUtilities.invokeLater(() -> {
            FrameReportes ventana = new FrameReportes();
            ventana.setVisible(true);
        });
    }
}
