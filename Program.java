import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program {

    static Connection conn;

    public static void main(String[] args) throws Exception {
        Date tanggal = new Date();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MMMM-Y | HH:mm:ss");
        String Tanggal = formatTanggal.format(tanggal);
        System.out.println("Tanggal : " + Tanggal.toUpperCase());

        try (Scanner Input = new Scanner(System.in)) {
            String pilihan;
            Boolean Lanjut = true;

            String url = "jdbc:mysql://localhost:3306/tugasp14";
            String user = "root";
            String password = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Class Driver ditemukan");

                while (Lanjut) {
                    System.out.println("===========================");
                    System.out.println("     Data Transaksi    ");
                    System.out.println("===========================");
                    System.out.println("1. Lihat Data Transaksi");
                    System.out.println("2. Tambah Data Transaksi");
                    System.out.println("3. Ubah Data Transaksi");
                    System.out.println("4. Hapus Data Transaksi");
                    System.out.println("5. Cari Data Transaksi");
                    System.out.print("\n Masukkan pilihan anda : ");
                    pilihan = Input.next();

                    switch (pilihan) {
                        case "1":
                            lihatdata();
                            break;

                        case "2":
                            tambahdata();
                            break;

                        case "3":
                            ubahdata();
                            break;

                        case "4":
                            hapusdata();
                            break;

                        case "5":
                            caridata();
                            break;

                        default:
                            System.out.println("\n Data anda tidak ditemukan \n silahkan masukkan (1-5)");
                    }

                    System.out.print("\nApakah anda ingin melanjutkan (y/n)");
                    pilihan = Input.next();
                    Lanjut = pilihan.equalsIgnoreCase("y");
                }
                System.out.println("\n Program selesai ... \n Terima kasih telah menggunakan program ini :)");
            } catch (SQLException ex) {
                System.err.println(ex);
                System.exit(0);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    private static void lihatdata() throws SQLException {
        System.out.println(" Daftar Data Transaksi ");

        String sql = "SELECT * FROM faktur ";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            System.out.print("\nNo Faktur : ");
            System.out.println(result.getInt("nofaktur"));
            System.out.print("Nama Barang : ");
            System.out.println(result.getString("nama_barang"));
            System.out.print("Harga Barang : ");
            System.out.println(result.getInt("harga_barang"));
            System.out.print("Jumlah Barang : ");
            System.out.println(result.getInt("jumlahbarang"));
            System.out.print("Total Bayar : ");
            System.out.println(result.getInt("totalbayar"));
        }
    }

    private static void tambahdata() throws SQLException {
        System.out.println(" Tambah Data Transaksi ");

        try (Scanner user = new Scanner(System.in)) {
            Transaksi T1 = new Transaksi();

            System.out.print("No Faktur : ");
            T1.NoFaktur();
            System.out.print("Nama Barang : ");
            T1.NamaBarang();
            try {
                System.out.print("Harga Barang : ");
                T1.HargaBarang();
                System.out.print("Jumlah Barang : ");
                T1.jumlahbarang = user.nextInt();

                System.out.println("Subtotal :  " + T1.SubTotal());
                T1.Discount();
                T1.TotalHarga();
                System.out.println("Total Bayar : " + T1.totalBayar);
                System.out.println("------------------------------------------------");

                String sql = "INSERT INTO faktur (nofaktur, nama_barang, harga_barang, jumlahbarang, totalbayar) VALUES ('"
                        + T1.NoFaktur + "','" + T1.namabarang + "', '" + T1.hargabarang + "','"
                        + T1.jumlahbarang + "' , '" + T1.totalBayar + "')";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sql);
                System.out.println("\nProses Input berhasil");

            } catch (SQLException e) {
                System.err.println(e);
            } catch (Exception InputMismatchException) {
                System.err.println("\nInputan yang anda masukkan salah\n");
            }
        }
    }

    private static void ubahdata() throws SQLException {
        System.out.println(" Ubah Data Transaksi \n");
        try (Scanner user = new Scanner(System.in)) {
            try{
                lihatdata();
                System.out.print("Masukkan No faktur yang ingin di update : ");
                Integer nofaktur = user.nextInt();

                String sql = "SELECT * FROM faktur WHERE nofaktur = " + nofaktur;

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                if (result.next()) {
                    System.out.print("Nama Barang [" + result.getString("nama_barang") + "] :");
                    String nama = user.next();
                    System.out.print("Harga Barang [" + result.getInt("harga_barang") + "] :");
                    Integer harga = user.nextInt();
                    System.out.print("Jumlah Barang [" + result.getInt("jumlahbarang") + "] :");
                    Integer jumlah = user.nextInt();

                    Integer subtotal = harga * jumlah;
                    Double discount = 0.0;
                    if (subtotal >30000 && subtotal <=40000) {
                        discount = 0.1;
                        System.out.println("\ndiskon 10%\n");
                    } else if (subtotal >40000 && subtotal <=60000) {
                        discount = 0.15;
                        System.out.println("\ndiskon 15%\n");
                    } else if (subtotal >60000 && subtotal <=80000) {
                        discount = 0.2;
                        System.out.println("\ndiskon 20%\n");
                    } else if (subtotal >80000) {
                        discount = 0.25;
                        System.out.println("\ndiskon 25%\n");
                    } else {
                        System.out.println("\ntidak dapat diskon\n");
                    }
                    Integer total = (int) (subtotal - (subtotal * discount));

                    System.out.print("Total Bayar [" + result.getInt("totalbayar") + "] :" +total);
                    

                    sql = "UPDATE faktur SET nama_barang = '" + nama + "',harga_barang = '" + harga + "',jumlahbarang = '"
                            + jumlah + "',totalbayar = '" + total + "' WHERE nofaktur ='" + nofaktur + "'";
                    
                    if(statement.executeUpdate(sql)>0){
                        System.out.println("\nBerhasil memperbaharui data transaksi");
                    }
                }
                statement.close();
            }catch(SQLException e){
                System.err.println("Terjadi eror dalam mengedit");
                System.err.println(e.getMessage());
            }
        }
    }

    private static void hapusdata() throws SQLException {
        System.out.println(" Hapus Data Transaksi \n");
        try (Scanner user = new Scanner(System.in)) {
            try{
                lihatdata();
                System.out.print("Masukkan nofaktur yang ingin dihapus : ");
                Integer nofaktur = user.nextInt();

                String sql = "DELETE FROM faktur WHERE nofaktur = "+nofaktur;
                Statement statement = conn.createStatement();

                if(statement.executeUpdate(sql)>0){
                    System.out.println("Data no faktur ("+ nofaktur + ") berhasil diihapus");
                }
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }

    private static void caridata() throws SQLException {
    System.out.println(" Cari Data Transaksi \n");

    try (Scanner user = new Scanner(System.in)) {
        System.out.print("Masukkan nama barang yang dicari : ");
        String cari = user.next();
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM faktur WHERE nama_barang LIKE '%"+cari+"%'";
        ResultSet result = statement.executeQuery(sql);

        while(result.next()){
            System.out.print("\nNo Faktur\t: ");
            System.out.print(result.getInt("nofaktur"));
            System.out.print("\nNama Barang\t: ");
            System.out.print(result.getString("nama_barang"));
            System.out.print("\nHarga Barang\t: ");
            System.out.print(result.getInt("harga_barang"));
            System.out.print("\nJumlah Barang\t: ");
            System.out.print(result.getInt("jumlahbarang"));
            System.out.print("\nTotal Bayar\t: ");
            System.out.print(result.getInt("totalbayar"));
            }
    }
    }
}
