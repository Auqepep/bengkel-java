import java.util.ArrayList;

public class Transaksi {
    private String idTransaksi;
    private Motor objMotor;
    private ArrayList<Barang> listBarang;
    private ArrayList<Layanan> listLayanan;

    public Transaksi(String idTransaksi, Motor objMotor) {
        this.idTransaksi = idTransaksi;
        this.objMotor = objMotor;
        this.listBarang = new ArrayList<>();
        this.listLayanan = new ArrayList<>();
    }

    public void tambahBarang(Barang barang, int jumlah) throws BengkelException {
        if (barang.getStok() < jumlah) {
            throw new BengkelException("Stok barang tidak mencukupi: " + barang.getNama());
        }
        barang.kurangiStok(jumlah);
        listBarang.add(barang);
    }

    public void tambahLayanan(Layanan layanan) {
        listLayanan.add(layanan);
    }

    public void cetakTransaksi() {
        System.out.println("========== TRANSAKSI ==========");
        System.out.println("ID: " + idTransaksi);
        System.out.println("Motor: " + objMotor.getNomorPolisi() + " - " + objMotor.getMerk() + " (" + objMotor.getTipe() + ")");
        
        System.out.println("\n>> Layanan:");
        for (Layanan l : listLayanan) {
            System.out.println("• " + l.getInfo());
        }

        System.out.println("\n>> Barang:");
        for (Barang b : listBarang) {
            System.out.println("• " + b.getInfo());
        }
    }
}
