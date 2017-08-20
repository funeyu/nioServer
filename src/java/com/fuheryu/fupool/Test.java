package com.fuheryu.fupool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Test {

    public static void main(String[] args) {
        FuPool pool =null;
        Connection connection =null;
        try {
            pool = FuPool.bootStrap();
            connection = pool.getOne();
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
        } finally {
            if(pool != null && connection != null) {
                pool.returnedOne(connection);
            }
        }
    }
}
