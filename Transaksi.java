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
        listBarang.add(barang); // Untuk kesederhanaan, tidak menyimpan jumlah per barang
    }

    public void tambahLayanan(Layanan layanan) {
        listLayanan.add(layanan);
    }

    public void cetakTransaksi() {
        System.out.println(getRingkasan());
    }

    public String getRingkasan() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== TRANSAKSI ==========\n");
        sb.append("ID: ").append(idTransaksi).append("\n");
        sb.append("Motor: ").append(objMotor.getNomorPolisi())
          .append(" - ").append(objMotor.getMerk())
          .append(" (").append(objMotor.getTipe()).append(")\n");

        sb.append("\n>> Layanan:\n");
        if (listLayanan.isEmpty()) {
            sb.append("Tidak ada layanan.\n");
        } else {
            for (Layanan l : listLayanan) {
                sb.append("• ").append(l.getInfo()).append("\n");
            }
        }

        sb.append("\n>> Barang:\n");
        if (listBarang.isEmpty()) {
            sb.append("Tidak ada barang.\n");
        } else {
            for (Barang b : listBarang) {
                sb.append("• ").append(b.getInfo()).append("\n");
            }
        }

        return sb.toString();
    }
}
