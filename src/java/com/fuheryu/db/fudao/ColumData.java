package com.fuheryu.db.fudao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fuheyu on 2017/8/19.
 */
public class ColumData {

    private String columName;

    private String sqlType;

    private Object rawData;


    private ColumData(){}

//    static {
//        typeMapping.put("CHARACTER", String.class);
//        typeMapping.put("VARCHAR", String.class);
//        typeMapping.put("LONGVARCHAR", String.class);
//        typeMapping.put("NUMERIC", BigDecimal.class);
//        typeMapping.put("DECIMAL", BigDecimal.class);
//        typeMapping.put("BIT", Boolean.class);
//
//    }

    /**
     * 根据列名与数据及格式创建一 ColumnData
     * @param columName
     * @return
     */
    public static ColumData init(String columName, String sqlType, ResultSet rs) throws SQLException {

        ColumData colum = new ColumData();
        colum.setColumName(columName);
        colum.setSqlType(sqlType);

        if(sqlType == "CHARACTER") {
            colum.setRawData(rs.getString(columName));
        }
        else if(sqlType == "VARCHAR") {
            colum.setRawData(rs.getString(columName));
        }
        else if(sqlType == "LONGVARCHAR") {
            colum.setRawData(rs.getString(columName));
        }
        else if(sqlType == "NUMERIC") {
            colum.setRawData(rs.getBigDecimal(columName));
        }
        else if(sqlType == "DECIMAL") {
            colum.setRawData(rs.getBigDecimal(columName));
        }
        else if(sqlType == "BIT") {
            colum.setRawData(rs.getBoolean(columName));
        }
        else if(sqlType == "TINYINT") {
            colum.setRawData(rs.getByte(columName));
        }
        else if(sqlType == "SMALLINT") {
            colum.setRawData(rs.getShort(columName));
        }
        else if(sqlType == "INTEGER") {
            colum.setRawData(rs.getInt(columName));
        }
        else if(sqlType == "BIGINT") {
            colum.setRawData(rs.getLong(columName));
        }
        else if(sqlType == "REAL") {
            colum.setRawData(rs.getFloat(columName));
        }
        else if(sqlType == "FLOAT") {
            colum.setRawData(rs.getDouble(columName));
        }
        else if(sqlType == "DOUBLE") {
            colum.setRawData(rs.getDouble(columName));
        }
        else if(sqlType == "BINARY") {
            colum.setRawData(rs.getBytes(columName));
        }
        else if(sqlType == "VARBINARY") {
            colum.setRawData(rs.getBytes(columName));
        }
        else if(sqlType == "LONGVARBINARY") {
            colum.setRawData(rs.getBytes(columName));
        }
        else if(sqlType == "DATE") {
            colum.setRawData(rs.getDate(columName));
        }
        else if(sqlType == "TIME") {
            colum.setRawData(rs.getDate(columName));
        }
        else if(sqlType == "TIMESTAMP") {
            colum.setRawData(rs.getTimestamp(columName));
        }
        return colum;
    }


    public void setColumName(String columName) {
        this.columName = columName;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public String getColumName() {
        return columName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public Object getRawData() {
        return rawData;
    }
}
