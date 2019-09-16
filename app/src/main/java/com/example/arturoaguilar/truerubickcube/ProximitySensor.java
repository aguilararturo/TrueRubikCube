package com.example.arturoaguilar.truerubickcube;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by arturo.aguilar on 6/8/2018.
 */
public class ProximitySensor implements SensorEventListener{

    SensorManager mngSen;
    Sensor proxSen;
    OnChangeEvent onChangeSensor;

    public ProximitySensor(SensorManager mgn) {
        mngSen = mgn;
        proxSen = mgn.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    public void Register(){
        mngSen.registerListener(this, proxSen, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void UnRegister(){
        mngSen.unregisterListener(this);
    }

    public void addOnChangeListener(OnChangeEvent event)
    {
        onChangeSensor = event;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY ){
            if(onChangeSensor !=null)
            {
                onChangeSensor.Onchange(event.values[0]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
