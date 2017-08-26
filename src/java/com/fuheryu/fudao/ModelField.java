package com.fuheryu.fudao;

/**
 * Created by fuheyu on 2017/8/24.
 */
public class ModelField {

    private String filedName;

    private Class<?> filedType;

    private Object value;

    private ModelField(String filedName, Class<?> filedType, Object value) {

        this.filedName = filedName;
        this.filedType = filedType;
        this.value = value;
    }

    public static ModelField init(String filedName, Class<?> filedType, Object value) {

        return new ModelField(filedName, filedType, value);
    }

    public String getFiledName() {
        return filedName;
    }

    public Class<?> getFiledType() {

        return filedType;
    }

    public Object getValue() {
        return value;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public void setFiledType(Class<?> filedType) {
        this.filedType = filedType;
    }
}
