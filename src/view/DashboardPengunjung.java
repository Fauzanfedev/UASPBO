package view;

import javax.swing.*;
import java.awt.*;

public class DashboardPengunjung extends JFrame {
    private String userRole;
    private int currentUserId; // Add current user's anggota ID

    public DashboardPengunjung(String role, int currentUserId) {
        this.userRole = role;
        this.currentUserId = currentUserId;

        setTitle("Dashboard Pengunjung");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel label = new JLabel("Selamat Datang, Pengunjung", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnLihatBuku = new JButton("Lihat Daftar Buku");
        JButton btnRiwayat = new JButton("Riwayat Peminjaman");
        JButton btnLogout = new JButton("Logout");

        panel.add(label);
        panel.add(btnLihatBuku);
        panel.add(btnRiwayat);
        panel.add(btnLogout);

        add(panel);

        btnLihatBuku.addActionListener(e -> {
            BukuGUI bukuGUI = new BukuGUI(this, userRole); // Kirim referensi ini dan role
            bukuGUI.setVisible(true); // Menampilkan BukuGUI
            this.setVisible(false); // Menyembunyikan DashboardPengunjung
        });

        btnRiwayat.addActionListener(e -> {
            RiwayatGUI riwayatGUI = new RiwayatGUI(this, currentUserId); // Kirim referensi ini dan currentUserId
            riwayatGUI.setVisible(true); // Menampilkan RiwayatGUI
            this.setVisible(false); // Menyembunyikan DashboardPengunjung
        });

        btnLogout.addActionListener(e -> {
            new LoginGUI(); // Membuka LoginGUI
            this.dispose(); // Menutup DashboardPengunjung
        });

        setVisible(true);
    }
}
