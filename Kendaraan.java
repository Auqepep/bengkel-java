public class Kendaraan {
    protected String nomorPolisi;
    protected String merk;

    public Kendaraan(String nomorPolisi, String merk) {
        this.nomorPolisi = nomorPolisi;
        this.merk = merk;
    }

    public String getNomorPolisi() { return nomorPolisi; }
    public String getMerk() { return merk; }
}
