package view;

import dao.PeminjamanDAO;
import model.Peminjaman;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PeminjamanGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame parentDashboard; // Simpan referensi parent dashboard

    public PeminjamanGUI(JFrame parent) {
        this.parentDashboard = parent; // Simpan referensi parent

        setTitle("Kelola Peminjaman");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelTengah = new JPanel(new BorderLayout());
        String[] kolom = {"ID Peminjaman", "ID Anggota", "ID Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panelTengah.add(scrollPane, BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");

        JPanel panelBawah = new JPanel();
        panelBawah.add(btnKembali);

        setLayout(new BorderLayout(10, 10));
        add(panelTengah, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        loadPeminjamanData();

        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup PeminjamanGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

        setVisible(true);
    }

    private void loadPeminjamanData() {
        tableModel.setRowCount(0);
        List<Peminjaman> list = PeminjamanDAO.getAll();
        if (list == null || list.isEmpty()) {
            System.out.println("No loan data found.");
            return;
        }
        for (Peminjaman p : list) {
            Object[] data = {
                    p.getId(),
                    p.getIdAnggota(),
                    p.getIdBuku(),
                    p.getTanggalPinjam() != null ? p.getTanggalPinjam() : "N/A",
                    p.getTanggalKembali() != null ? p.getTanggalKembali() : "N/A",
                    p.getStatus() != null ? p.getStatus() : "N/A"
            };
            tableModel.addRow(data);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PeminjamanGUI(new DashboardPustakawan("pustakawan"))); // Contoh pemanggilan
    }
}
