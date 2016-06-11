package es.npatarino.android.gotchallenge.view.executor;

import android.os.Handler;
import android.os.Looper;

import com.tonilopezmr.interactorexecutor.MainThread;

public class MainThreadImp implements MainThread {

    private Handler handler;

    public MainThreadImp() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
