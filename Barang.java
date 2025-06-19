public class Barang implements Entitas {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Barang(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKode() { return kode; }    
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    public void kurangiStok(int jumlah) { this.stok -= jumlah; }

    @Override
    public String getInfo() {
        return kode + " - " + nama + " (Harga: Rp" + harga + ")";
    }

    @Override
    public String toString() {
    return nama + " (Stok: " + stok + ")";
    }
}