import view.LoginGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Tampilan modern look & feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}
