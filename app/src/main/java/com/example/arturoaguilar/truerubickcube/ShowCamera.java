package com.example.arturoaguilar.truerubickcube;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by arturo.aguilar on 6/8/2018.
 */
public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {
    Camera cam;
    SurfaceHolder holder;
    Context context;


    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.context = context;
        cam = camera;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = cam.getParameters();

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            params.set("orientation","portrait");
            cam.setDisplayOrientation(90);
            params.setRotation(90);
        }

        Camera.Size pictureSize=getScreenResolution(params);

        if (pictureSize != null) {
            params.setPictureSize(pictureSize.width,
                    pictureSize.height);
        }

        cam.setParameters(params);
        try {
            cam.setPreviewDisplay(holder);
            cam.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Camera.Size getScreenResolution(Camera.Parameters params){
        int height = DisplayHelper.getScreenHeightInDPs(this.context);

        Camera.Size result = getSmallestPictureSize(params);
        for(Camera.Size size : params.getSupportedPictureSizes()) {
            if(size.height<height) {
                break;
            }
            else{
                result = size;
            }

        }

        return result;
    }

    private Camera.Size getSmallestPictureSize(Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (result == null) {
                result=size;
            }
            else {
                int resultArea=result.width * result.height;
                int newArea=size.width * size.height;

                if (newArea < resultArea) {
                    result=size;
                }
            }
        }

        return(result);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
