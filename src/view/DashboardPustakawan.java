package view;

import javax.swing.*;
import java.awt.*;

public class DashboardPustakawan extends JFrame {
    private String userRole;

    public DashboardPustakawan(String role) {
        this.userRole = role;

        setTitle("Dashboard Pustakawan");
        // setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel label = new JLabel("Selamat Datang, Pustakawan", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnBuku = new JButton("Kelola Buku");
        JButton btnAnggota = new JButton("Kelola Anggota");
        JButton btnPeminjaman = new JButton("Kelola Peminjaman");
        JButton btnPengembalian = new JButton("Pengembalian Buku");
        JButton btnDenda = new JButton("Laporan Denda");
        JButton btnLogout = new JButton("Logout");

        panel.add(label);
        panel.add(btnBuku);
        panel.add(btnAnggota);
        panel.add(btnPeminjaman);
        panel.add(btnPengembalian);
        panel.add(btnDenda);
        panel.add(btnLogout);

        add(panel);

        btnBuku.addActionListener(e -> {
            BukuGUI bukuGUI = new BukuGUI(this, userRole); // Kirim referensi ini dan role
            bukuGUI.setVisible(true); // Menampilkan BukuGUI
            this.setVisible(false); // Menyembunyikan DashboardPustakawan
        });

        btnAnggota.addActionListener(e -> {
            AnggotaGUI anggotaGUI = new AnggotaGUI(this); // Kirim referensi ini
            anggotaGUI.setVisible(true); // Menampilkan AnggotaGUI
            this.setVisible(false); // Menyembunyikan DashboardPustakawan
        });

        btnPeminjaman.addActionListener(e -> {
            PeminjamanGUI peminjamanGUI = new PeminjamanGUI(this); // Kirim referensi ini
            peminjamanGUI.setVisible(true); // Menampilkan PeminjamanGUI
            this.setVisible(false); // Menyembunyikan DashboardPustakawan
        });

        btnPengembalian.addActionListener(e -> {
            PengembalianGUI pengembalianGUI = new PengembalianGUI(this); // Kirim referensi ini
            pengembalianGUI.setVisible(true); // Menampilkan PengembalianGUI
            this.setVisible(false); // Menyembunyikan DashboardPustakawan
        });

        btnDenda.addActionListener(e -> {
            DendaGUI dendaGUI = new DendaGUI(this); // Kirim referensi ini
            dendaGUI.setVisible(true); // Menampilkan DendaGUI
            this.setVisible(false); // Menyembunyikan DashboardPustakawan
        });

        btnLogout.addActionListener(e -> {
            new LoginGUI(); // Membuka LoginGUI
            this.dispose(); // Menutup DashboardPustakawan
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardPustakawan("pustakawan")); // Menjalankan aplikasi dengan role pustakawan
    }
}
