package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class ColorSender {
    Context context;
    FrameLayout frame;
    View colorView;

    public ColorSender(FrameLayout fra, Context cont) {
        context = cont;
        frame = fra;


    }

    public void displayColor(int color) {

        if(colorView == null) {
            createColorView(color);
        } else  {
            ShapeDrawable sd = new ShapeDrawable(new RectShape());
            sd.getPaint().setColor(color);
            sd.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);

            sd.getPaint().setStrokeWidth(20);

            colorView.setBackground(sd);
        }
    }

    private void createColorView(int color) {
        int size = 350;
        this.colorView = new View(this.context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
        params.topMargin = frame.getHeight() - size;
        params.leftMargin = frame.getWidth() - size;

        ShapeDrawable sd = new ShapeDrawable(new RectShape());
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);

        sd.getPaint().setStrokeWidth(20);

        colorView.setBackground(sd);

        this.frame.addView(colorView, params);

        colorView.setOnTouchListener(new View.OnTouchListener() {
            int xDelta = 0;
            int yDelta = 0;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        xDelta = x - view.getLeft();
                        yDelta = y - view.getTop();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:


                        FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
                        lParams.topMargin = y-yDelta;
                        lParams.leftMargin = x-xDelta;

                        view.setLayoutParams(lParams);
                        break;
                }

                return true;
            }
        });
    }
}
