import dao.BangPhanCongDAO;
import dao.LaiXeDAO;
import dao.TuyenDAO;
import model.BangPhanCong;
import model.LaiXe;
import model.Tuyen;

import javax.management.MBeanNotificationInfo;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<LaiXe> dsLaiXe = new ArrayList<>();
    private static ArrayList<Tuyen> dsTuyen = new ArrayList<>();
    private static ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
    private static ArrayList<BangPhanCong> dsPhanCongByMaLX = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("Menu");
            System.out.println("1. Nhập lái xe mới.");
            System.out.println("2. Nhập tuyến xe mới.");
            System.out.println("3. Nhập phân công mới.");
            System.out.println("4. Sắp xếp danh sách phân công theo họ tên lái xe.");
            System.out.println("5. Sắp xếp danh sách phân công theo số lượng tuyến đảm nhận trong ngày (giảm dần)");
            System.out.println("6. Lập bảng thống kê tổng khoảng cách chạy xe trong ngày.");
            System.out.println("0. Thoát.");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    nhapLaiXe();
                    inDsLaiXe();
                    break;
                case 2:
                    nhapTuyenXe();
                    inDsTuyenXe();
                    break;
                case 3:
                    nhapPhanCong();
                    inDsPhanCong();
                    break;
                case 4:
                    sapXepTheoTenLaiXe();
                    break;
                case 5:
                    sapXepTheoSoLuongTuyen();
                    break;
                case 6:
                    lapThongKe();
                    break;
                case 0:
                    System.out.println("Kết thúc!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    private static void nhapLaiXe() {
        int current = LaiXeDAO.getInstance().getSoLuong();
        LaiXe.setCnt(LaiXe.getCnt() + current);
        System.out.print("Nhập số lượng lái xe: ");
        int slLaiXe = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < slLaiXe; i++) {
            System.out.println("Nhập thông tin lái xe thứ " + (i + 1));
            System.out.print("Họ tên: ");
            String hoTen = sc.nextLine();
            System.out.print("Địa chỉ: ");
            String dchi = sc.nextLine();
            System.out.print("Số điện thoại: ");
            String sdt = sc.nextLine();
            System.out.print("Trình độ: ");
            String trinhDo = sc.nextLine();
            LaiXeDAO.getInstance().insert(new LaiXe(hoTen, dchi, sdt, trinhDo));
        }
    }

    private static void inDsLaiXe() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        for (LaiXe laiXe : dsLaiXe) System.out.println(laiXe);
    }

    private static void nhapTuyenXe() {
        int current = TuyenDAO.getInstance().getSoLuong();
        Tuyen.setCnt(Tuyen.getCnt() + current);
        System.out.print("Nhập số lượng tuyến xe: ");
        int slTuyen = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < slTuyen; i++) {
            System.out.println("Nhập thông tin tuyến xe thứ " + (i + 1));
            System.out.print("Khoảng cách: ");
            int khoangCach = Integer.parseInt(sc.nextLine());
            System.out.print("Số điểm dừng: ");
            int soDiemDung = Integer.parseInt(sc.nextLine());
            TuyenDAO.getInstance().insert(new Tuyen(khoangCach, soDiemDung));
        }
    }

    private static void inDsTuyenXe() {
        dsTuyen = TuyenDAO.getInstance().selectAll();
        for (Tuyen tuyen : dsTuyen) System.out.println(tuyen);
    }

    private static void nhapPhanCong() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();
        System.out.print("Nhập mã lái xe: ");
        int maLaiXe = Integer.parseInt(sc.nextLine());
        LaiXe laiXePC = null;
        for (LaiXe laiXe : dsLaiXe) {
            if (laiXe.getMaLX() == maLaiXe) {
                laiXePC = laiXe;
                break;
            }
        }
        if (laiXePC == null) {
            System.out.println("Không tìm thấy lái xe!");
            return;
        }
        System.out.print("Nhập số lượng tuyến xe muốn phân công: ");
        int slPC = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < slPC; i++) {
            System.out.print("Nhập mã tuyến xe thứ " + (i + 1) + ": ");
            int maTuyen = Integer.parseInt(sc.nextLine());
            Tuyen tuyenPC = null;
            for (Tuyen tuyen : dsTuyen) {
                if (tuyen.getMaTuyen() == maTuyen) {
                    tuyenPC = tuyen;
                    break;
                }
            }
            if (tuyenPC == null) {
                System.out.println("Không tìm thấy tuyến xe!");
                continue;
            }
            System.out.print("Nhập số lượt phân công: ");
            int soLuot = Integer.parseInt(sc.nextLine());
            BangPhanCong phanCong = BangPhanCongDAO.getInstance().selectByMaLXAndMaTuyen(maLaiXe, maTuyen);
            if (phanCong == null) {
                BangPhanCongDAO.getInstance().insert(new BangPhanCong(laiXePC, tuyenPC, soLuot));
            } else {
                int luot = phanCong.getLuot();
                System.out.println(luot);
                BangPhanCongDAO.getInstance().updateLuot(maLaiXe, maTuyen, (luot + soLuot));
            }
        }
    }

    private static void inDsPhanCong() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsPhanCong = BangPhanCongDAO.getInstance().selectAll();
        Set<Integer> dsMaLX = new HashSet<>();
        for (BangPhanCong phanCong : dsPhanCong) {
            dsMaLX.add(phanCong.getLaiXe().getMaLX());
        }
        for (int x : dsMaLX) {
            dsPhanCongByMaLX = BangPhanCongDAO.getInstance().selectByMaLX(x);
            for (LaiXe laiXe : dsLaiXe) {
                if (laiXe.getMaLX() == x) {
                    System.out.println("Danh sách phân công của lái xe " + laiXe.getHoTen());
                    break;
                }
            }
            for (BangPhanCong phanCong : dsPhanCongByMaLX) {
                System.out.println("Mã tuyến: " + phanCong.getTuyen().getMaTuyen() + " Số lượt: " + phanCong.getLuot());
            }
        }

    }

    private static void sapXepTheoTenLaiXe() {
        dsPhanCong = BangPhanCongDAO.getInstance().sortByTenLX();
        for (BangPhanCong phanCong : dsPhanCong) {
            System.out.println("Mã lái xe: " + phanCong.getLaiXe().getMaLX() + " Họ tên lái xe: " + phanCong.getLaiXe().getHoTen() + " Mã tuyến: " + phanCong.getTuyen().getMaTuyen() + " Số lượt: " + phanCong.getLuot());
        }
    }

    private static void sapXepTheoSoLuongTuyen() {
        dsPhanCong = BangPhanCongDAO.getInstance().sortByLuot();
        for (BangPhanCong phanCong : dsPhanCong) {
            System.out.println("Mã lái xe: " + phanCong.getLaiXe().getMaLX() + " Họ tên lái xe: " + phanCong.getLaiXe().getHoTen() + " Tổng lượt: " + phanCong.getLuot());
        }
    }

    private static void lapThongKe() {
        dsPhanCong = BangPhanCongDAO.getInstance().thongKe();
        for (BangPhanCong phanCong : dsPhanCong) {
            System.out.println("Mã lái xe: " + phanCong.getLaiXe().getMaLX() + " Họ tên lái xe: " + phanCong.getLaiXe().getHoTen() + " Tổng khoảng cách: " + phanCong.getLuot());
        }
    }

}