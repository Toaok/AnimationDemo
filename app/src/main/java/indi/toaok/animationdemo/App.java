package indi.toaok.animationdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author Toaok
 * @version 1.0  2019/7/25.
 */
public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
