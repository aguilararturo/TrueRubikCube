package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class frameListener implements View.OnLayoutChangeListener {
    FrameLayout frame;
    Context context;
    public frameListener(FrameLayout frame, Context context) {
        this.frame = frame;
        this.context = context;
    }
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        cubeShape cube = new cubeShape(this.frame, this.context);
        cube.displayShape(1,0,0);
    }
}
