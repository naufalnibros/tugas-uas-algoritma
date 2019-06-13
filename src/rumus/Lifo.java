package rumus;

import models.Barang;
import models.Result;

import java.util.Stack;

public class Lifo extends Utils implements Utils.OnMessage {
    private int totalKeuntungan             = 0;
    private final Stack<Barang> stackBarang = new Stack<>();

    private int laba(){
        return totalKeuntungan;
    }

    private int sisaStok(){
        return hitungSisaStok(stackBarang);
    }

    private int aktiva(){
        return hitungAktiva(stackBarang, laba());
    }

    public void onPembelian(Barang barang){
        stackBarang.push(barang);
        onResult(stackBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));
    }

    public boolean onPenjualan(Barang barang){
        onMessageInit(this);

        if(!onValidation(stackBarang, barang, VALIDATION_GENERAL)){
            onResult(stackBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));
            return true;
        }

        transaction(barang);
        return true;
    }

    private void transaction(Barang barang){
        int persediaan;
        boolean isLooping = true;

        if(barang.getQuantity() <= stackBarang.peek().getQuantity()){
            persediaan  = hitungPersediaan(stackBarang.peek(), barang);
            totalKeuntungan += hitungKeuntungan(stackBarang.peek(), barang);

            stackBarang.peek().setQuantity(persediaan);

            if (persediaan == 0) stackBarang.pop();

        } else {
            if(!onValidation(stackBarang, barang, VALIDATION_TRANSACTION)) isLooping = false;

            while (isLooping) {
                persediaan = hitungPersediaan(stackBarang.peek(), barang);
                totalKeuntungan += hitungKeuntungan(stackBarang.peek(), barang);

                stackBarang.peek().setQuantity(persediaan);

                if (persediaan >= 0) isLooping = false;

                if (persediaan <= 0) stackBarang.pop();

                barang.setQuantity( (Math.abs(persediaan)) );
            }
        }

        onResult(stackBarang, new Result(sisaStok(), totalKeuntungan, aktiva()));

    }

    @Override
    public void onError(String message) {
        System.out.println("\n\n======================================");
        System.out.println(message);
        System.out.println("======================================\n\n");
    }
}