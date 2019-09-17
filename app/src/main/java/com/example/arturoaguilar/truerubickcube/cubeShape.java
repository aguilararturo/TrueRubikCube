package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturo.aguilar on 6/11/2018.
 */
public class cubeShape {
    FrameLayout frame;
    Context context;
    List<View> views;
   public int startX = 0;
   public int startY = 0;
    public int h;
    public int w;
    float currentScale;

    public cubeShape(FrameLayout fra, Context cont) {
        this.frame = fra;
        this.context = cont;
        this.views = new ArrayList<>();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(60, 60);
        params.topMargin = 0;
        params.leftMargin = 0;

        ShapeDrawable sd = new ShapeDrawable(new RectShape());
        sd.getPaint().setColor(Color.RED);
        sd.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);

        sd.getPaint().setStrokeWidth(20);
    }

    public int getSize() {
        return (int) ((this.views.get(0).getWidth() * 3) * this.currentScale);
    }

    public boolean isPositionInsideCube(int left, int top) {
        return left > this.startY && left < this.w && top > this.startY && top < this.h;
    }

    public int getDifferenceX(int left) {
        return this.startX-left;

    }

    public int getDifferenceY(int top) {
        return this.startY - top;

    }

    public void displayShape(float scale, int startLeft, int startTop) {

        this.currentScale = scale;
        for (View fv : this.views) {
            this.frame.removeView(fv);
        }

        this.startX = startLeft;
        this.startY = startTop;

        //int a = this.frame.getWidth()/4;
        this.w = this.frame.getWidth() * (int) scale;
        this.h = this.frame.getHeight() * (int) scale;

        int wd = DisplayHelper.getScreenWidthInDPs(this.context);
        int hd = DisplayHelper.getScreenHeight(this.context);

        //int a = DisplayHelper.getScreenWidthInDPs(this.context) / 3;

        int a = (int) (this.frame.getWidth() * scale) / 3;

        int stroke = a / 4;
        int size = a;

        int left = startLeft;
        int top = startTop;

        for (int i = 1; i <= 9; i++) {
            ShapeDrawable sd = new ShapeDrawable(new RectShape());
            if (i % 2 == 0) {
                sd.getPaint().setColor(Color.GREEN);
            } else {
                sd.getPaint().setColor(Color.MAGENTA);
            }
            sd.getPaint().setStyle(Paint.Style.STROKE);
            sd.getPaint().setStrokeWidth(stroke);
            View shapeView = new View(this.context);
            shapeView.setBackground(sd);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
            params.topMargin = top;
            params.leftMargin = left;
            this.frame.addView(shapeView, params);
            this.views.add(shapeView);

            if ((i % 3) == 0) {
                //top = top + size -(stroke/2);
                top = top + size;
                left = startLeft;
            } else {
                //left = left + (size - (stroke/2));
                left = left + size;
            }
        }
    }

    public void DrawCube(int x, int y, String map) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ShapeDrawable sd = new ShapeDrawable(new RectShape());
                sd.getPaint().setStyle(Paint.Style.FILL);
                sd.getPaint().setColor(Color.GREEN);

            }
        }
    }
}
