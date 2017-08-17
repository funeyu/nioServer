package com.fuheryu.db.fupool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by fuheyu on 2017/8/16.
 */
public interface DataSourceBase {

    /**
     * 根据username 和 password及数据库类型去获取一个原生的数据库连接
     * @return
     * @throws SQLException
     */
    Connection getNonPooledConnection(String dbType, String username, String password) throws SQLException;

    class Builder {

        private Builder() {}

        public static DriverHolder createBuilder(String type) throws
                ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

            return DriverHolder.init(type);
        }

    }

    class DriverHolder {
        private Driver holder;
        private DriverHolder(Driver holder) {
            this.holder = holder;
        }

        public static DriverHolder init(String sqlType)
                throws ClassNotFoundException, IllegalAccessException,
                InstantiationException, SQLException, NoDriverTypeException {

            if(PoolConfig.contains(sqlType)) {
                Driver driver = (Driver)Class.forName(PoolConfig.getDriverClassName(sqlType)).newInstance();
                DriverManager.registerDriver(driver);
                return new DriverHolder(driver);
            }

            throw new NoDriverTypeException(String.format("no support driver for type : %s", sqlType));
        }
    }
}
