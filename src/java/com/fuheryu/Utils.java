package com.fuheryu;

import com.fuheryu.controller.ControllerMethod;
import com.fuheryu.http.AnnotationFactory.RouterMapping;
import com.fuheryu.http.AnnotationFactory.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by fuheyu on 2017/8/4.
 */
public class Utils {

    private final static Properties prop = new Properties();

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {

        return prop.getProperty(key);
    }

    /*
        遍历文件目录，查找.class，返回反射类数组
    */
    private static List<Class> walkDir(File file, String packageName) {

        System.out.println(file.getName());
        List<Class> classes = new ArrayList<Class>();

        try {
            if(file.isFile()) {
                String fileName = file.getName();
                if(fileName.endsWith(".class")) {
                    classes.add(Class.forName(packageName + "." +fileName.substring(0, fileName.lastIndexOf("."))));
                }

                return classes;
            } else {
                File[] files = file.listFiles();
                for(File f : files) {
                    String tempName = f.getName();
                    System.out.println("lsitname:" + f.getName() + f.isDirectory());
                    if(!f.isDirectory() && tempName.endsWith(".class")) {
                        classes.add(Class.forName(packageName + "." + tempName.substring(0, tempName.lastIndexOf("."))));
                    } else if(f.isDirectory()) {
                        classes.addAll(walkDir(f, packageName + "." + file.getName()));
                    }

                }
            }
            return classes;

        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }


        return null;
    }


    /*
        根据packageName（com.xxx.xxx）获取该包下的所有Class
     */
    public static List<Class> getClassesPageName(String packageName) {

        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));

            while(dirs.hasMoreElements()) {
                URL dir = dirs.nextElement();
                File f = new File(dir.getPath());
                if(f.isDirectory()) {
                    List<Class> list = walkDir(f, packageName);
                    return list;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }


        return null;
    }

    /*
        某个class下查找特定注解的控制器的信息
    */
    public static List<ControllerMethod> getAnnotedControllers(Class<?> clazz) {
        List<ControllerMethod> results = new ArrayList<ControllerMethod>();

        Method[] methods = clazz.getMethods();
        RouterMapping rmapping;
        for(Method m : methods) {
            if((rmapping = m.getAnnotation(RouterMapping.class)) != null) {

                results.add(ControllerMethod.init(m, clazz, rmapping.path(), rmapping.method()));
            }
        }

        return results;
    }

}
