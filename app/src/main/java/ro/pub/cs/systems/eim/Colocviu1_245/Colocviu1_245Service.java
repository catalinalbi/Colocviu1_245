package ro.pub.cs.systems.eim.Colocviu1_245;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Colocviu1_245Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("SERVER", "START SERVICE");
        int sum = intent.getIntExtra("sum", 0);
        processingThread = new ProcessingThread(this, sum);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
