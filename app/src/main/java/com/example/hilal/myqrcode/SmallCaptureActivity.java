package com.example.hilal.myqrcode;

import android.widget.ImageView;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

/**
 * Created by Ruslan on 27.03.2017.
 */

public class SmallCaptureActivity extends CaptureActivity {
    ImageView imageView;
    @Override
    protected CompoundBarcodeView initializeContent() {
        setContentView(R.layout.scaning_layout);
        return (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }


}
