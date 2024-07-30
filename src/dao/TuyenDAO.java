package dao;

import database.JDBCUtil;
import model.LaiXe;
import model.Tuyen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TuyenDAO implements DAOInterface<Tuyen>{
    public static TuyenDAO getInstance() {
        return new TuyenDAO();
    }

    public int getSoLuong() {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT count(*) FROM `tuyen`";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(Tuyen tuyen) {
        Connection con = JDBCUtil.getConnection();
        String sql = "insert into `tuyen`(`id`, `khoangCach`, `soDiemDung`) values(?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, tuyen.getMaTuyen());
            st.setInt(2, tuyen.getKhoangCach());
            st.setInt(3, tuyen.getSoDiemDung());
            st.executeUpdate();
            System.out.println("Thêm tuyến xe thành công!");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<Tuyen> selectAll() {
        ArrayList<Tuyen> dsTuyen = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `tuyen`";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maTuyen = rs.getInt("id");
                int khoangCach = rs.getInt("khoangCach");
                int soDiemDung = rs.getInt("soDiemDung");
                dsTuyen.add(new Tuyen(maTuyen, khoangCach, soDiemDung));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTuyen;
    }
}
