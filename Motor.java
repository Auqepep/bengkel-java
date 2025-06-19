public class Motor extends Kendaraan {
    private String tipe;

    public Motor(String nomorPolisi, String merk, String tipe) {
        super(nomorPolisi, merk);
        this.tipe = tipe;
    }

    public String getTipe() { 
        return tipe; 
    }
}