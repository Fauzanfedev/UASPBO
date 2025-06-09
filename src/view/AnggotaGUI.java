package view;

import model.Anggota;
import dao.AnggotaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AnggotaGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfNim, tfNama, tfJurusan, tfEmail;
    private JFrame parentDashboard; // Simpan referensi parent dashboard

    public AnggotaGUI(JFrame parent) {
        this.parentDashboard = parent; // Simpan referensi parent

        setTitle("Kelola Anggota");
        // setSize(700, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("NIM:"));
        tfNim = new JTextField();
        panelForm.add(tfNim);

        panelForm.add(new JLabel("Nama:"));
        tfNama = new JTextField();
        panelForm.add(tfNama);

        panelForm.add(new JLabel("Jurusan:"));
        tfJurusan = new JTextField();
        panelForm.add(tfJurusan);

        panelForm.add(new JLabel("Email:"));
        tfEmail = new JTextField();
        panelForm.add(tfEmail);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKembali = new JButton("Kembali");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnTambah);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnHapus);
        panelButtons.add(btnKembali);

        String[] kolom = {"NIM", "Nama", "Jurusan", "Email"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout(10, 10));
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        loadData();

        btnTambah.addActionListener(e -> tambahAnggota());
        btnUpdate.addActionListener(e -> updateAnggota());
        btnHapus.addActionListener(e -> hapusAnggota());
        btnKembali.addActionListener(e -> {
            this.dispose(); // Tutup AnggotaGUI
            parentDashboard.setVisible(true); // Tampilkan dashboard induk
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    tfNim.setText(tableModel.getValueAt(row, 0).toString());
                    tfNama.setText(tableModel.getValueAt(row, 1).toString());
                    tfJurusan.setText(tableModel.getValueAt(row, 2).toString());
                    tfEmail.setText(tableModel.getValueAt(row, 3).toString());
                    tfNim.setEditable(false); // NIM tidak boleh diubah saat update
                }
            }
        });

        setVisible(true);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Anggota> list = new AnggotaDAO().getAll();
        for (Anggota a : list) {
            Object[] data = {a.getNim(), a.getNama(), a.getJurusan(), a.getEmail()};
            tableModel.addRow(data);
        }
    }

    private void tambahAnggota() {
        String nim = tfNim.getText().trim();
        String nama = tfNama.getText().trim();
        String jurusan = tfJurusan.getText().trim();
        String email = tfEmail.getText().trim();

        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM dan Nama wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Anggota anggota = new Anggota(nim, nama, jurusan, email);
        AnggotaDAO.tambahAnggota(anggota);
        JOptionPane.showMessageDialog(this, "Anggota berhasil ditambahkan!");
        clearForm();
        loadData();
    }

    private void updateAnggota() {
        String nim = tfNim.getText().trim();
        String nama = tfNama.getText().trim();
        String jurusan = tfJurusan.getText().trim();
        String email = tfEmail.getText().trim();

        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM dan Nama wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Anggota a = new Anggota(nim, nama, jurusan, email);
        AnggotaDAO.updateAnggota(a);
        JOptionPane.showMessageDialog(this, "Anggota berhasil diupdate!");
        clearForm();
        loadData();
        tfNim.setEditable(true);
    }

    private void hapusAnggota() {
        String nim = tfNim.getText().trim();
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus anggota dengan NIM " + nim + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            AnggotaDAO.hapusAnggota(nim);
            JOptionPane.showMessageDialog(this, "Anggota berhasil dihapus!");
            clearForm();
            loadData();
            tfNim.setEditable(true);
        }
    }

    private void clearForm() {
        tfNim.setText("");
        tfNama.setText("");
        tfJurusan.setText("");
        tfEmail.setText("");
        tfNim.setEditable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnggotaGUI(new DashboardPustakawan("pustakawan"))); // Contoh pemanggilan
    }
}
