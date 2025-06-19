import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BookingServiceGUI extends JFrame {
    private JTextField tfNopol, tfMerk, tfTipe;
    private JComboBox<Layanan> cbLayanan;
    private JComboBox<Barang> cbBarang;
    private JTextField tfJumlahBarang;
    private JTextArea taOutput;
    private JButton btnTambahLayanan, btnTambahBarang, btnBooking;

    private Transaksi transaksi;
    private int counterTransaksi = 1;

    // Dummy Data
    private ArrayList<Layanan> layananList = new ArrayList<>();
    private ArrayList<Barang> barangList = new ArrayList<>();

    public BookingServiceGUI() {
        setTitle("Booking Service Motor");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2));

        // Input motor
        add(new JLabel("Nomor Polisi:"));
        tfNopol = new JTextField();
        add(tfNopol);

        add(new JLabel("Merk:"));
        tfMerk = new JTextField();
        add(tfMerk);

        add(new JLabel("Tipe:"));
        tfTipe = new JTextField();
        add(tfTipe);

        // Layanan
        add(new JLabel("Pilih Layanan:"));
        cbLayanan = new JComboBox<>();
        add(cbLayanan);

        btnTambahLayanan = new JButton("Tambah Layanan");
        add(btnTambahLayanan);
        add(new JLabel()); // spacer

        // Barang
        add(new JLabel("Pilih Barang:"));
        cbBarang = new JComboBox<>();
        add(cbBarang);

        add(new JLabel("Jumlah Barang:"));
        tfJumlahBarang = new JTextField("1");
        add(tfJumlahBarang);

        btnTambahBarang = new JButton("Tambah Barang");
        add(btnTambahBarang);
        add(new JLabel()); // spacer

        // Booking
        btnBooking = new JButton("Simpan Transaksi");
        add(btnBooking);

        taOutput = new JTextArea(8, 30);
        taOutput.setEditable(false);
        add(new JScrollPane(taOutput));

        // Dummy data init
        inisialisasiData();

        // Action Listeners
        btnTambahLayanan.addActionListener(e -> {
            if (transaksi != null) {
                Layanan layanan = (Layanan) cbLayanan.getSelectedItem();
                transaksi.tambahLayanan(layanan);
                taOutput.append("Layanan ditambahkan: " + layanan.getInfo() + "\n");
            }
        });

        btnTambahBarang.addActionListener(e -> {
            if (transaksi != null) {
                try {
                    Barang barang = (Barang) cbBarang.getSelectedItem();
                    int jumlah = Integer.parseInt(tfJumlahBarang.getText());
                    transaksi.tambahBarang(barang, jumlah);
                    taOutput.append("Barang ditambahkan: " + barang.getInfo() + " x" + jumlah + "\n");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
                } catch (BengkelException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        btnBooking.addActionListener(e -> buatTransaksiBaru());
    }

    private void buatTransaksiBaru() {
        String nopol = tfNopol.getText();
        String merk = tfMerk.getText();
        String tipe = tfTipe.getText();

        if (nopol.isEmpty() || merk.isEmpty() || tipe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field motor harus diisi!");
            return;
        }

        Motor motor = new Motor(nopol, merk, tipe);
        transaksi = new Transaksi("TRX-" + (counterTransaksi++), motor);

        taOutput.setText("Transaksi baru dibuat untuk motor: " + nopol + "\n");
    }

    private void inisialisasiData() {
        // Data layanan
        layananList.add(new Layanan("L001", "Ganti Oli", 75000));
        layananList.add(new Layanan("L002", "Tune Up", 50000));
        layananList.add(new Layanan("L003", "Ganti Ban", 150000));
        for (Layanan l : layananList) cbLayanan.addItem(l);

        // Data barang
        barangList.add(new Barang("B001", "Oli Mesin", 40000, 10));
        barangList.add(new Barang("B002", "Ban Tubeless", 25000, 5));
        barangList.add(new Barang("B003", "Spark Plug", 35000, 7));
        for (Barang b : barangList) cbBarang.addItem(b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingServiceGUI().setVisible(true));
    }
}
