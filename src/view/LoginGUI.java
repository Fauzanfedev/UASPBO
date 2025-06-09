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
        // setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);

        // Use GridBagLayout for flexible layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblUsername, gbc);

        // Username text field
        tfUsername = new JTextField();
        tfUsername.setPreferredSize(new Dimension(200, 25));
        tfUsername.setToolTipText("Enter your username");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(tfUsername, gbc);

        // Password label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPassword, gbc);

        // Password field
        pfPassword = new JPasswordField();
        pfPassword.setPreferredSize(new Dimension(200, 25));
        pfPassword.setToolTipText("Enter your password");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(pfPassword, gbc);

        // Role label
        JLabel lblRole = new JLabel("Login as:");
        lblRole.setFont(new Font("Arial", Font.BOLD, 14));
        lblRole.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblRole, gbc);

        // Role combo box
        cbRole = new JComboBox<>(new String[]{"Pengunjung", "Pustakawan"});
        cbRole.setToolTipText("Select your role");
        cbRole.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(cbRole, gbc);

        // Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(59, 89, 182));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

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
