package com.fuheryu;

import com.fuheryu.controller.ControllerMethod;
import com.fuheryu.http.AnnotationFactory.RouterMapping;
import com.fuheryu.http.AnnotationFactory.Controller;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import me.zzp.ar.DB;
import me.zzp.ar.Record;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    private static Enumeration<URL> getDirs(String packageName) throws IOException {

        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));
        return dirs;
    }

    private static URL getOneDir(String packageName) throws IOException {

        Enumeration<URL> dirs = getDirs(packageName);
        return dirs.nextElement();
    }

    /*
        根据packageName（com.xxx.xxx）获取该包下的所有Class
     */
    public static List<Class> getClassesPageName(String packageName) {

        try {
            Enumeration<URL> dirs = getDirs(packageName);

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

    /*
        获取某路径下的所有类，除了某些Class类,
        note:要保证这里的每个service name 独一无二
     */
    public static List<Class> getCalssesInDir(String packageS, Class...others) {

        try {
            URL source = getOneDir(packageS);
            List<Class> classes = walkDir(new File(source.getPath()), packageS);

            HashSet<String> otherNames = new HashSet<String>();
            for(Class an : others) {
                otherNames.add(an.getName());
            }
            List<Class> results = classes.stream()
                    .filter(one -> {
                                return !otherNames.contains(one.getName());
                    }).collect(Collectors.toList());

            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


//    public static void main(String[] args) {
//
//        String hostName = "localhost";
//        String dbName = "seckill";
//        String userName = "root";
//        String passWord = "funer8090";
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
//            Connection conn = DriverManager.getConnection(connectionURL, userName, passWord);
//
//            Statement st = conn.createStatement();
//
//            String sql = "select name, number, start_time from seckill";
//
//            ResultSet result = st.executeQuery(sql);
//
//            while(result.next()) {
//                System.out.println("--------------------");
//                System.out.println("name:" + result.getString(1));
//                System.out.println("number:" + result.getString(2));
//                System.out.println("start_time:" + result.getString("start_time"));
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        DB mysql = DB.open("jdbc:mysql://localhost:3306/seckill", userName, passWord);
//        List<Record> list = mysql.active("seckill").findBy("name", "1000元秒杀iphone6");
//        for(Record l : list) {
//            System.out.println("record" + l.get("number"));
//        }
//
//    }

}
