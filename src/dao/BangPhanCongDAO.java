package dao;

import database.JDBCUtil;
import model.BangPhanCong;
import model.LaiXe;
import model.Tuyen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BangPhanCongDAO implements DAOInterface<BangPhanCong> {
    private static ArrayList<LaiXe> dsLaiXe = new ArrayList<>();
    private static ArrayList<Tuyen> dsTuyen = new ArrayList<>();

    public static BangPhanCongDAO getInstance() {
        return new BangPhanCongDAO();
    }

    @Override
    public int insert(BangPhanCong bangPhanCong) {
        Connection con = JDBCUtil.getConnection();
        String sql = "insert into `phancong`(`maLX`, `maTuyen`, `luot`) values(?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, bangPhanCong.getLaiXe().getMaLX());
            st.setInt(2, bangPhanCong.getTuyen().getMaTuyen());
            st.setInt(3, bangPhanCong.getLuot());
            st.executeUpdate();
            System.out.println("Thêm phân công thành công!");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<BangPhanCong> selectAll() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();

        ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `phancong`";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                int maLX = rs.getInt("maLX");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("luot");

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    dsPhanCong.add(new BangPhanCong(laiXePC, tuyenPC, luot));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhanCong;
    }

    public ArrayList<BangPhanCong> selectByMaLX(int idLaiXe) {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();

        ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `phancong` WHERE (maLX = ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idLaiXe);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maLX = rs.getInt("maLX");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("luot");

                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    dsPhanCong.add(new BangPhanCong(laiXePC, tuyenPC, luot));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhanCong;
    }

    public BangPhanCong selectByMaLXAndMaTuyen(int idLaiXe, int idTuyen) {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();

        BangPhanCong bangPhanCong = null;
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `phancong` WHERE (maLX = ? AND maTuyen = ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idLaiXe);
            st.setInt(2, idTuyen);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int maLX = rs.getInt("maLX");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("luot");

                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    bangPhanCong = new BangPhanCong(laiXePC, tuyenPC, luot);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bangPhanCong;
    }

    public int updateLuot(int idLaiXe, int idTuyen, int soLuot) {
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE `phancong` SET `luot`= ? WHERE `maLX`= ? and `maTuyen`= ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, soLuot);
            st.setInt(2, idLaiXe);
            st.setInt(3, idTuyen);
            st.executeUpdate();
            System.out.println("Cập nhật thành công!");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<BangPhanCong> sortByTenLX() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();

        ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT laixe.id, laixe.hoTen, phancong.maTuyen, phancong.luot FROM laixe \n" +
                "JOIN phancong on phancong.maLX = laixe.id\n" +
                "order by laixe.hoTen";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maLX = rs.getInt("id");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("luot");

                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    dsPhanCong.add(new BangPhanCong(laiXePC, tuyenPC, luot));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhanCong;
    }

    public ArrayList<BangPhanCong> sortByLuot() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();

        ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT laixe.id, laixe.hoTen, phancong.maTuyen, sum(phancong.luot) as tong_luot FROM laixe \n" +
                "JOIN phancong on phancong.maLX = laixe.id\n" +
                "group by laixe.id\n" +
                "order by sum(phancong.luot) desc\n";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maLX = rs.getInt("id");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("tong_luot");

                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    dsPhanCong.add(new BangPhanCong(laiXePC, tuyenPC, luot));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhanCong;
    }

    public ArrayList<BangPhanCong> thongKe() {
        dsLaiXe = LaiXeDAO.getInstance().selectAll();
        dsTuyen = TuyenDAO.getInstance().selectAll();

        ArrayList<BangPhanCong> dsPhanCong = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT laixe.id, laixe.hoTen, phancong.maTuyen, sum(phancong.luot * tuyen.khoangCach) as tong_khoang_cach FROM laixe \n" +
                "JOIN phancong on phancong.maLX = laixe.id\n" +
                "join tuyen on phancong.maTuyen = tuyen.id\n" +
                "group by laixe.id\n";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maLX = rs.getInt("id");
                int maTuyen = rs.getInt("maTuyen");
                int luot = rs.getInt("tong_khoang_cach");

                LaiXe laiXePC = null;
                Tuyen tuyenPC = null;

                for (LaiXe laiXe : dsLaiXe) {
                    if (laiXe.getMaLX() == maLX) {
                        laiXePC = laiXe;
                        break;
                    }
                }
                for (Tuyen tuyen : dsTuyen) {
                    if (tuyen.getMaTuyen() == maTuyen) {
                        tuyenPC = tuyen;
                        break;
                    }
                }

                if (laiXePC != null && tuyenPC != null) {
                    dsPhanCong.add(new BangPhanCong(laiXePC, tuyenPC, luot));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhanCong;
    }
}
