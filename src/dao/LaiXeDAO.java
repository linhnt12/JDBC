package dao;

import database.JDBCUtil;
import model.LaiXe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LaiXeDAO implements DAOInterface<LaiXe>  {
    public static LaiXeDAO getInstance() {
        return new LaiXeDAO();
    }

    public int getSoLuong() {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT count(*) FROM `laixe`";
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
    public int insert(LaiXe laiXe) {
        Connection con = JDBCUtil.getConnection();
        String sql = "insert into laixe (`id`, `hoTen`, `dchi`, `sdt`, `trinhDo`) values(?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, laiXe.getMaLX());
            st.setString(2, laiXe.getHoTen());
            st.setString(3, laiXe.getDchi());
            st.setString(4, laiXe.getSdt());
            st.setString(5, laiXe.getTrinhDo());
            st.executeUpdate();
            System.out.println("Thêm lái xe thành công!");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<LaiXe> selectAll() {
        ArrayList<LaiXe> dsLaiXe = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `laixe`";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maLX = rs.getInt("id");
                String hoTen = rs.getString("hoTen");
                String dchi = rs.getString("dchi");
                String sdt = rs.getString("sdt");
                String trinhDo = rs.getString("trinhDo");
                dsLaiXe.add(new LaiXe(maLX, hoTen, dchi, sdt, trinhDo));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLaiXe;
    }
}
