package rumus;

import models.Barang;
import models.Result;

import java.util.Queue;

class Utils {

    private OnMessageFifo onMessageFifo;

    void onMessageFifoListener(OnMessageFifo onMessageFifo){
        this.onMessageFifo = onMessageFifo;
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

    int hitungAktiva(Queue<Barang> queue, int laba){
        int persediaan = 0;
        for(Barang barang : queue){
            persediaan += (barang.getPrice() * barang.getQuantity());
        }
        return (persediaan + laba);
    }

    boolean onValidation(Queue<Barang> queue, Barang barang){
        int stokRencana         = hitungSisaStok(queue) - barang.getQuantity();
        boolean isValidation    = (stokRencana > 0);

        if(!isValidation) {
            if (hitungSisaStok(queue) == 0) {
                onMessageFifo.onError("ERROR : Persediaan Anda kosong");
            } else {
                onMessageFifo.onError("ERROR : Permintaan stok melebihi persediaan");
            }
        }

        return isValidation;
    }

    void onResult(Queue<Barang> queue, Result result){
        int increment = 0;
        System.out.println("\n\n");

        System.out.println("================================= LAPORAN FIFO  ================================");
        System.out.println("NO.\t=\tSTOK\t=\tHARGA");

        for(Barang barang : queue){
            increment++;
            System.out.println("" + increment + "\t\t" + barang.getQuantity() + "\t\t" + barang.getPrice());
        }

        System.out.println("SISA STOK : " + result.getSisaStok());
        System.out.println("LABA      : " + result.getLaba());

        System.out.println("\n\n");
    }

    public interface OnMessageFifo {
        void onError(String message);
    }

}
