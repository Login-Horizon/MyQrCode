package com.example.hilal.myqrcode;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
        setContentView(R.layout.smallscan);
        return (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }


}
