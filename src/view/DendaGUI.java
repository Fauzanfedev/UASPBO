package view;

import dao.PeminjamanDAO;
import model.Peminjaman;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DendaGUI extends JFrame {
    private JTable table;
    private JFrame parentDashboard; // Simpan referensi parent dashboard

    public DendaGUI(JFrame parent) {
        this.parentDashboard = parent; // Simpan referensi parent

        setTitle("Laporan Denda");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup DendaGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

        loadDenda();

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnKembali, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadDenda() {
        List<Peminjaman> list = PeminjamanDAO.getAll();
        List<model.Buku> bukuList = dao.BukuDAO.getAll();
        List<model.Anggota> anggotaList = new dao.AnggotaDAO().getAll();

        Map<Integer, model.Buku> bukuMap = bukuList.stream()
                .collect(Collectors.toMap(b -> Integer.parseInt(b.getId()), b -> b));
        Map<Integer, model.Anggota> anggotaMap = anggotaList.stream()
                .collect(Collectors.toMap(model.Anggota::getId, a -> a));

        String[] columnNames = {"ID Peminjaman", "ID Anggota", "Judul Buku", "Jumlah Denda", "Status Pembayaran"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only "Status Pembayaran" column is editable
                return column == 4;
            }
        };

        for (Peminjaman p : list) {
                if (p.getTanggalKembali() != null) {
                    long daysLate = ChronoUnit.DAYS.between(p.getTanggalKembali(), LocalDate.now());
                    if (daysLate > 0) {
                        int fine = (int) daysLate * 5000;
                        String judulBuku = bukuMap.containsKey(p.getIdBuku()) ? bukuMap.get(p.getIdBuku()).getJudul() : "Unknown";
                        String statusPembayaran = p.getStatusPembayaranDenda() != null ? p.getStatusPembayaranDenda() : "Belum Bayar";
                        model.addRow(new Object[]{
                                p.getId(),
                                p.getIdAnggota(),
                                judulBuku,
                                fine,
                                statusPembayaran
                        });
                    }
                }
        }
        table.setModel(model);

        // Add listener to update payment status on cell edit
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 4) { // Status Pembayaran column
                int row = e.getFirstRow();
                int idPeminjaman = (int) table.getValueAt(row, 0);
                String newStatus = (String) table.getValueAt(row, 4);
                // Update status in database
                dao.PeminjamanDAO.updateStatusPembayaranDenda(idPeminjaman, newStatus);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DendaGUI(new DashboardPustakawan("pustakawan"))); // Contoh pemanggilan
    }
}
