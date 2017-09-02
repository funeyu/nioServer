package com.fuheryu.bench.java; /**
 * Created by fuheyu on 2017/9/2.
 */
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: main program of server
 * @author: jitianyu
 * @time: Aug 24, 2016
 *        6:21:40 AM
 */
class Server extends Thread{
    File doucumentRootDirectory;
    String indexFileName;
    ServerSocket server = null;
    int numThreads;
    public Server( File rootDirectory, String index, ServerSocket server, int num ){
        if(!rootDirectory.isDirectory()){
            System.out.println( "Not a Directory" );
            System.exit(0);
        }
        this.doucumentRootDirectory = rootDirectory;
        this.indexFileName = index;
        this.server = server;
        this.numThreads = num;
    }
    @Override
    public void run(){
        for( int i = 0; i < numThreads; i++ ){
            Thread t = new Thread( new RequestProcessor( doucumentRootDirectory, indexFileName ) );
            t.start();
        }
        Socket request = null;
        while(true){
            try {
                request = server.accept();
                //add incoming request to the request queue
                RequestProcessor.processRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
				/* do not close socket here
				*/
            }
        }
    }

    public static void main(String[] args) {
        File rootDir = new File("src/myHttpServer/webContent/");
        String index = "index.html";
        int num = 50;
        try {
            ServerSocket server = new ServerSocket(3399);
            Thread serverMain = new Server(rootDir, index, server, num);
            serverMain.start();
        } catch (IOException e) {
            System.out.println("Creating ServerSocket Occurs an Exception");
            System.exit(0);
        }
    }
}