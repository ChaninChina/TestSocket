package com.chanin;

public class Server {

    public static final String TAG = "Server";

    public static void main(String[] args) {
        java.net.ServerSocket serverSocket = null;
        try {

            serverSocket = new java.net.ServerSocket(8888);
            com.chanin.utils.Log.d(TAG,"start");
            while (true){
                java.net.Socket socket = serverSocket.accept();
                com.chanin.utils.Log.d(TAG,"accept");
                new com.chanin.SocketThread(socket).start();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

    }

}
