package com.example.arturoaguilar.truerubickcube;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by arturo.aguilar on 6/11/2018.
 */
public class BitmapSaver {
    public static String getPath() {
        String path = Environment.getExternalStorageDirectory().toString();
        String fileName = "cubeimg.png";
        path = path + File.separator + "cubePics";
        File folder = new File(path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            path = path + File.separator + fileName;
        } else {
            path = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;
        }

        return path;
    }

    public static void saveBmp(Bitmap bmp) {
        String filePath = getPath();

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
