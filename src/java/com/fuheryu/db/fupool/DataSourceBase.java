package com.fuheryu.db.fupool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by fuheyu on 2017/8/16.
 */
public interface DataSourceBase {

    /**
     * 根据username 和 password获取一个原生的数据库连接
     * @return
     * @throws SQLException
     */
    Connection getNonPooledConnection(String username, String password) throws SQLException;

    class Builder {

        private Builder() {}

    }
}
