package model;

public class BangPhanCong {
    private LaiXe laiXe;
    private Tuyen tuyen;
    private int luot;

    public BangPhanCong(int maLX, int maTuyen, int luot) {
    }

    public BangPhanCong(LaiXe laiXe, Tuyen tuyen, int luot) {
        this.laiXe = laiXe;
        this.tuyen = tuyen;
        this.luot = luot;
    }

    public LaiXe getLaiXe() {
        return laiXe;
    }

    public void setLaiXe(LaiXe laiXe) {
        this.laiXe = laiXe;
    }

    public Tuyen getTuyen() {
        return tuyen;
    }

    public void setTuyen(Tuyen tuyen) {
        this.tuyen = tuyen;
    }

    public int getLuot() {
        return luot;
    }

    public void setLuot(int luot) {
        this.luot = luot;
    }
}
