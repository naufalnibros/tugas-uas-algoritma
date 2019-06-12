package models;

public class Result {
    private int sisaStok;
    private int laba;

    public Result(int sisaStok, int laba) {
        this.sisaStok = sisaStok;
        this.laba = laba;
    }

    public int getSisaStok() {
        return sisaStok;
    }

    public void setSisaStok(int sisaStok) {
        this.sisaStok = sisaStok;
    }

    public int getLaba() {
        return laba;
    }

    public void setLaba(int laba) {
        this.laba = laba;
    }



}
