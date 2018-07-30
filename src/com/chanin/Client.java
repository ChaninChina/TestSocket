package com.chanin;

public class Client {




    private static final String TAG = "Connection";
    public static final int ERROR = -1;
    public static final int SUCCESS = 1;
    private static java.net.Socket socket;
    private static java.io.PrintWriter printWriter;
    private static java.io.BufferedReader reader;
    public static final int RETRY_TIME = 3;
    private static InputThread inputThread;
    private java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();
    private static java.util.concurrent.BlockingQueue<Integer> connectState = new java.util.concurrent.ArrayBlockingQueue<>(1);
    static java.util.concurrent.ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        try {
            int retry = RETRY_TIME;
            while (!isInputConnected()) {
                if (retry < 0) {
                    System.out.println("connect error :");
                    putConnectState(ERROR);
                    return;
                }
                retry--;
                socket = new java.net.Socket();
                socket.connect(new java.net.InetSocketAddress(com.chanin.utils.Constants.IP, com.chanin.utils.Constants.PORT), 1000);
                reader = new java.io.BufferedReader(new java.io.InputStreamReader(
                        socket.getInputStream(), com.chanin.utils.Constants.UTF8));
                printWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(
                        socket.getOutputStream(), com.chanin.utils.Constants.UTF8)), true);
            }
            System.out.println("connect!!!!");
            putConnectState(SUCCESS);
            inputThread = new InputThread(printWriter);
            executorService.execute(inputThread);
            while (isInputConnected()) {
                String message = reader.readLine();
                com.chanin.utils.Log.d(TAG, "receive message :" + message);
                dealMessage(message);
            }
        } catch (java.io.IOException e) {
            com.chanin.utils.Log.d(TAG, "sendMessage error " + e.getMessage());
            e.printStackTrace();
        } finally {
            putConnectState(ERROR);
            com.chanin.utils.IOUtils.close(reader);
            com.chanin.utils.IOUtils.close(printWriter);
            try {
                if(socket!=null){
                    socket.close();
                    socket = null;
                }
                if (inputThread!=null){
                    inputThread.setBreak(false);
                    executorService.shutdown();
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            reader = null;
            printWriter = null;
        }
    }


    private Integer pollConnectState() {
        Integer poll = null;
        try {
            poll = connectState.poll(5, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            com.chanin.utils.Log.d(TAG, "pollConnectState error " + e.getMessage());
        }

        return poll;
    }

    private static void putConnectState(int i) {
        try {
            connectState.put(i);
        } catch (InterruptedException e) {
            com.chanin.utils.Log.d(TAG, "putConnectState error " + e.getMessage());
        }
    }

    private static void dealMessage(String message) {

        com.chanin.utils.Log.d(TAG,message);
    }

    public void stopConnection() {
        if (socket != null) {
            com.chanin.utils.IOUtils.close(printWriter);
            com.chanin.utils.IOUtils.close(reader);
            printWriter = null;
            reader = null;
            try {
                socket.close();
                socket = null;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean sendMessage(String message) {
        lock.lock();
        try {
           // startConnection();
            if (isOutputConnected()) {
                printWriter.println(message);
                printWriter.flush();
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }


//    public void asynSendMessage(String message){
//        Flowable.just(sendMessage(message)).
//    }



    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public static boolean isInputConnected() {
        boolean b = socket != null;
        if(socket==null){
            return false;
        }
        boolean connected = socket.isConnected();
        boolean closed = socket.isClosed();
        return b && connected && !closed && !socket.isInputShutdown() && reader != null;
    }


    public boolean isOutputConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed() && !socket.isOutputShutdown() && printWriter != null;
    }

}
