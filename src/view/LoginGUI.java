package view;

import dao.UserDAO;
import model.User;
import model.Anggota;
import dao.AnggotaDAO;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JComboBox<String> cbRole;

    public LoginGUI() {
        setTitle("Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        tfUsername = new JTextField();
        panel.add(tfUsername);

        panel.add(new JLabel("Password:"));
        pfPassword = new JPasswordField();
        panel.add(pfPassword);

        panel.add(new JLabel("Login as:"));
        cbRole = new JComboBox<>(new String[]{"Pengunjung", "Pustakawan"});
        panel.add(cbRole);

        JButton btnLogin = new JButton("Login");
        panel.add(new JLabel()); // placeholder
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(e -> prosesLogin());

        setVisible(true);
    }

    private void prosesLogin() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword()).trim();
        String selectedRole = (String) cbRole.getSelectedItem();

        if ("pustakawan".equalsIgnoreCase(selectedRole)) {
            User user = UserDAO.login(username, password);
            if (user != null) {
                if (selectedRole.equalsIgnoreCase(user.getRole())) {
                    JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + user.getRole());
                    dispose();
                    new DashboardPustakawan("pustakawan");
                } else {
                    JOptionPane.showMessageDialog(this, "Role yang dipilih tidak sesuai dengan akun!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("pengunjung".equalsIgnoreCase(selectedRole)) {
            Anggota anggota = AnggotaDAO.login(username, password);
            if (anggota != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil sebagai Pengunjung");
                dispose();
                new DashboardPengunjung("pengunjung", anggota.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Role tidak dikenali", "Error", JOptionPane.ERROR_MESSAGE);
            new LoginGUI(); // fallback
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
