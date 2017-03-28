package com.example.hilal.myqrcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.journeyapps.barcodescanner.ViewfinderView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ruslan on 28.03.2017.
 */

public class TransparentSurf extends Activity {


    SurfaceView cameraPreview;
    Camera camera;
    ViewfinderView vf;
    int b = 0;
    ImageView scan_img;
    TextView tv;
    Canvas canvas;
    String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cameraPreview = (SurfaceView) findViewById(R.id.surfaceCam);
        cameraPreview.setZOrderMediaOverlay(true);
        scan_img = (ImageView) findViewById(R.id.scan_img);

        vf = (ViewfinderView) findViewById(R.id.viewfinder_view);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        tv = (TextView) findViewById(R.id.res_txt);
        tv.setText((String) b.get("res"));
        tv.setTextColor(Color.GREEN);
        path =(String )b.get("path");
        //setResult((String )b.get("path"),(String )b.get("res"));
        creatCameraSource();
    }


    private void setResult(String path, String res) {
        File imgFile = new File(path);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            scan_img.setImageBitmap(myBitmap);

        }
        tv.setText(res);
    }

    public void shadow(View view) {
        if (b == 0) {
            //  vf.setVisibility(View.VISIBLE);
            b++;
        } else {
            //  vf.setVisibility(View.GONE);
            b--;
        }
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

    private void creatCameraSource() {

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {

                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();

                    File imgFile = new File(path);
                    canvas=null;
                    canvas=surfaceHolder.lockCanvas(null);
                    if (imgFile.exists()) {

                        Bitmap _scratch = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                       canvas.drawColor(Color.BLACK);
                        canvas.drawBitmap(_scratch, 10, 10, null);
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {


            }

        });
    }


}
