package view;

import dao.PeminjamanDAO;
import model.Peminjaman;
import view.DashboardPengunjung;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RiwayatGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame parentDashboard; // Simpan referensi parent dashboard
    private int currentUserId; // Store current user's anggota ID

    public RiwayatGUI(JFrame parent, int currentUserId) {
        this.parentDashboard = parent; // Simpan referensi parent
        this.currentUserId = currentUserId;

        setTitle("Riwayat Peminjaman");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] kolom = {"ID Peminjaman", "Judul", "ID Anggota", "ID Buku", "Tanggal Pinjam", "Tenggat Kembali", "Status", "Denda"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        loadData();

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup RiwayatGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

        JPanel panelBawah = new JPanel();
        panelBawah.add(btnKembali);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        setVisible(true);

        // Adjust column widths
        int[] columnWidths = {80, 250, 80, 80, 120, 120, 100, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        // Remove hiding the last column (ID Peminjaman)
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Peminjaman> list = PeminjamanDAO.getByIdAnggota(currentUserId);
        java.util.List<model.Buku> listBuku = dao.BukuDAO.getAll();
        for (Peminjaman p : list) {
            String judulBuku = "";
            for (model.Buku b : listBuku) {
                if (b.getId().equals(String.valueOf(p.getIdBuku()))) {
                    judulBuku = b.getJudul();
                    break;
                }
            }
            // Calculate denda
            long denda = 0;
            java.time.LocalDate tanggalPinjam = p.getTanggalPinjam();
            java.time.LocalDate tenggatKembali = tanggalPinjam.plusDays(7); // tenggat kembali 7 hari dari tanggal pinjam
            java.time.LocalDate tanggalKembali = p.getTanggalKembali();
            java.time.LocalDate today = java.time.LocalDate.now();
            int dendaPerHari = 5000;

            if (tanggalKembali == null) {
                // Not returned yet, calculate based on today
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(tenggatKembali, today);
                if (daysLate > 0) {
                    denda = daysLate * dendaPerHari;
                }
            } else {
                // Returned, calculate based on tanggalKembali
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(tenggatKembali, tanggalKembali);
                if (daysLate > 0) {
                    denda = daysLate * dendaPerHari;
                }
            }

            Object[] data = {
                p.getId(),
                judulBuku,
                p.getIdAnggota(),
                p.getIdBuku(),
                p.getTanggalPinjam(),
                tenggatKembali,
                p.getStatus(),
                denda
            };
            tableModel.addRow(data);
        }
    }


    public static void main(String[] args) {
        // Since constructor requires currentUserId, provide a dummy value (e.g., 1) for testing
        SwingUtilities.invokeLater(() -> new RiwayatGUI(new DashboardPengunjung("pengunjung", 1), 1)); // Contoh pemanggilan
    }
}
