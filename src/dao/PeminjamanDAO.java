package dao;

import db.DatabaseConnection;
import model.Peminjaman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeminjamanDAO {
    public static void tambahPeminjaman(Peminjaman p) {
        String sql = "INSERT INTO peminjaman (id_anggota, id_buku, tanggal_pinjam, tanggal_kembali, status, status_pembayaran_denda) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdAnggota());
            stmt.setInt(2, p.getIdBuku());
            stmt.setDate(3, Date.valueOf(p.getTanggalPinjam()));
            if (p.getTanggalKembali() != null) {
                stmt.setDate(4, Date.valueOf(p.getTanggalKembali()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            stmt.setString(5, p.getStatus());
            stmt.setString(6, p.getStatusPembayaranDenda());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Peminjaman> getAll() {
        List<Peminjaman> list = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Peminjaman p = new Peminjaman(rs.getInt("id_anggota"), rs.getInt("id_buku"), 
                                                rs.getDate("tanggal_pinjam").toLocalDate(), 
                                                rs.getDate("tanggal_kembali") != null ? rs.getDate("tanggal_kembali").toLocalDate() : null);
                p.setId(rs.getInt("id"));
                p.setStatus(rs.getString("status"));
                p.setStatusPembayaranDenda(rs.getString("status_pembayaran_denda"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Peminjaman> getByIdAnggota(int idAnggota) {
        List<Peminjaman> list = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman WHERE id_anggota = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnggota);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Peminjaman p = new Peminjaman(rs.getInt("id_anggota"), rs.getInt("id_buku"),
                            rs.getDate("tanggal_pinjam").toLocalDate(),
                            rs.getDate("tanggal_kembali") != null ? rs.getDate("tanggal_kembali").toLocalDate() : null);
                    p.setId(rs.getInt("id"));
                    p.setStatus(rs.getString("status"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void hapusPeminjaman(int id) {
        String sql = "DELETE FROM peminjaman WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatusPengembalian(int id) {
        String sql = "UPDATE peminjaman SET status = 'dikembalikan' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatusAndTanggalKembali(int id, java.time.LocalDate tanggalKembali) {
        String sql = "UPDATE peminjaman SET status = 'dikembalikan', tanggal_kembali = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(tanggalKembali));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatusPembayaranDenda(int id, String statusPembayaranDenda) {
        String sql = "UPDATE peminjaman SET status_pembayaran_denda = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusPembayaranDenda);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
