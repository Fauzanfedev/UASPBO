package view;

import javax.swing.*;

import model.Peminjaman;

import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardGUI extends JFrame {
    private String userRole;

    private JButton btnKelolaAnggota;
    private JButton btnKelolaBuku;
    private JButton btnPeminjaman;
    private JButton btnPengembalian;

    public DashboardGUI(String role) {
        this.userRole = role;

        setTitle("Dashboard Perpustakaan");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        btnKelolaAnggota = new JButton("Kelola Anggota");
        btnKelolaBuku = new JButton("Kelola Buku");
        btnPeminjaman = new JButton("Peminjaman Buku");
        btnPengembalian = new JButton("Pengembalian Buku");

        add(btnKelolaAnggota);
        add(btnKelolaBuku);
        add(btnPeminjaman);
        add(btnPengembalian);

        btnKelolaAnggota.addActionListener((ActionEvent e) -> {
            this.setVisible(false); // Sembunyikan Dashboard saat buka GUI baru
            new AnggotaGUI(this);
        });
        btnKelolaBuku.addActionListener((ActionEvent e) -> {
            this.setVisible(false);
            new BukuGUI(this, userRole);
        });
        btnPeminjaman.addActionListener((ActionEvent e) -> {
            this.setVisible(false);
            new PeminjamanGUI(this);
        });
        btnPengembalian.addActionListener((ActionEvent e) -> {
            this.setVisible(false);
            new PengembalianGUI(this);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new DashboardGUI("pustakawan");
    }
}
