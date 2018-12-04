package org.fonuhuolian.nohttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import org.fonuhuolian.xnohttp.callback.XLoadingDownLoadDialog;

public class MainActivity extends AppCompatActivity {

    private XLoadingDownLoadDialog dialog;

    int progress = 0;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.setProgress(progress += 20);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new XLoadingDownLoadDialog(this, null, true);

        dialog.show();

        handler.sendEmptyMessageDelayed(1, 1000);
        handler.sendEmptyMessageDelayed(1, 2000);
        handler.sendEmptyMessageDelayed(1, 3000);
        handler.sendEmptyMessageDelayed(1, 4000);
        handler.sendEmptyMessageDelayed(1, 5000);

    }

}
