import models.Barang;
import rumus.Fifo;

import java.util.Scanner;

public class MainActivity {
    private static final int TYPE_PEMBELIAN = 1038;
    private static final int TYPE_PENJUALAN = 1083;
    private static final int USE_FIFO = 2004;
    private static final int USE_LIFO = 4002;
    private static int CONTEXT_USE;

    private static Fifo fifo;

    static Scanner input = new Scanner(System.in);
    static boolean isLooping = true;
    static boolean isProses;

    static void showMenu(){
        System.out.println("\n========= MENU ========");
        System.out.println("[1] FIFO");
        System.out.println("[2] LIFO");
        System.out.println("[3] Exit");
        System.out.print("PILIH MENU> ");

        int selectedMenu = input.nextInt();

        switch(selectedMenu){
            case 1:
                usingFifo();
                break;
            case 2:
                usingLifo();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan salah!");

        }
    }

    static String checkType(int type){
        switch(type){
            case TYPE_PEMBELIAN:
                return "PEMBELIAN";
            case TYPE_PENJUALAN:
                return "PENJUALAN";
            default:
                return "";
        }
    }

    static String checkUse(){
        switch(CONTEXT_USE){
            case USE_FIFO:
                return "FIFO";
            case USE_LIFO:
                return "LIFO";
            default:
                return "";
        }
    }

    static void insertBarang(int type){
        Scanner inputBarang = new Scanner(System.in);

        System.out.println("\n"
                + "========= ("+ checkType(type) +") INPUT DATA BARANG ========="
        );

        System.out.print("Quantity> ");
        int quantity = inputBarang.nextInt();

        System.out.print("Harga> ");
        int price    = inputBarang.nextInt();

        Barang barang = new Barang(quantity, price);

        switch(type){
            case TYPE_PEMBELIAN:
                if(CONTEXT_USE == USE_FIFO) fifo.onPembelian(barang);
                break;
            case TYPE_PENJUALAN:
                if(CONTEXT_USE == USE_FIFO) fifo.onPenjualan(barang);
                break;
        }

    }

    static void chooseTransaksi(){

        System.out.println("\n"
                + "========= ("+ checkUse() +") PILIH TRANSAKSI ========="
        );

        System.out.println("[1] PEMBELIAN");
        System.out.println("[2] PENJUALAN");
        System.out.println("[3] Exit");
        System.out.print("PILIH TRANSAKSI> ");

        int selectedTransaksi = input.nextInt();

        switch(selectedTransaksi){
            case 1:
                insertBarang(TYPE_PEMBELIAN);
                break;
            case 2:
                insertBarang(TYPE_PENJUALAN);
                break;
            case 3:
                isProses = false;
                break;
        }
    }

    static void usingFifo(){
        isProses = true;
        CONTEXT_USE = USE_FIFO;
        fifo = new Fifo();

        do {
            chooseTransaksi();
        } while (isProses);
    }

    static void usingLifo() {

    }

    public static void main(String[] args) {
        do {
            showMenu();
        } while (isLooping);
    }
}

