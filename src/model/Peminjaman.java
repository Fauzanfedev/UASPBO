package model;

import java.time.LocalDate;

public class Peminjaman {
    private int id;
    private int idAnggota;
    private int idBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String status;
    private String statusPembayaranDenda;

    public Peminjaman(int idAnggota, int idBuku, LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.status = "dipinjam"; // Status default
        this.statusPembayaranDenda = "Belum Bayar"; // Default payment status
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAnggota() { return idAnggota; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public int getIdBuku() { return idBuku; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public void setTanggalPinjam(LocalDate tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public void setTanggalKembali(LocalDate tanggalKembali) { this.tanggalKembali = tanggalKembali; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStatusPembayaranDenda() { return statusPembayaranDenda; }
    public void setStatusPembayaranDenda(String statusPembayaranDenda) { this.statusPembayaranDenda = statusPembayaranDenda; }
}
