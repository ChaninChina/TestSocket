package com.chanin;

public class SocketClientThread extends Thread {


    private java.net.Socket socket;
    private java.io.BufferedReader reader;
    private java.io.PrintWriter printWriter;

    public SocketClientThread(java.net.Socket socket) {
        super("SocketThread");
        this.socket = socket;
        java.net.SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
        java.net.SocketAddress localSocketAddress = socket.getLocalSocketAddress();
        System.out.println(remoteSocketAddress.toString());
        System.out.println(localSocketAddress.toString());
    }


    @Override
    public void run() {
        super.run();

        try {
            while (socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && socket.isOutputShutdown()) {
                reader = new java.io.BufferedReader(new java.io.InputStreamReader(
                        socket.getInputStream(), com.chanin.utils.Constants.UTF8));
                printWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(
                        socket.getOutputStream(), com.chanin.utils.Constants.UTF8)), true);
                String readLine;
                while ((readLine = reader.readLine()) != null) {
                    System.out.println(readLine);
                    printWriter.println("OK!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.chanin.utils.IOUtils.close(reader);
            com.chanin.utils.IOUtils.close(printWriter);
            if (socket != null) {
                try {
                    socket.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
