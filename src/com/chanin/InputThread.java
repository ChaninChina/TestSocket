package com.chanin;

public class InputThread extends Thread {

    private java.io.PrintWriter printWriter;
    public static final String TAG = "InputThread";
    private boolean isBreak = true;

    public InputThread(java.io.PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }

    @Override
    public void run() {
        super.run();

        try {
            while (isBreak) {
                Thread.sleep(1000);
                printWriter.println("sendMessage");
                printWriter.flush();
                com.chanin.utils.Log.d(TAG, "sendMessage");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            com.chanin.utils.IOUtils.close(printWriter);
            com.chanin.utils.Log.d(TAG, "Close!");
        }

    }
}
