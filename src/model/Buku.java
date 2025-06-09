package model;

public class Buku {
    private String id;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahunTerbit;
    private int stok;

    public Buku(String id, String judul, String penulis, String penerbit, int tahunTerbit, int stok) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
    }

    // Getter dan Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getPenulis() { return penulis; }
    public void setPenulis(String penulis) { this.penulis = penulis; }

    public String getPenerbit() { return penerbit; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }

    public int getTahunTerbit() { return tahunTerbit; }
    public void setTahunTerbit(int tahunTerbit) { this.tahunTerbit = tahunTerbit; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}
