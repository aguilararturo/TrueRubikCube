package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arturo.aguilar on 6/11/2018.
 */
public class CubeMapper {
    Context context;
    Bitmap img;
    CubeLog logger;
    public CubeMapper(Context cont){
        context = cont;
        logger = new CubeLog(cont);
    }

    public String mapCube(Bitmap image) {
        logger.clear();
        int squareSize = image.getHeight() / 3;

        int ysize = image.getHeight()/3;
        int xsize = image.getWidth()/3;

        int h = image.getHeight();
        int w = image.getWidth();

        img = image.copy(image.getConfig(), true);

        int a = squareSize/2;

        int ay = ysize /2;
        int ax = xsize /2;

        String result = "";
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {

                int xPos = ax + (i * xsize);
                int yPos = ay + (j * ysize);

                result += "-" + getColor(image, xPos, yPos).charAt(0);
            }
        }
        BitmapSaver.saveBmp(img);
        return result;
    }

    public String getColor(Bitmap image, int xPosition, int yPosition) {
        String result = "";
        Map<String, Integer> map = new HashMap<String, Integer>();

        int iter = 5;
        int x = xPosition;
        int y = yPosition;
        for (int i = 0; i < iter; i++) {
            y = yPosition + i;
            for (int j = 0; j < iter; j++) {
                x = xPosition + j;
                 int color = image.getPixel(x, y);
                img.setPixel(x, y, Color.rgb(0, 255, 255));

                float[] hue = getHue(color);

                result = getPixelColor(hue);
                logger.log("\\n Color:" + result + " x:" + x + " y:" + y + " colorHue:" + hue[0] + " colorSat:" + hue[1]);
                Integer count = map.get(result);
                if (count == null) {
                    count = 0;
                }
                count++;
                map.put(result, count);
            }
        }

        int max = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                result = entry.getKey();
            }
        }

        return result;
    }

    public float[] getHue(int colorNumber) {
        int r = Color.red(colorNumber);
        int g = Color.green(colorNumber);
        int b = Color.blue(colorNumber);

        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);

        return hsv;
    }

    public String getPixelColor(float[] hsv) {
        String result = "N/C";

        float hue = hsv[0];
        float sat = hsv[1];
        if(hue>240 && hue<330){
            return "WHITE";
        }

        if(hue > 220 && hue<245) {
            if(sat< 0.5) {
                return  "WHITE";
            } else
            {
                return  "BLUE";
            }
        }

        if (hue > 195 && hue < 220) {
            result = "BLUE";
        }

        if (hue > 20 && hue < 35) {
            result = "ORANGE";
        }

        if ((hue > 0 && hue < 15) || (hue > 330)) {
            result = "RED";
        }

        if (hue > 45 && hue < 70) {
            result = "YELLOW";
        }

        if (hue > 90 && hue < 150) {
            result = "GREEN";
        }

        if (result == "") {
            result = Float.toString(hue);
        }

        return result;
    }
}
