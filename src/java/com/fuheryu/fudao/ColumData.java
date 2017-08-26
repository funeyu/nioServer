package com.fuheryu.fudao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.*;
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

    public static void fillStatement(PreparedStatement pre, ModelField field, int index)throws SQLException {

        String type = field.getFiledType().getSimpleName().toLowerCase();
        Object fieldValue = field.getValue();

        switch (type) {
            case "string":
                pre.setString(index, (String)fieldValue);
                break;
            case "int":
                pre.setInt(index, (Integer)fieldValue);
                break;
            case "biginteger":
                pre.setLong(index, ((BigInteger)fieldValue).longValue());
                break;
            case "timestamp":
                pre.setTimestamp(index, (Timestamp)fieldValue);
                break;
            case "float":
                pre.setFloat(index, (Long)fieldValue);
                break;
            case "double":
                pre.setDouble(index, (Double)fieldValue);
                break;
            case "time":
                pre.setTime(index, (Time)fieldValue);
                break;
            case "byte[]":
                pre.setBytes(index, (byte[])fieldValue);
                break;

        }
    }

    /**
     * 获取Model实例的字段数据
     * @param model
     * @param <T>
     * @return
     */
    public static <T extends Model> ArrayList<ModelField> extractFieldData(T model) {

        Field[] fields = model.getClass().getDeclaredFields();

        ArrayList<ModelField> results = new ArrayList<>();
        for(int i = 0; i < fields.length; i ++) {
            Object fieldValue = filedValue(model, fields[i].getName());
            results.add(ModelField.init(fields[i].getName(), fields[i].getType(), fieldValue));
        }

        return results;
    }

    /**
     * 获取key下的属性值
     * @param key
     * @return
     */
    private static <T extends Model> Object filedValue(T model, String key) {

        try {
            Method m = model.getClass().getMethod(methodGET(key));
            return m.invoke(model);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某列名下字段的类型
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Model> Class filedType(Class<T> clazz, String key) {

        try {
            Field field = clazz.getDeclaredField(key);
            return field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 获取key的get方法
     * @param key
     * @return
     */
    private static String methodGET(String key) {

        return "get" + (key.substring(0,1).toUpperCase() + key.substring(1));
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

    public static void main(String[] args) {

        byte[] s = new byte[9];
        System.out.println(s.getClass().getTypeName());
    }
}
