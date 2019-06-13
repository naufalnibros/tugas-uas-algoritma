package rumus;

import models.Barang;
import models.Result;

import java.util.Queue;
import java.util.Stack;

class Utils {

    private OnMessage onMessage;
    int VALIDATION_TRANSACTION = 12314;
    int VALIDATION_GENERAL     = 41321;

    void onMessageInit(OnMessage onMessage){
        this.onMessage = onMessage;
    }

    int hitungKeuntungan(Barang barangPersediaan, Barang barangPenjualan){
        int QtyPenjualan    = barangPenjualan.getQuantity();
        int PricePenjualan  = barangPenjualan.getPrice();
        int PricePersediaan = barangPersediaan.getPrice();

        if (barangPersediaan.getQuantity() < barangPenjualan.getQuantity()) {
            QtyPenjualan = barangPersediaan.getQuantity();
        }

        return ( QtyPenjualan * PricePenjualan ) - ( QtyPenjualan * PricePersediaan );
    }

    int hitungPersediaan(Barang barangPersediaan, Barang barangPenjualan){
        return (barangPersediaan.getQuantity() - barangPenjualan.getQuantity());
    }

    int hitungSisaStok(Queue<Barang> queue){
        int value = 0;
        for(Barang barang : queue ){
            value += barang.getQuantity();
        }
        return value;
    }

    int hitungSisaStok(Stack<Barang> stack){
        int value = 0;
        for(Barang barang : stack ){
            value += barang.getQuantity();
        }
        return value;
    }

    int hitungAktiva(Queue<Barang> queue, int laba){
        int persediaan = 0;
        for(Barang barang : queue){
            persediaan += (barang.getPrice() * barang.getQuantity());
        }
        return (persediaan + laba);
    }

    int hitungAktiva(Stack<Barang> stack, int laba){
        int persediaan = 0;
        for(Barang barang : stack){
            persediaan += (barang.getPrice() * barang.getQuantity());
        }
        return (persediaan + laba);
    }

    boolean onValidation(Queue<Barang> queue, Barang barang, int typeValidation){
        int stokRencana         = hitungSisaStok(queue) - barang.getQuantity();
        boolean isValidation    = true;

        if (hitungSisaStok(queue) == 0) {
            isValidation = false;
            onMessage.onError("ERROR : Persediaan Anda kosong");
        }

        if (!(stokRencana >= 0)){
            isValidation = false;
            onMessage.onError("ERROR : Permintaan stok melebihi persediaan \nStok saat ini : " + hitungSisaStok(queue));
        }

        if (stokRencana == 0 && typeValidation == VALIDATION_TRANSACTION){
            isValidation = true;
            onMessage.onError("MESSAGE : Persediaan Anda Habis");
        }

        return isValidation;
    }

    boolean onValidation(Stack<Barang> stack, Barang barang, int typeValidation){
        int stokRencana         = hitungSisaStok(stack) - barang.getQuantity();
        boolean isValidation    = true;

        if (hitungSisaStok(stack) == 0) {
            isValidation = false;
            onMessage.onError("ERROR : Persediaan Anda kosong");
        }

        if (!(stokRencana >= 0)){
            isValidation = false;
            onMessage.onError("ERROR : Permintaan stok melebihi persediaan \nStok saat ini : " + hitungSisaStok(stack));
        }

        if (stokRencana == 0 && typeValidation == VALIDATION_TRANSACTION){
            isValidation = true;
            onMessage.onError("MESSAGE : Persediaan Anda Habis");
        }

        return isValidation;
    }

    void onResult(Queue<Barang> queue, Result result){
        int increment = 0;
        System.out.println("\n");

        System.out.println("============== LAPORAN FIFO  ==============");
        System.out.println("NO.\t\tSTOK\t\tHARGA");

        for(Barang barang : queue){
            increment++;
            System.out.println("" + increment + "\t\t" + barang.getQuantity() + "\t\t\t" + barang.getPrice());
        }

        System.out.println("SISA STOK : " + result.getSisaStok());
        System.out.println("LABA      : " + result.getLaba());
        System.out.println("AKTIVA    : " + result.getAktiva());

        System.out.println("\n");
    }

    void onResult(Stack<Barang> stack, Result result){
        int increment = 0;
        System.out.println("\n");

        System.out.println("============== LAPORAN LIFO ==============");
        System.out.println("NO.\t\tSTOK\t\tHARGA");

        for(Barang barang : stack){
            increment++;
            System.out.println("" + increment + "\t\t" + barang.getQuantity() + "\t\t\t" + barang.getPrice());
        }

        System.out.println("SISA STOK : " + result.getSisaStok());
        System.out.println("LABA      : " + result.getLaba());
        System.out.println("AKTIVA    : " + result.getAktiva());

        System.out.println("\n");
    }

    public interface OnMessage {
        void onError(String message);
    }

}
