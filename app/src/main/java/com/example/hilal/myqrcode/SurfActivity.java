package com.example.hilal.myqrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by Ruslan on 29.03.2017.
 */

public class SurfActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView surfaceView;
    Camera camera;
    ImageView scan_img;
    String path = "";
    TextView bar_tv;
    String barcode="";
    NumberPicker np;
    int pval=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_preview);
        scan_img = (ImageView) findViewById(R.id.surf_img);
        scan_img.setImageResource(R.mipmap.ic_launcher_round);
        scan_img.setScaleType(ImageView.ScaleType.FIT_XY);
        bar_tv = (TextView) findViewById(R.id.res_txt);
        bar_tv.setTextColor(Color.GREEN);
        np = (NumberPicker)findViewById(R.id.numberPicker1);

        np.setMinValue(0);
        np.setMaxValue(10);
        np.setValue(1);
        np.setWrapSelectorWheel(false);
        setNumberPickerTextColor(np,Color.GREEN);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        path = (String) b.get("path");
        barcode = (String) b.get("res");
        setResult(path, barcode);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceCam);
        surfaceView.setZOrderMediaOverlay(true);
        surfaceView.getHolder().addCallback(this);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                pval = i1;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(0);
        camera.setDisplayOrientation(90);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.release();
        camera = null;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void setResult(String path, String res) {
        File imgFile = new File(path);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            scan_img.setImageBitmap(myBitmap);

        }
        bar_tv.setText(res);
        Intent intent = new Intent();
        intent.putExtra("val",pval);
    }


}
