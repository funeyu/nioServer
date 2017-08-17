package com.fuheryu.db.fupool;

import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class PoolConfig {

    private final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private final static String SQL_SERVER = "com.microsoft.sqlserver.SQLServerDriver";

    private final static String ORACLE = "oracle.jdbc.driver.OracleDriver";

    private final static String DB2 = "com.ibm.db2.jdbc.app.DB2.Driver";

    private final static String POSTGRESQL = "org.postgresql.Driver";

    private final static HashMap<String, String> container = new HashMap();

    static {
        container.put("MYSQL_DRIVER", MYSQL_DRIVER);
        container.put("SQL_SERVER", SQL_SERVER);
        container.put("ORACLE", ORACLE);
        container.put("DB2", DB2);
        container.put("POSTGRESQL", POSTGRESQL);
    }

    public static boolean contains(String type) {
        return container.containsKey(type);
    }

    public static String getDriverClassName(String name) {
        return container.get(name);
    }
}
