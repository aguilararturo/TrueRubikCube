package com.example.arturoaguilar.truerubickcube;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.hardware.Camera.PictureCallback;

import com.example.arturoaguilar.truerubickcube.cubeSolver.Search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class camActivity extends Activity implements OnChangeEvent {
    Camera cam;
    FrameLayout camFrame;
    ShowCamera showCam;
    ProximitySensor sensor;
    TextView txtData;
    CubeMapper mapper;
    CubePatern cubePatern;
    Character[] cubeFaces = {'F', 'L', 'B', 'R', 'D', 'U'};
    String colorFaces = "";
    int faceIndex;
    ScaleGestureDetector SGD;
    cubeShape cube;
    float scale = 1f;
    int _xDelta = 0;
    int _yDelta = 0;
    ColorSender sender;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * this method check permission and return current state of permission need.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * this method request to permission asked.
     */
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
        } else {
            Log.i(TAG, "Requesting permission");
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkPermissions()) {
            requestPermissions();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_cam);

        camFrame = (FrameLayout) findViewById(R.id.camFrame);
        cam = Camera.open();
        txtData = (TextView) findViewById(R.id.txtError);

        cubePatern = new CubePatern(camFrame, this);


        if (cam != null) {
            showCam = new ShowCamera(this, cam);
            camFrame.addView(showCam);
        } else {
            getStorageImage();
        }
        mapper = new CubeMapper(this);

        sender = new ColorSender(camFrame, this);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();

            ViewGroup.LayoutParams layoutParams = cube.moveView.getLayoutParams();
            cube.moveView.setLayoutParams(layoutParams);

            txtData.setText(String.valueOf(scale));
            cube.displayShape(scale, cube.moveView.getLeft(), cube.moveView.getTop());
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        SGD.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void restartCamera() {
        if (cam != null) {
            cam.startPreview();
        }
    }

    protected void getStorageImage() {
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeFile(BitmapSaver.getPath());
            String mapResult = mapper.mapCube(rotateImage(image, 90));
            txtData.setText(mapResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensor.Register();
        }

        restartCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensor != null) {
            sensor.UnRegister();
        }
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bmp;

            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            BitmapSaver.saveBmp(rotateImage(bmp, 90));

            restartCamera();
        }
    };

    PictureCallback mTakeSnap = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bmp;
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            Bitmap newbmp = rotateImage(bmp, 90);

            int hn = newbmp.getHeight();
            int wn = newbmp.getWidth();

            int ch = camFrame.getHeight();
            int cw = camFrame.getWidth();

            int x, y, w,h;
            float hs, ws;

            int size = cube.getSize();


                hs = (float) hn / ch;
                ws = (float) wn / cw;
                y = (int) (cube.startY * hs);
                x = (int) (cube.startX * ws);
                w = (int) (size * ws);
                h = (int) (size * hs);

            try {

                Bitmap savebmp = Bitmap.createBitmap(newbmp, x, y, w, h);
                BitmapSaver.saveBmp(savebmp);

                String mapResult = mapper.mapCube(savebmp);

                txtData.setText(mapResult);

                colorFaces += cubePatern.draw(mapResult, cubeFaces[faceIndex].toString());

                faceIndex++;
                if (faceIndex >= cubeFaces.length) {

                    colorFaces.replace(colorFaces.charAt(5), cubeFaces[0]);
                    colorFaces.replace(colorFaces.charAt(14), cubeFaces[1]);
                    colorFaces.replace(colorFaces.charAt(23), cubeFaces[2]);
                    colorFaces.replace(colorFaces.charAt(32), cubeFaces[3]);
                    colorFaces.replace(colorFaces.charAt(41), cubeFaces[4]);
                    colorFaces.replace(colorFaces.charAt(50), cubeFaces[5]);

                    Search solution = new Search();
                    String result = solution.solution(colorFaces, 21, 100000000, 0, 0);
                    txtData.setText(result);

                    faceIndex = 0;
                    colorFaces = "";
                }

                restartCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void Onchange(float data) {
        if (data < 5) {
            try {
                //camFrame.setDrawingCacheEnabled(true);
                //Bitmap bitmap = Bitmap.createBitmap(camFrame.getDrawingCache());
                //BitmapSaver.saveBmp(bitmap);

                cam.takePicture(null, null, mTakeSnap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startSensor() {
        sensor = new ProximitySensor((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        sensor.addOnChangeListener(this);
        sensor.Register();
    }

    public void drawColorSquares() {

    }

    Handler handler = new Handler();
    int  colors[] = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.WHITE,
            Color.rgb(255, 165, 0)
    };

    int colorIndex = 0;
    int delay = 3000; //milliseconds

    public void startClick(View v) {
        cube = new cubeShape(camFrame, this);
        cube.displayShape(1, 0, 0);

        SGD = new ScaleGestureDetector(this, new ScaleListener());

        this.faceIndex = 0;

        drawColorSquares();
        restartCamera();
        startSensor();

        sender.displayColor(Color.BLACK);


        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                sender.displayColor(colors[colorIndex]);
                colorIndex++;
                if(colorIndex >= colors.length) {
                    colorIndex = 0;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
