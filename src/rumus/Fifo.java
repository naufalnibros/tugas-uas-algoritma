package rumus;

import models.Barang;
import models.Result;

import java.util.LinkedList;
import java.util.Queue;

public class Fifo extends Utils implements Utils.OnMessageFifo{

    private int totalKeuntungan                   = 0;
    private final Queue<Barang> queueBarang       = new LinkedList<>();

    private int laba(){
        return totalKeuntungan;
    }

    private int sisaStok(){
        return hitungSisaStok(queueBarang);
    }

    private int aktiva(){
        return hitungAktiva(queueBarang, laba());
    }

    public void onPembelian(Barang barang){
        queueBarang.add(barang);
        onResult(queueBarang, new Result(sisaStok(), totalKeuntungan));
    }

    public boolean onPenjualan(Barang barang){
        onMessageFifoListener(this);
        if(!onValidation(queueBarang, barang)) return true;

        transaction(barang);
        onResult(queueBarang, new Result(sisaStok(), totalKeuntungan));
        return true;
    }

    private void transaction(Barang barang){
        int persediaan;
        boolean isLooping = true;

        if(barang.getQuantity() <= queueBarang.element().getQuantity()){
            persediaan  = hitungPersediaan(queueBarang.element(), barang);
            totalKeuntungan += hitungKeuntungan(queueBarang.element(), barang);

            queueBarang.element().setQuantity(persediaan);

            if (persediaan == 0) queueBarang.remove();

        } else {
            if(!onValidation(queueBarang, barang)) isLooping = false;

            while (isLooping) {
                persediaan = hitungPersediaan(queueBarang.element(), barang);
                totalKeuntungan += hitungKeuntungan(queueBarang.element(), barang);

                queueBarang.element().setQuantity(persediaan);

                if (persediaan >= 0) isLooping = false;

                if (persediaan <= 0) queueBarang.remove();

                barang.setQuantity( (barang.getQuantity() + persediaan) );

            }
        }
    }

    @Override
    public void onError(String message) {
        System.out.println("\n\n====================================");
        System.out.println(message);
        System.out.println("====================================\n\n");
    }

}
