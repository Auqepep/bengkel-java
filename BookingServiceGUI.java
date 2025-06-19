import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class BookingServiceGUI extends JFrame {
    private JTextField tfNopol, tfMerk, tfTipe;
    private JComboBox<Layanan> cbLayanan;
    private JComboBox<Barang> cbBarang;
    private JTextField tfJumlahBarang;
    private JTextArea taOutput;
    private JButton btnTambahLayanan, btnTambahBarang, btnBooking, btnLihatTransaksi;

    private Transaksi transaksi;
    private int counterTransaksi = 1;

    private ArrayList<Layanan> layananList = new ArrayList<>();
    private ArrayList<Barang> barangList = new ArrayList<>();
    private ArrayList<Transaksi> semuaTransaksi = new ArrayList<>();

    public BookingServiceGUI() {
        setTitle("Booking Service Motor");
        setSize(700, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // MOTOR PANEL
        JPanel panelMotor = new JPanel(new GridLayout(3, 2, 10, 10));
        panelMotor.setBorder(BorderFactory.createTitledBorder("Data Motor"));

        JLabel lblNopol = new JLabel("Nomor Polisi:"); lblNopol.setFont(labelFont);
        tfNopol = new JTextField(); tfNopol.setFont(fieldFont);
        panelMotor.add(lblNopol); panelMotor.add(tfNopol);

        JLabel lblMerk = new JLabel("Merk:"); lblMerk.setFont(labelFont);
        tfMerk = new JTextField(); tfMerk.setFont(fieldFont);
        panelMotor.add(lblMerk); panelMotor.add(tfMerk);

        JLabel lblTipe = new JLabel("Tipe:"); lblTipe.setFont(labelFont);
        tfTipe = new JTextField(); tfTipe.setFont(fieldFont);
        panelMotor.add(lblTipe); panelMotor.add(tfTipe);

        // LAYANAN PANEL
        JPanel panelLayanan = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelLayanan.setBorder(BorderFactory.createTitledBorder("Layanan"));

        cbLayanan = new JComboBox<>();
        cbLayanan.setPreferredSize(new Dimension(200, 25));
        btnTambahLayanan = new JButton("Tambah Layanan");
        panelLayanan.add(cbLayanan);
        panelLayanan.add(btnTambahLayanan);

        // BARANG PANEL
        JPanel panelBarang = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBarang.setBorder(BorderFactory.createTitledBorder("Barang"));

        cbBarang = new JComboBox<>();
        cbBarang.setPreferredSize(new Dimension(200, 25));
        tfJumlahBarang = new JTextField("1", 5);
        btnTambahBarang = new JButton("Tambah Barang");

        panelBarang.add(new JLabel("Barang:"));
        panelBarang.add(cbBarang);
        panelBarang.add(new JLabel("Jumlah:"));
        panelBarang.add(tfJumlahBarang);
        panelBarang.add(btnTambahBarang);

        // BUTTONS
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new FlowLayout());
        btnBooking = new JButton("Simpan Transaksi");
        btnLihatTransaksi = new JButton("Lihat Semua Transaksi");
        panelButton.add(btnBooking);
        panelButton.add(btnLihatTransaksi);

        // OUTPUT
        taOutput = new JTextArea(12, 60);
        taOutput.setEditable(false);
        taOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(taOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detail Transaksi"));

        // ASSEMBLE
        panelForm.add(panelMotor);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(panelLayanan);
        panelForm.add(panelBarang);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(panelButton);
        panelForm.add(Box.createVerticalStrut(10));

        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        inisialisasiData();
        setupListeners();
    }

    private void setupListeners() {
        btnTambahLayanan.addActionListener(e -> {
            if (transaksi != null) {
                Layanan layanan = (Layanan) cbLayanan.getSelectedItem();
                if (layanan != null) {
                    transaksi.tambahLayanan(layanan);
                    taOutput.append("✔ Layanan: " + layanan.getNama() + "\n");
                }
            }
        });

        btnTambahBarang.addActionListener(e -> {
            if (transaksi != null) {
                try {
                    Barang barang = (Barang) cbBarang.getSelectedItem();
                    int jumlah = Integer.parseInt(tfJumlahBarang.getText());
                    transaksi.tambahBarang(barang, jumlah);
                    taOutput.append("✔ Barang: " + barang.getNama() + " x" + jumlah + "\n");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
                } catch (BengkelException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        btnBooking.addActionListener(e -> buatTransaksiBaru());

        btnLihatTransaksi.addActionListener(e -> {
            taOutput.setText("=== Semua Transaksi ===\n");
            for (Transaksi t : semuaTransaksi) {
                taOutput.append(t.getRingkasan() + "\n\n");
            }
        });
    }

    private void buatTransaksiBaru() {
        String nopol = tfNopol.getText();
        String merk = tfMerk.getText();
        String tipe = tfTipe.getText();

        if (nopol.isEmpty() || merk.isEmpty() || tipe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data motor harus diisi!");
            return;
        }

        Motor motor = new Motor(nopol, merk, tipe);
        transaksi = new Transaksi("TRX-" + (counterTransaksi++), motor);
        semuaTransaksi.add(transaksi); // simpan transaksi

        taOutput.setText("=== Transaksi Baru ===\n" + transaksi.getRingkasan() + "\n");
    }

    private void inisialisasiData() {
        layananList.add(new Layanan("L001", "Ganti Oli", 75000));
        layananList.add(new Layanan("L002", "Tune Up", 50000));
        layananList.add(new Layanan("L003", "Ganti Ban", 150000));
        for (Layanan l : layananList) cbLayanan.addItem(l);

        barangList.add(new Barang("B001", "Oli Mesin", 40000, 10));
        barangList.add(new Barang("B002", "Ban Tubeless", 25000, 5));
        barangList.add(new Barang("B003", "Spark Plug", 35000, 7));
        for (Barang b : barangList) cbBarang.addItem(b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingServiceGUI().setVisible(true));
    }
}
﻿
