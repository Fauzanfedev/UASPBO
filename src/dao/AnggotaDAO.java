package dao;

import db.DatabaseConnection;
import model.Anggota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnggotaDAO {
    public List<Anggota> getAll() {
        List<Anggota> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM anggota";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Anggota a = new Anggota(
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("email")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Anggota login(String username, String password) {
        Anggota anggota = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM anggota WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                anggota = new Anggota(
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anggota;
    }

    public static void tambahAnggota(Anggota a) {
        String sql = "INSERT INTO anggota (nim, nama, jurusan, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNim());
            stmt.setString(2, a.getNama());
            stmt.setString(3, a.getJurusan());
            stmt.setString(4, a.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAnggota(Anggota a) {
        String sql = "UPDATE anggota SET nama = ?, jurusan = ?, email = ? WHERE nim = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNama());
            stmt.setString(2, a.getJurusan());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getNim());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusAnggota(String nim) {
        String sql = "DELETE FROM anggota WHERE nim = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nim);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}