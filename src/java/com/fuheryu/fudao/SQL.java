package com.fuheryu.fudao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by fuheyu on 2017/8/24.
 */
public class SQL {

    /**
     * 根据Model 创建insert的sql语句
     * @param model
     * @param <T>
     * @return
     */
    public static <T extends Model> String insert(ArrayList<ModelField> fieldsInfo, T model) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(model.getClass().getSimpleName().toLowerCase());
        sb.append(" (");

        for (ModelField mf : fieldsInfo) {
            sb.append(mf.getFiledName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUE (");

        for (int i = 0; i <fieldsInfo.size(); i ++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }

    /**
     * 拼接update的sql语句
     * update 的sql类型：
     * ("UPDATE items SET name = ?, category = ?, price = ?, quantity = ? WHERE id = ?");
     * @param fields
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Model> StringBuffer update(ArrayList<ModelField> fields, Class<T> clazz) {

        StringBuffer sb = new StringBuffer("UPDATE ")
                .append(clazz.getSimpleName().toLowerCase())
                .append(" SET");
        for(ModelField field : fields) {
            sb.append(" ")
                    .append(field.getFiledName())
                    .append(" = ?,");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb;
    }

}
