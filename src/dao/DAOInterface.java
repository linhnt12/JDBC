package dao;

import java.util.ArrayList;

public interface DAOInterface<T> {
    public int insert(T t);

    public ArrayList<T> selectAll();
}
