package ro.pub.cs.systems.eim.Colocviu1_245;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread{

    private boolean running = false;
    private Context context = null;
    private int sum = Integer.MAX_VALUE;

    public ProcessingThread(Context context, int sum) {
        this.context = context;
        this.sum = sum;
    }

    public void run() {
        running = true;
        while (running) {
            Log.v("da", "adsasdas");
            sendMessage();
            sleep();
        }
    }

    public void stopThread() {
        running = false;
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("SEND_DATA");
        intent.putExtra("NEW_DATA",
                new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
