package rumus;

import models.Barang;
import models.Result;

import java.util.LinkedList;
import java.util.Queue;

public class Fifo extends Utils implements Utils.OnMessage {

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
        onResult(queueBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));
    }

    public boolean onPenjualan(Barang barang){
        onMessageInit(this);

        if(!onValidation(queueBarang, barang, VALIDATION_GENERAL)){
            onResult(queueBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));
            return true;
        }

        transaction(barang);
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
            if(!onValidation(queueBarang, barang, VALIDATION_TRANSACTION)) isLooping = false;

            while (isLooping) {
                persediaan = hitungPersediaan(queueBarang.element(), barang);
                totalKeuntungan += hitungKeuntungan(queueBarang.element(), barang);

                queueBarang.element().setQuantity(persediaan);

                if (persediaan >= 0) isLooping = false;

                if (persediaan <= 0) queueBarang.remove();

                barang.setQuantity( (Math.abs(persediaan)) );
            }
        }

        onResult(queueBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));

    }

    @Override
    public void onError(String message) {
        System.out.println("\n\n====================================");
        System.out.println(message);
        System.out.println("====================================\n\n");
    }

}
