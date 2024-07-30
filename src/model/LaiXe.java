package model;

public class LaiXe {
    private static int cnt = 10000;
    private int maLX;
    private String hoTen, dchi, sdt, trinhDo;

    public LaiXe() {
    }

    public LaiXe(int maLX, String hoTen, String dchi, String sdt, String trinhDo) {
        this.maLX = maLX;
        this.hoTen = hoTen;
        this.dchi = dchi;
        this.sdt = sdt;
        this.trinhDo = trinhDo;
    }

    public LaiXe(String hoTen, String dchi, String sdt, String trinhDo) {
        this.maLX = cnt++;
        this.hoTen = hoTen;
        this.dchi = dchi;
        this.sdt = sdt;
        this.trinhDo = trinhDo;
    }

    public static int getCnt() {
        return cnt;
    }

    public static void setCnt(int cnt) {
        LaiXe.cnt = cnt;
    }

    public int getMaLX() {
        return maLX;
    }

    public void setMaLX(int maLX) {
        this.maLX = maLX;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDchi() {
        return dchi;
    }

    public void setDchi(String dchi) {
        this.dchi = dchi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }

    @Override
    public String toString() {
        return maLX + "\t" + hoTen + "\t" + dchi + "\t" + sdt + "\t" + trinhDo;
    }
}
