package view;

import dao.PeminjamanDAO;
import model.Peminjaman;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PengembalianGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame parentDashboard; // Simpan referensi parent dashboard

    public PengembalianGUI(JFrame parent) {
        this.parentDashboard = parent; // Simpan referensi parent

        setTitle("Pengembalian Buku");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] kolom = {"ID", "ID Anggota", "ID Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        loadData();

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup PengembalianGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

        JButton btnProsesPengembalian = new JButton("Proses Pengembalian");
        btnProsesPengembalian.addActionListener(e -> prosesPengembalian());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnProsesPengembalian);
        panelButtons.add(btnKembali);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Peminjaman> list = PeminjamanDAO.getAll();
        for (Peminjaman p : list) {
            if ("dipinjam".equals(p.getStatus())) {
                Object[] data = {
                    p.getId(),
                    p.getIdAnggota(),
                    p.getIdBuku(),
                    p.getTanggalPinjam(),
                    p.getTanggalKembali(),
                    p.getStatus()
                };
                tableModel.addRow(data);
            }
        }
    }

    private void prosesPengembalian() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int idPeminjaman = (int) tableModel.getValueAt(selectedRow, 0);
            int idBuku = (int) tableModel.getValueAt(selectedRow, 2);
            java.time.LocalDate today = java.time.LocalDate.now();
            PeminjamanDAO.updateStatusAndTanggalKembali(idPeminjaman, today);

            // Update stock of the returned book
            int currentStock = dao.BukuDAO.getStokById(String.valueOf(idBuku));
            dao.BukuDAO.updateStok(String.valueOf(idBuku), currentStock + 1);

            JOptionPane.showMessageDialog(this, "Pengembalian berhasil diproses!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman yang ingin dikembalikan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PengembalianGUI(new DashboardPustakawan("pustakawan"))); // Contoh pemanggilan
    }
}
