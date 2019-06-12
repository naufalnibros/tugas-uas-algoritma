package models;

public class Barang {
    private int quantity;
    private int price;

    public Barang(int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Barang{" + "quantity=" + quantity + ", price=" + price + '}';
    }

}
