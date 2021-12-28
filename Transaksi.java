import java.util.Scanner;

public class Transaksi extends Barang implements Penjualan {
    Scanner user = new Scanner(System.in);
    private int hargaBarang;
    private Integer subTotal;

    @Override
    public void NoFaktur() {
        this.NoFaktur = user.nextInt();
    }

    @Override
    public void NamaBarang() {
        this.namabarang = user.next();
    }

    @Override
    public void HargaBarang() {
        this.hargaBarang = user.nextInt();
    }

    @Override
    public void SubTotal() {
        this.subTotal = hargaBarang * jumlahbarang;

        return;
    }

    @Override
    public Double Discount() {
        if (SubTotal >30000 && SubTotal <=40000) {
            discount = 0.1;
            System.out.println("\nanda mendapatkan diskon 10%\n");
        } else if (SubTotal >40000 && SubTotal <=60000) {
            discount = 0.15;
            System.out.println("\nanda mendapatkan diskon 15%\n");
        } else if (SubTotal >60000 && SubTotal <=80000) {
            discount = 0.2;
            System.out.println("\nanda mendapatkan diskon 20%\n");
        } else if (SubTotal >80000) {
            discount = 0.25;
            System.out.println("\nanda mendapatkan diskon 25%\n");
        } else {
            System.out.println("\nanda tidak mendapatkan diskon\n");
        }
        return this.discount;
    }

    @Override
    public void TotalHarga() {
        this.totalBayar = (int) (this.subTotal - (this.subTotal * this.discount));

    }

    @Override
    public void Jumlah() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void SubTotal() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Discount() {
        // TODO Auto-generated method stub
        
    }

    
}