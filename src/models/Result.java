package models;

public class Result {
    private int sisaStok;
    private int laba;
    private int aktiva;

    public Result(int sisaStok, int laba, int aktiva) {
        this.sisaStok = sisaStok;
        this.laba = laba;
        this.aktiva = aktiva;
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

    public int getAktiva() {
        return aktiva;
    }

    public void setAktiva(int aktiva) {
        this.aktiva = aktiva;
    }
}
