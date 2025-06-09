package dao;

import model.Anggota;
import model.Buku;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {
    public static void simpan(Buku buku) {
        String sql = "INSERT INTO buku (judul, penulis, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getPenerbit());
            stmt.setInt(4, buku.getTahunTerbit());
            stmt.setInt(5, buku.getStok());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Buku buku = new Buku(
                    rs.getString("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("penerbit"),
                    rs.getInt("tahun_terbit"),
                    rs.getInt("stok")
                );
                list.add(buku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void hapusBuku(String id) {
        String sql = "DELETE FROM buku WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tambahBuku(Buku a) {
        String sql = "INSERT INTO buku (id, judul, penulis, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getId());
            stmt.setString(2, a.getJudul());
            stmt.setString(3, a.getPenulis());
            stmt.setString(4, a.getPenerbit());
            stmt.setInt(5, a.getTahunTerbit());
            stmt.setInt(6, a.getStok());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBuku(Buku a) {
        String sql = "UPDATE buku SET judul = ?, penulis = ?, penerbit = ?, tahun_terbit = ?, stok = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getJudul());
            stmt.setString(2, a.getPenulis());
            stmt.setString(3, a.getPenerbit());
            stmt.setInt(4, a.getTahunTerbit());
            stmt.setInt(5, a.getStok());
            stmt.setString(6, a.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStok(String idBuku, int stokBaru) {
    String sql = "UPDATE buku SET stok = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, stokBaru);
        stmt.setString(2, idBuku);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static int getStokById(String idBuku) {
        int stok = 0;
        String sql = "SELECT stok FROM buku WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idBuku);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stok = rs.getInt("stok");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stok;
    }


}