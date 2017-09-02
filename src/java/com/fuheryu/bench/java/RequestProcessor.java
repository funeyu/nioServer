package com.fuheryu.bench.java; /**
 * Created by fuheyu on 2017/9/2.
 */

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @Description: thread that proceed request;
 * @author: jitianyu
 * @time: Aug 24, 2016
 *        6:23:37 AM
 */
public class RequestProcessor implements Runnable {

    File doucumentRootDir;
    String indexFileName;

    public RequestProcessor( File f, String str){
        doucumentRootDir= f;
        indexFileName = str;
    }
    private static LinkedList<Socket> pool = new LinkedList<>();
    @Override
    public void run() {
        while(true){
            Socket connection = null;
            synchronized(pool){
                try{
                    while( pool.isEmpty() )
                        pool.wait();
                }catch(InterruptedException e) {
                    //do nothing
                }
                connection = (Socket)pool.remove(0);
            }
            try {
                connection.setSoTimeout(10);
                Request requestImpl = new Request(connection.getInputStream());
                Response responseImpl = null;
                System.out.println( requestImpl.getMethod() );
                if( requestImpl.getMethod().equals("GET")){
                    String filename = requestImpl.getRequestURL();
                    //check-postfix
                    if( filename.endsWith("/")) filename += indexFileName;
                    System.out.println("requestURL is: " + filename );
					/*
					 * 1. this is another method to create File object
					 * 2. request url always begins with a "/"
					 */
                    File fileToWrite = new File(doucumentRootDir,filename.substring( 1,filename.length() ));
                    if(!fileToWrite.canRead())responseImpl = new Response(requestImpl.getVersion(),filename, "404", null);
                    else{
                        DataInputStream fin = new DataInputStream( new BufferedInputStream( new FileInputStream(fileToWrite)));
                        byte[] fileData = new byte[(int)fileToWrite.length()];
                        fin.readFully( fileData );
                        fin.close();
                        responseImpl = new Response(requestImpl.getVersion(),filename,"200", fileData);
                    }
                }else responseImpl = new Response(requestImpl.getVersion(),requestImpl.getRequestURL(), "501", null);

                responseImpl.writeResponse(connection.getOutputStream());
                if( !connection.isClosed() && connection.isConnected() ) connection.close();
            } catch (IOException e) {
                System.out.println("Reading and Writing Request Throws an Exception");
                e.printStackTrace();
            }
        }
    }
    public static void processRequest( Socket request ){
        synchronized(pool){
            pool.add( pool.size(), request );
            pool.notifyAll();
        }
    }
}
