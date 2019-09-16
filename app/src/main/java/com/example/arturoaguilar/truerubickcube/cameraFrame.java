package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class cameraFrame extends FrameLayout {

    public cameraFrame(@NonNull Context context) {
        super(context);
    }

    public cameraFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public cameraFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public cameraFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
    }
}
