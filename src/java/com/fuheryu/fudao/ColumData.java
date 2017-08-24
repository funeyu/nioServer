package com.fuheryu.fudao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/19.
 */
public class ColumData {

    private String columName;

    private String sqlType;

    private Object rawData;


    private ColumData(){}

    /**
     * 根据列名与数据及格式创建一 ColumnData
     * @param columName
     * @return
     */
    public static ColumData init(String columName, String sqlType, ResultSet rs) throws SQLException {

        ColumData colum = new ColumData();
        colum.setColumName(columName);
        colum.setSqlType(sqlType);
        switch (sqlType) {
            case "CHARACTER" :
                colum.setRawData(rs.getString(columName));
                break;
            case "VARCHAR" :
                colum.setRawData(rs.getString(columName));
                break;
            case "LONGVARCHAR" :
                colum.setRawData(rs.getString(columName));
                break;
            case "NUMERIC" :
                colum.setRawData(rs.getBigDecimal(columName));
                break;
            case "DECIMAL" :
                colum.setRawData(rs.getBigDecimal(columName));
                break;
            case "INT" :
                colum.setRawData(rs.getInt(columName));
                break;
            case "BIT" :
                colum.setRawData(rs.getBoolean(columName));
                break;
            case "TINYINT" :
                colum.setRawData(rs.getByte(columName));
                break;
            case "SMALLINT" :
                colum.setRawData(rs.getShort(columName));
                break;
            case "INTEGER" :
                colum.setRawData(rs.getInt(columName));
                break;
            case "BIGINT" :
                colum.setRawData(rs.getLong(columName));
                break;
            case "REAL" :
                colum.setRawData(rs.getFloat(columName));
                break;
            case "FLOAT" :
                colum.setRawData(rs.getDouble(columName));
                break;
            case "DOUBLE" :
                colum.setRawData(rs.getDouble(columName));
                break;
            case "BINARY" :
                colum.setRawData(rs.getBytes(columName));
                break;
            case "VARBINARY" :
                colum.setRawData(rs.getBytes(columName));
                break;
            case "LONGVARBINARY" :
                colum.setRawData(rs.getBytes(columName));
                break;
            case "DATE" :
                colum.setRawData(rs.getDate(columName));
                break;
            case "TIME" :
                colum.setRawData(rs.getDate(columName));
                break;
            case "TIMESTAMP" :
                colum.setRawData(rs.getTimestamp(columName));
                break;
        }

        return colum;
    }

    public static void fillStatement(PreparedStatement pre, Object object, Class<?> type, int index) throws SQLException {
        switch (type.getSimpleName()) {
            case "string":
                pre.setString(index, (String)object);
                break;
            case "int":
                pre.setInt(index, (Integer)object);
                break;
            case "biginteger":
                pre.setLong(index, (long)object);
                break;
            case "timestamp":
                pre.setTimestamp(index, (Timestamp)object);
                break;

        }
    }

    /**
     * 获取Model实例的字段名称,Map<fieldName, fieldType>
     * @param model
     * @param <T>
     * @return
     */
    public static <T extends Model> ArrayList<ModelField> getFiledName(T model) {

        Field[] fields = model.getClass().getDeclaredFields();
        ArrayList<ModelField> results = new ArrayList<>();
        for(int i = 0; i < fields.length; i ++) {

            results.add(ModelField.init(fields[i].getName(), fields[i].getType()));
        }

        return results;
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
