package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by arturo.aguilar on 6/12/2018.
 */
public class CubeLog {
    Context context;
    String path;

    public  CubeLog(Context cont) {
        context = cont;
        path = Environment.getExternalStorageDirectory().toString();
        String fileName = "cubeLog.txt";
        path = path + File.separator + "cubePics"+File.separator+fileName;

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (IOException e) {
            Log.e("Exception", "Unable to create file " +path +" : " + e.toString());
        }
    }

    public void  clear() {
        try {
            File file=new File(path);
            FileWriter writer = new FileWriter(file,false);
            writer.write("");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void log(String text) {
        try {
            File file=new File(path);
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.newLine();
            bw.append(text);

            bw.flush();
            bw.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
