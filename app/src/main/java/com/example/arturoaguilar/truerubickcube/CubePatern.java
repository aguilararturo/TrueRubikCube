package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.FrameLayout;

public class CubePatern {
    FrameLayout frame;
    Context context;

    public CubePatern(FrameLayout frame, Context context) {
        this.frame = frame;
        this.context = context;
    }

    public boolean setColor(String color, ShapeDrawable sd){
        switch (color) {
            case "R":
                sd.getPaint().setColor(Color.RED);
                break;
            case "G":
                sd.getPaint().setColor(Color.GREEN);
                break;
            case "B":
                sd.getPaint().setColor(Color.BLUE);
                break;
            case "Y":
                sd.getPaint().setColor(Color.YELLOW);
                break;
            case "W":
                sd.getPaint().setColor(Color.WHITE);
                break;
            case "O":
                sd.getPaint().setColor(Color.parseColor("#FFA500"));
                break;
            case "":
                return false;
            default:
                sd.getPaint().setColor(Color.BLACK);
                break;
        }

        return true;
    }

    public Position getPosition(String face, Position initialPos, int size) {
        Position result=new Position();
        switch (face){
            case "F" :
                result.Top = initialPos.Top + size;
                result.Left = initialPos.Left + size;
                break;
            case "L" :
                result.Top = initialPos.Top + size;
                result.Left = initialPos.Left;
                break;
            case "R" :
                result.Top = initialPos.Top+size ;
                result.Left = initialPos.Left+ (size*2);
                break;
            case "B" :
                result.Top = initialPos.Top + size;
                result.Left = initialPos.Left + (size*3);
                break;
            case "D" :
                result.Top = initialPos.Top + (size * 2);
                result.Left = initialPos.Left + size;
                break;
            case "U" :
                result.Top = initialPos.Top;
                result.Left = initialPos.Left + size;
                break;
        }

        return  result;
    }

    public String draw(String colors, String face) {
        String[] faceMap = colors.split("\\-");
        String result = "";

        int w = this.frame.getWidth();
        int h = this.frame.getHeight();

        int max = h - w;
        int size = max / 5;
        int initialLeft = 0;

        int pSize = size / 3;
        int top = w;
        int l = initialLeft;

        Position initial = new Position();
        initial.Left = initialLeft;
        initial.Top = top;

        initial = getPosition(face, initial, size);

        l = initial.Left;
        top = initial.Top;

        for (int i = 0; i < faceMap.length; i++) {
            ShapeDrawable sd = new ShapeDrawable(new RectShape());
            ShapeDrawable borderShape = new ShapeDrawable(new RectShape());

            if (!setColor(faceMap[i], sd)) {
                continue;
            }

            result += faceMap[i];

            sd.getPaint().setStyle(Paint.Style.FILL);

            borderShape.getPaint().setStyle(Paint.Style.STROKE);
            borderShape.getPaint().setColor(Color.BLACK);
            borderShape.getPaint().setStrokeWidth(pSize / 4);

            View shapeView = new View(this.context);
            shapeView.setBackground(sd);

            View strokeView = new View(this.context);
            strokeView.setBackground(borderShape);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(pSize, pSize);
            params.topMargin = top;
            params.leftMargin = l;

            this.frame.addView(shapeView, params);
            this.frame.addView(strokeView, params);

            if (i % 3 == 0) {
                l = initial.Left;
                top = top + pSize;
            } else {
                l = l + pSize;
            }
        }

        return result;
    }
}
