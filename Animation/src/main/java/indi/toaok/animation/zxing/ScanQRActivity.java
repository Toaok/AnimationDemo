package indi.toaok.animation.zxing;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.DecodeHintManager;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoderFactory;
import com.journeyapps.barcodescanner.DecoderResultPointCallback;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import indi.toaok.animation.R;

public class ScanQRActivity extends AppCompatActivity {

    public static final int SELECT_IMAGE_REQUEST_CODE = 0x11;
    private DecoratedBarcodeView mDecoratedBarcode;
    private CaptureManager mCaptureManager;

    private DecoderFactory decoderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        init(savedInstanceState);
    }


    private void init(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左侧按钮：可见+可用+更换图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.mipmap.back_white);
        }
        mDecoratedBarcode = findViewById(R.id.decorated_barcode_view);
        mCaptureManager = new CaptureManager(this, mDecoratedBarcode);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.decode();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    private View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDecoratedBarcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    /**
     * 复写：左侧按钮点击动作
     * android.R.id.home
     * v7 actionbar back event
     * 注意：如果复写了onOptionsItemSelected方法，则onSupportNavigateUp无用
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_album) {
            openSysAlbum();
        }
        return true;

    }

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        initializeFromIntent(getIntent());
        Intent innerIntent = new Intent();
        innerIntent.setAction(Intent.ACTION_PICK);
        innerIntent.setType("image/*");
        startActivityForResult(innerIntent, SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data.getData() != null) {
                    DecoderResultPointCallback callback = new DecoderResultPointCallback();
                    Map<DecodeHintType, Object> hints = new HashMap<>();
                    hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, callback);
                    new DecodeImgThread(decoderFactory.createDecoder(hints), ImageUtil.getImageAbsolutePath(this, data.getData()), new DecodeImgCallback() {
                        @Override
                        public void onImageDecodeSuccess(Result result) {
                            ToastUtils.showShort(result.getText());
                        }

                        @Override
                        public void onImageDecodeFailed() {

                        }
                    }).start();
                }
                break;
        }
    }

    public void initializeFromIntent(Intent intent) {
        // Scan the formats the intent requested, and return the result to the calling activity.
        Set<BarcodeFormat> decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
        Map<DecodeHintType, Object> decodeHints = DecodeHintManager.parseDecodeHints(intent);

        // Check what type of scan. Default: normal scan
        int scanType = intent.getIntExtra(Intents.Scan.SCAN_TYPE, 0);

        String characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);

        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(decodeHints);

        decoderFactory = new DefaultDecoderFactory(decodeFormats, decodeHints, characterSet, scanType);
    }

}
