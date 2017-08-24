package com.fuheryu.fudao;

/**
 * Created by fuheyu on 2017/8/24.
 */
public class ModelField {

    private String filedName;

    private Class<?> filedType;

    private ModelField(String filedName, Class<?> filedType) {
        this.filedName = filedName;
        this.filedType = filedType;
    }

    public static ModelField init(String filedName, Class<?> filedType) {
        return new ModelField(filedName, filedType);
    }

    public String getFiledName() {
        return filedName;
    }

    public Class<?> getFiledType() {
        return filedType;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public void setFiledType(Class<?> filedType) {
        this.filedType = filedType;
    }
}
