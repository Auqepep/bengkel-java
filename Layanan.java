public class Layanan implements Entitas {
    private String kode;
    private String nama;
    private double biaya;

    public Layanan(String kode, String nama, double biaya) {
        this.kode = kode;
        this.nama = nama;
        this.biaya = biaya;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getBiaya() { return biaya; }

    @Override
    public String getInfo() {
        return kode + " - " + nama + " (Biaya: Rp" + biaya + ")";
    }

    @Override
    public String toString() {
        return nama + " - Rp" + biaya; 
    }
}
