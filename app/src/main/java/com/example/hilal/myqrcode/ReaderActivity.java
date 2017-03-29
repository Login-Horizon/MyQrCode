package  com.example.hilal.myqrcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

public class ReaderActivity extends AppCompatActivity {

    private Button scan_btn;
    private ImageView scan_img;
    NumberPicker npp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        scan_img = (ImageView) findViewById(R.id.scan_img);
        final Activity activity = this;
        npp = (NumberPicker)findViewById(R.id.numberPicker1);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.addExtra("SCAN_WIDTH",50);
                integrator.addExtra("SCAN_HEIGHT",50);
                integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
                integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(SmallCaptureActivity.class);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    private void setImage(String path){
        File imgFile = new  File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            scan_img.setImageBitmap(myBitmap);

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(this, result.getBarcodeImagePath(),Toast.LENGTH_LONG).show();
                setImage(result.getBarcodeImagePath());
                Intent io=new Intent(this, SurfActivity.class);
                io.putExtra("path", result.getBarcodeImagePath());
                io.putExtra("res", result.getContents());
                startActivity(io);



            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
