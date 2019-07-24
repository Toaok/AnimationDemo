package indi.toaok.animation;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MotionEvent;
import android.view.View;

import indi.toaok.animation.core.property.widget.PropertyAnimationWidget;
import indi.toaok.animation.core.property.widget.SwipeRefreshLayout;
import indi.toaok.animation.core.view.ViewAnimationWidget;
import indi.toaok.animation.utils.LogUtil;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout mRootLayout;

    AppCompatButton mXmlExample;
    AppCompatButton mCodeExample;

    ViewAnimationWidget mAnimationWidget;

    PropertyAnimationWidget mPropertyAnimationWidget;

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima_main);

        init();
    }

    private void init() {
        initView();
        initEvent();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {

        mRootLayout = findViewById(R.id.root_layout);
        CircularProgressDrawable progressDrawable;
        progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
        mRootLayout.setBackground(progressDrawable);
        mXmlExample = findViewById(R.id.xml_example);
        mCodeExample = findViewById(R.id.code_example);
        mAnimationWidget = findViewById(R.id.view_animation_widget);
        mPropertyAnimationWidget = findViewById(R.id.property_animation_widget);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);


    }

    private void initEvent() {

        mXmlExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleActivity.startActivity(MainActivity.this, 0);
            }
        });

        mCodeExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ExampleActivity.startActivity(MainActivity.this, 1);
            }
        });

        mAnimationWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimationWidget.startAnimation(3000);
            }
        });

        mPropertyAnimationWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyAnimationWidget.startAnimation(3000);
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPropertyAnimationWidget.startAnimation(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mPropertyAnimationWidget.startAnimation(1000);
                            }
                        });
                    }
                }).start();


            }
        });
    }

}
