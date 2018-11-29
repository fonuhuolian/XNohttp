package org.fonuhuolian.nohttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.fonuhuolian.xnohttp.base.XLoadingStyle;
import org.fonuhuolian.xnohttp.callback.XLoadingNoCancleDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new XLoadingNoCancleDialog(this, XLoadingStyle.YOCYCLE).show();
    }
}
