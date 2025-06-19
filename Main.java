import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Motor> listMotor = new ArrayList<>();
    private static ArrayList<Barang> listBarang = new ArrayList<>();
    private static ArrayList<Layanan> listLayanan = new ArrayList<>();
    private static ArrayList<Transaksi> listTransaksi = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize sample data
        initializeData();
        
        System.out.println("========== SISTEM BENGKEL MOTOR ==========");
        
        while (true) {
            showMenu();
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (pilihan) {
                case 1:
                    daftarMotor();
                    break;
                case 2:
                    lihatBarang();
                    break;
                case 3:
                    lihatLayanan();
                    break;
                case 4:
                    buatTransaksi();
                    break;
                case 5:
                    lihatTransaksi();
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n========== MENU UTAMA ==========");
        System.out.println("1. Daftar Motor Baru");
        System.out.println("2. Lihat Barang");
        System.out.println("3. Lihat Layanan");
        System.out.println("4. Buat Transaksi");
        System.out.println("5. Lihat Transaksi");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void initializeData() {
        // Sample barang
        listBarang.add(new Barang("B001", "Oli Mesin", 50000, 10));
        listBarang.add(new Barang("B002", "Ban Tubeless", 150000, 5));
        listBarang.add(new Barang("B003", "Spark Plug", 25000, 8));
        
        // Sample layanan
        listLayanan.add(new Layanan("L001", "Ganti Oli", 15000));
        listLayanan.add(new Layanan("L002", "Tune Up", 50000));
        listLayanan.add(new Layanan("L003", "Ganti Ban", 20000));
    }

    private static void daftarMotor() {
        System.out.println("\n========== DAFTAR MOTOR BARU ==========");
        System.out.print("Nomor Polisi: ");
        String nomorPolisi = scanner.nextLine();
        System.out.print("Merk: ");
        String merk = scanner.nextLine();
        System.out.print("Tipe: ");
        String tipe = scanner.nextLine();
        
        Motor motor = new Motor(nomorPolisi, merk, tipe);
        listMotor.add(motor);
        System.out.println("Motor berhasil didaftarkan!");
    }

    private static void lihatBarang() {
        System.out.println("\n========== DAFTAR BARANG ==========");
        if (listBarang.isEmpty()) {
            System.out.println("Tidak ada barang tersedia.");
            return;
        }
        
        for (int i = 0; i < listBarang.size(); i++) {
            Barang b = listBarang.get(i);
            System.out.println((i + 1) + ". " + b.getInfo() + " - Stok: " + b.getStok());
        }
    }

    private static void lihatLayanan() {
        System.out.println("\n========== DAFTAR LAYANAN ==========");
        if (listLayanan.isEmpty()) {
            System.out.println("Tidak ada layanan tersedia.");
            return;
        }
        
        for (int i = 0; i < listLayanan.size(); i++) {
            System.out.println((i + 1) + ". " + listLayanan.get(i).getInfo());
        }
    }

    private static void buatTransaksi() {
        if (listMotor.isEmpty()) {
            System.out.println("Tidak ada motor terdaftar. Daftar motor terlebih dahulu!");
            return;
        }

        System.out.println("\n========== BUAT TRANSAKSI ==========");
        
        // Pilih motor
        System.out.println("Pilih Motor:");
        for (int i = 0; i < listMotor.size(); i++) {
            Motor m = listMotor.get(i);
            System.out.println((i + 1) + ". " + m.getNomorPolisi() + " - " + m.getMerk() + " (" + m.getTipe() + ")");
        }
        
        System.out.print("Pilih motor (nomor): ");
        int motorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (motorIndex < 0 || motorIndex >= listMotor.size()) {
            System.out.println("Motor tidak valid!");
            return;
        }

        System.out.print("ID Transaksi: ");
        String idTransaksi = scanner.nextLine();
        
        Transaksi transaksi = new Transaksi(idTransaksi, listMotor.get(motorIndex));
        
        // Tambah layanan
        System.out.println("\nTambah Layanan:");
        while (true) {
            lihatLayanan();
            System.out.print("Pilih layanan (nomor, 0 untuk selesai): ");
            int layananIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
            
            if (layananIndex == -1) break;
            
            if (layananIndex >= 0 && layananIndex < listLayanan.size()) {
                transaksi.tambahLayanan(listLayanan.get(layananIndex));
                System.out.println("Layanan ditambahkan!");
            } else {
                System.out.println("Layanan tidak valid!");
            }
        }
        
        // Tambah barang
        System.out.println("\nTambah Barang:");
        while (true) {
            lihatBarang();
            System.out.print("Pilih barang (nomor, 0 untuk selesai): ");
            int barangIndex = scanner.nextInt() - 1;
            
            if (barangIndex == -1) break;
            
            if (barangIndex >= 0 && barangIndex < listBarang.size()) {
                System.out.print("Jumlah: ");
                int jumlah = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                try {
                    transaksi.tambahBarang(listBarang.get(barangIndex), jumlah);
                    System.out.println("Barang ditambahkan!");
                } catch (BengkelException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Barang tidak valid!");
            }
        }
        
        listTransaksi.add(transaksi);
        System.out.println("\nTransaksi berhasil dibuat!");
        transaksi.cetakTransaksi();
    }

    private static void lihatTransaksi() {
        System.out.println("\n========== DAFTAR TRANSAKSI ==========");
        if (listTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        
        for (int i = 0; i < listTransaksi.size(); i++) {
            System.out.println("\n" + (i + 1) + ".");
            listTransaksi.get(i).cetakTransaksi();
        }
    }
}