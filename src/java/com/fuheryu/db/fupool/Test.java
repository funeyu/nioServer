package com.fuheryu.db.fupool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Test {

    public static void main(String[] args) {
        try {
            ConnectionPool pool = ConnectionPool.bootStrap(4,  64, "root", "funer8090");
            Connection connection = pool.getOne();
            Statement st = connection.createStatement();
            String sql = "select name, number, start_time from seckill";
            ResultSet result = st.executeQuery(sql);

            while(result.next()) {
                System.out.println("--------------------");
                System.out.println("name:" + result.getString(1));
                System.out.println("number:" + result.getString(2));
                System.out.println("start_time:" + result.getString("start_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
