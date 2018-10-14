package com.chanin;

public class SocketThread extends Thread {

    public static final String TAG = "SocketThread";
    private java.net.Socket socket;
    private java.io.BufferedReader reader;
    private java.io.PrintWriter printWriter;

    public SocketThread(java.net.Socket socket) {
        super("SocketThread");
        this.socket = socket;
        java.net.SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
        java.net.SocketAddress localSocketAddress = socket.getLocalSocketAddress();
        com.chanin.utils.Log.d(TAG, "remoteSocketAddress : " + remoteSocketAddress.toString());
        com.chanin.utils.Log.d(TAG, "localSocketAddress : " + localSocketAddress.toString());

    }


    @Override
    public void run() {
        super.run();

        try {
            while (socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown()) {
                reader = new java.io.BufferedReader(new java.io.InputStreamReader(
                        socket.getInputStream(), com.chanin.utils.Constants.UTF8));
                printWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(
                        socket.getOutputStream(), com.chanin.utils.Constants.GBK)), true);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        while (socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown()){
                            String data = "这个是服务器推送消息！不做任何处理之间显示";
                            printWriter.println("Start20>>>" + data + com.chanin.utils.MD5Util.MD5(data) + "*****");
                            printWriter.flush();
                            try {
                                Thread.sleep(60*1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                String readLine;
                while (socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown()) {
                    readLine = reader.readLine();
                    if (readLine != null && readLine.length() > 0) {
                        com.chanin.utils.Log.d(TAG, readLine);
                        if (readLine.contains("StartDataGet00>>>")) {
                            printWriter.println("Start00>>>{{3@@&301}}" + com.chanin.utils.MD5Util.MD5("{{3@@&301}}") + "*****");
                            //printWriter.println("Start00>>>NODATA" + com.chanin.utils.MD5Util.MD5("NODATA") + "*****");
                            printWriter.flush();
                        } else if (readLine.contains("StartDataGet60>>>")) {
                            String data = "{{抽检超时@@2018-07-14@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:00@@1}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:00@@2}}" +
                                    "{{异常A@@2018-07-12@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-13 20:03:00@@3}}" +
                                    "{{抽检超时@@2018-07-11@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:00@@4}}" +
                                    "{{异常C@@2018-07-10@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-12 20:03:00@@5}}" +
                                    "{{抽检超时@@2018-07-14@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:00@@6}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-11 20:03:00@@7}}";
                            printWriter.println("Start60>>>" + data + com.chanin.utils.MD5Util.MD5(data) + "*****");
                            printWriter.flush();
                        } else if (readLine.contains("StartDataGet70>>>")) {
                            String data = "{{抽检超时@@2018-07-14@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:14@@1}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:11@@2}}" +
                                    "{{抽检超时@@2018-07-12@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:23@@3}}" +
                                    "{{异常B@@2018-07-11@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 21:03:12@@4}}" +
                                    "{{抽检超时@@2018-07-10@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:12@@5}}" +
                                    "{{抽检超时@@2018-07-14@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:12@@6}}" +
                                    "{{异常B@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:12@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:13:12@@7}}" +
                                    "{{异常A@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:12@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:21@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:12@@7}}";
                            printWriter.println("Start70>>>" + data + com.chanin.utils.MD5Util.MD5(data) + "*****");
                            printWriter.flush();
                        } else if (readLine.contains("StartDataGet80>>>")) {
                            String data = "{{抽检超时@@2018-07-14@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@1}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@2}}" +
                                    "{{异常D@@2018-07-12@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@3}}" +
                                    "{{抽检超时@@2018-07-11@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@4}}" +
                                    "{{抽检超时@@2018-07-10@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@5}}" +
                                    "{{异常C@@2018-07-14@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@6}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{异常B@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{异常B@@2018-07-13@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{异常D@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-15 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{异常D@@2018-07-13@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-16 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{异常A@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@1已处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-19 20:03:20@@7}}" +
                                    "{{异常A@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-14 20:03:20@@7}}" +
                                    "{{抽检超时@@2018-07-13@@订单号;线别;计划完成时间;@@0还未处理@@<NULL>@@<NULL>@@2018-07-12 20:03:20@@7}}";
                            printWriter.println("Start80>>>" + data + com.chanin.utils.MD5Util.MD5(data) + "*****");
                            printWriter.flush();
                        }
                    } else {
                        printWriter.println("OK!");
                    }

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
