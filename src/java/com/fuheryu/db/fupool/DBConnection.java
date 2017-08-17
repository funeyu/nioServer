package com.fuheryu.db.fupool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class DBConnection implements DataSourceBase{
    public Connection getNonPooledConnection(String dbType, String username, String password) throws SQLException {

        String dbUrl = Utils.getProperty("dbUrl");
        Connection connection;

        if(PoolConfig.contains(dbType)) {
            try {
                Builder.createBuilder(dbType);
                connection = DriverManager.getConnection(dbUrl, username, password);
                return connection;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public Connection getNonPooledConnection(String username, String password)
            throws SQLException,NoConnectionTypeException {

        String dbUrl = Utils.getProperty("dbUrl");
        if(dbUrl.indexOf("mysql") > -1) {
            return getNonPooledConnection("MYSQL", username, password);
        }
        else if(dbUrl.indexOf("oracle") > -1) {
            return getNonPooledConnection("ORACLE", username, password);
        }
        else if(dbUrl.indexOf("db2") > -1) {
            return getNonPooledConnection("DB2", username, password);
        }
        else if(dbUrl.indexOf("postgresql") > -1) {
            return getNonPooledConnection("POSTGRESQL", username, password);
        }

        throw new NoConnectionTypeException(String.format("no such url: %s connection", dbUrl));
    }
}
