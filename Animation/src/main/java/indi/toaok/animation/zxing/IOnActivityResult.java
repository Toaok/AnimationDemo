package indi.toaok.animation.zxing;

import android.content.Intent;
import android.support.annotation.Nullable;


/**
 * @author hpp
 * @version 1.0  2019/6/19.
 */
public interface IOnActivityResult {

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
