package indi.toaok.animation.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.DecodeHintManager;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.Decoder;
import com.journeyapps.barcodescanner.DecoderFactory;
import com.journeyapps.barcodescanner.DecoderResultPointCallback;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by yzq on 2017/10/17.
 * <p>
 * 解析二维码图片
 * 解析是耗时操作，要放在子线程
 */

public class DecodeImgThread extends Thread {


    /*图片路径*/
    private String imgPath;
    /*回调*/
    private DecodeImgCallback callback;

    private Decoder decoder;

    public DecodeImgThread(Decoder decoder, String imgPath, DecodeImgCallback callback) {
        this.decoder = decoder;
        this.imgPath = imgPath;
        this.callback = callback;
    }


    @Override
    public void run() {
        super.run();

        if (TextUtils.isEmpty(imgPath) || callback == null) {
            return;
        }

        /**
         * 对图片进行裁剪，防止oom
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(imgPath, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 400);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(imgPath, options);

        //MultiFormatReader multiFormatReader = new MultiFormatReader();

        // 开始对图像资源解码

        Result result = null;
        try {
            int[] pixels = new int[scanBitmap.getWidth() * scanBitmap.getHeight()];
            scanBitmap.getPixels(pixels, 0, scanBitmap.getWidth(), 0, 0, scanBitmap.getWidth(), scanBitmap.getHeight());
            result = decoder.decode(new RGBLuminanceSource(scanBitmap.getWidth(), scanBitmap.getHeight(), pixels));
            if (result != null) {
                Log.i("解析结果", result.getText());
            } else {
                Log.i("解析结果", "解析失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  Log.i("解析的图片结果","失败");
        }

        if (result != null) {
            callback.onImageDecodeSuccess(result);
        } else {
            callback.onImageDecodeFailed();
        }
    }


}
