package com.fuheryu.fupool;

import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class PoolConfig {

    private final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private final static String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.SQLServerDriver";

    private final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private final static String DB2_DRIVER = "com.ibm.db2.jdbc.app.DB2.Driver";

    private final static String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    private final static HashMap<String, String> container = new HashMap();

    static {
        container.put("MYSQL_DRIVER", MYSQL_DRIVER);
        container.put("SQL_SERVER_DRIVER", SQL_SERVER_DRIVER);
        container.put("ORACLE_DRIVER", ORACLE_DRIVER);
        container.put("DB2_DRIVER", DB2_DRIVER);
        container.put("POSTGRESQL_DRIVER", POSTGRESQL_DRIVER);
    }

    public static boolean contains(String type) {
        return container.containsKey(type);
    }

    public static String getDriverClassName(String name) {
        return container.get(name);
    }
}
