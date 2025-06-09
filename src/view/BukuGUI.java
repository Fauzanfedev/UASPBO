package view;

import dao.AnggotaDAO;
import dao.BukuDAO;
import model.Anggota;
import model.Buku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.util.List;

public class BukuGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfId, tfJudul, tfPenulis, tfPenerbit, tfTahunTerbit, tfStok;
    private JFrame parentDashboard; // Simpan referensi parent dashboard
    private String userRole;

    public BukuGUI(JFrame parent, String role) {
        this.parentDashboard = parent; // Simpan referensi parent
        this.userRole = role;

        setTitle("Kelola Buku");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0 - ID label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        tfId = new JTextField(10);
        panelForm.add(tfId, gbc);

        // ID and Judul spacing
        gbc.gridx = 2;
        panelForm.add(Box.createHorizontalStrut(20), gbc);

        // Judul label and text field
        gbc.gridx = 3;
        panelForm.add(new JLabel("Judul:"), gbc);

        gbc.gridx = 4;
        tfJudul = new JTextField(20);
        panelForm.add(tfJudul, gbc);

        // Row 1 - Penulis label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(new JLabel("Penulis:"), gbc);

        gbc.gridx = 1;
        tfPenulis = new JTextField(15);
        panelForm.add(tfPenulis, gbc);

        // Penulis and Penerbit spacing
        gbc.gridx = 2;
        panelForm.add(Box.createHorizontalStrut(20), gbc);

        // Penerbit label and text field
        gbc.gridx = 3;
        panelForm.add(new JLabel("Penerbit:"), gbc);

        gbc.gridx = 4;
        tfPenerbit = new JTextField(15);
        panelForm.add(tfPenerbit, gbc);

        // Row 2 - Tahun Terbit label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Tahun Terbit:"), gbc);

        gbc.gridx = 1;
        tfTahunTerbit = new JTextField(8);
        panelForm.add(tfTahunTerbit, gbc);

        // Tahun Terbit and Stok spacing
        gbc.gridx = 2;
        panelForm.add(Box.createHorizontalStrut(20), gbc);

        // Stok label and text field
        gbc.gridx = 3;
        panelForm.add(new JLabel("Stok:"), gbc);

        gbc.gridx = 4;
        tfStok = new JTextField(5);
        panelForm.add(tfStok, gbc);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKembali = new JButton("Kembali");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnTambah);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnHapus);
        panelButtons.add(btnKembali);

        String[] kolom = {"ID", "Judul", "Penulis", "Penerbit", "Tahun Terbit", "Stok"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout(10, 10));
        if ("pustakawan".equalsIgnoreCase(userRole)) {
            add(panelForm, BorderLayout.NORTH);
            add(panelButtons, BorderLayout.SOUTH);
        } else if ("pengunjung".equalsIgnoreCase(userRole)) {
            // For pengunjung, add Kembali and Pinjam Buku buttons
            JButton btnPinjam = new JButton("Pinjam Buku");
            JPanel panelButtonsPengunjung = new JPanel();
            panelButtonsPengunjung.add(btnPinjam);
            panelButtonsPengunjung.add(btnKembali);
            add(panelButtonsPengunjung, BorderLayout.SOUTH);

btnPinjam.addActionListener(e -> {
    // Implement pinjam buku action for pengunjung
    String nim = JOptionPane.showInputDialog(this, "Masukkan NIM/NIP Anda:");
    if (nim == null || nim.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "NIM/NIP wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    nim = nim.trim();

    // Validate anggota by nim
    Anggota anggota = null;
    for (Anggota a : new dao.AnggotaDAO().getAll()) {
        if (a.getNim().equals(nim)) {
            anggota = a;
            break;
        }
    }
    if (anggota == null) {
        JOptionPane.showMessageDialog(this, "Anggota dengan NIM tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get selected book ID from table
    int selectedRow = table.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dipinjam dari tabel!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String idBukuStr = tableModel.getValueAt(selectedRow, 0).toString();
    model.Buku buku = null;
    for (model.Buku b : dao.BukuDAO.getAll()) {
        if (b.getId().equals(idBukuStr)) {
            buku = b;
            break;
        }
    }
    if (buku == null) {
        JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (buku.getStok() <= 0) {
        JOptionPane.showMessageDialog(this, "Stok buku habis, tidak dapat dipinjam!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Create peminjaman record
    java.time.LocalDate now = java.time.LocalDate.now();
    model.Peminjaman peminjaman = new model.Peminjaman(anggota.getId(), Integer.parseInt(buku.getId()), now, null);
    dao.PeminjamanDAO.tambahPeminjaman(peminjaman);

    // Update stok buku
    buku.setStok(buku.getStok() - 1);
    dao.BukuDAO.updateBuku(buku);

    JOptionPane.showMessageDialog(this, "Buku berhasil dipinjam!");
    loadData();
});
        }
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        if ("pustakawan".equalsIgnoreCase(userRole)) {
            btnTambah.addActionListener(e -> tambahBuku());
            btnUpdate.addActionListener(e -> updateBuku());
            btnHapus.addActionListener(e -> hapusBuku());
        }
        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup AnggotaGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

       if ("pustakawan".equalsIgnoreCase(userRole)) {
           table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        tfId.setText(tableModel.getValueAt(row, 0).toString());
                        tfJudul.setText(tableModel.getValueAt(row, 1).toString());
                        tfPenulis.setText(tableModel.getValueAt(row, 2).toString());
                        tfPenerbit.setText(tableModel.getValueAt(row, 3).toString());
                        tfTahunTerbit.setText(tableModel.getValueAt(row, 4).toString());
                        tfStok.setText(tableModel.getValueAt(row, 5).toString());
                        tfId.setEditable(false); // NIM tidak boleh diubah saat update
                    }
                }
            });
       }

       // Add reference to RiwayatGUI for refreshing after borrowing
       RiwayatGUI riwayatGUI = null;

       if ("pengunjung".equalsIgnoreCase(userRole)) {
           JButton btnPinjam = new JButton("Pinjam Buku");
           JPanel panelButtonsPengunjung = new JPanel();
           panelButtonsPengunjung.add(btnPinjam);
           panelButtonsPengunjung.add(btnKembali);
           add(panelButtonsPengunjung, BorderLayout.SOUTH);

           btnPinjam.addActionListener(e -> {
               String nim = JOptionPane.showInputDialog(this, "Masukkan NIM Anda:");
               if (nim == null || nim.trim().isEmpty()) {
                   JOptionPane.showMessageDialog(this, "NIM wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }
               nim = nim.trim();

               // Validate anggota by nim
               Anggota anggota = null;
               for (Anggota a : new dao.AnggotaDAO().getAll()) {
                   if (a.getNim().equals(nim)) {
                       anggota = a;
                       break;
                   }
               }
               if (anggota == null) {
                   JOptionPane.showMessageDialog(this, "Anggota dengan NIM tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               // Get selected book ID from table
               int selectedRow = table.getSelectedRow();
               if (selectedRow < 0) {
                   JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dipinjam dari tabel!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }
               String idBukuStr = tableModel.getValueAt(selectedRow, 0).toString();
               model.Buku buku = null;
               for (model.Buku b : dao.BukuDAO.getAll()) {
                   if (b.getId().equals(idBukuStr)) {
                       buku = b;
                       break;
                   }
               }
               if (buku == null) {
                   JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               if (buku.getStok() <= 0) {
                   JOptionPane.showMessageDialog(this, "Stok buku habis, tidak dapat dipinjam!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               // Create peminjaman record
               java.time.LocalDate now = java.time.LocalDate.now();
               model.Peminjaman peminjaman = new model.Peminjaman(anggota.getId(), Integer.parseInt(buku.getId()), now, null);
               dao.PeminjamanDAO.tambahPeminjaman(peminjaman);

               // Update stok buku
               buku.setStok(buku.getStok() - 1);
               dao.BukuDAO.updateBuku(buku);

               JOptionPane.showMessageDialog(this, "Buku berhasil dipinjam!");
               loadData();

               // Refresh RiwayatGUI if open
               if (riwayatGUI != null) {
                   riwayatGUI.loadData();
               }
           });
       }

        setVisible(true);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Buku> list = BukuDAO.getAll();
        for (Buku b : list) {
            Object[] data = {
                b.getId(),
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit(),
                b.getTahunTerbit(),
                b.getStok()
            };
            tableModel.addRow(data);
        }
    }

    private void tambahBuku() {
        String id = tfId.getText().trim();
        String judul = tfJudul.getText().trim();
        String penulis = tfPenulis.getText().trim();
        String penerbit = tfPenerbit.getText().trim();
        String tahun_terbit = tfTahunTerbit.getText().trim();
        String stok = tfStok.getText().trim();

        if (id.isEmpty() || judul.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID dan Judul wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tahun = Integer.parseInt(tahun_terbit);
        int stokBuku = Integer.parseInt(stok);
        Buku buku = new Buku(id, judul, penulis, penerbit, tahun, stokBuku);
        BukuDAO.tambahBuku(buku);
        JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!");
        clearForm();
        loadData();
    }

    private void updateBuku() {
        String id = tfId.getText().trim();
        String judul = tfJudul.getText().trim();
        String penulis = tfPenulis.getText().trim();
        String penerbit = tfPenerbit.getText().trim();
        String tahun_terbit = tfTahunTerbit.getText().trim();
        String stok = tfStok.getText().trim();

        if (id.isEmpty() || judul.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID dan Judul wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tahun = Integer.parseInt(tahun_terbit);
        int stokBuku = Integer.parseInt(stok);
        Buku buku = new Buku(id, judul, penulis, penerbit, tahun, stokBuku);
        BukuDAO.updateBuku(buku);
        JOptionPane.showMessageDialog(this, "Buku berhasil diupdate!");
        clearForm();
        loadData();
        tfId.setEditable(true);
    }

    private void hapusBuku() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus buku dengan ID " + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            BukuDAO.hapusBuku(id);
            JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!");
            clearForm();
            loadData();
            tfId.setEditable(true);
        }
    }

    private void clearForm() {
        tfId.setText("");
        tfJudul.setText("");
        tfPenulis.setText("");
        tfPenerbit.setText("");
        tfTahunTerbit.setText("");
        tfStok.setText("");
        tfId.setEditable(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BukuGUI(new DashboardPustakawan("pustakawan"), "pustakawan")); // Contoh pemanggilan
    }
}
