package org.fonuhuolian.nohttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.fonuhuolian.xnohttp.base.XLoadingStyle;
import org.fonuhuolian.xnohttp.callback.XLoadingNoCancleDialog;

public class MainActivity extends AppCompatActivity {

    private XLoadingNoCancleDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new XLoadingNoCancleDialog(this, XLoadingStyle.DOUBLE_BALL);

    }

    public void click(View view) {
        dialog.show();
    }
}
