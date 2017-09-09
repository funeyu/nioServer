package com.fuheryu.core;

import com.fuheryu.core.annotation.AnnotationFactory.RouterMapping;
import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.stream.Collectors;

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

    /*
        合并buffers
     */
    public static ByteBuffer concat(ArrayList<ByteBuffer> buffers) {
        int capacity = 0;
        for(ByteBuffer buffer : buffers) {
            capacity += buffer.capacity();
        }

        ByteBuffer all = ByteBuffer.allocate(capacity);

        for(ByteBuffer bb : buffers) {
            all.put(bb);
        }

        return all;
    }


    private static  final Unsafe THE_UNSAFE;

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception
                {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };

            THE_UNSAFE = AccessController.doPrivileged(action);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    public static Unsafe getUnsafe() {

        return THE_UNSAFE;
    }

}
