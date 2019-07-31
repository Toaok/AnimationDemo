package indi.toaok.animation;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;

import indi.toaok.animation.core.property.widget.coustom.PropertyAnimationView;
import indi.toaok.animation.core.property.widget.refresh.SwipeRefreshLayout;
import indi.toaok.animation.core.view.ViewAnimationView;
import indi.toaok.animation.core.view.round.DefaultBackgroundDrawable;
import indi.toaok.animation.core.view.round.RollImageView;
import indi.toaok.animation.utils.LogUtil;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout mRootLayout;

    AppCompatButton mXmlExample;
    AppCompatButton mCodeExample;

    ViewAnimationView mAnimationWidget;

    PropertyAnimationView mPropertyAnimationView;

    SwipeRefreshLayout mSwipeRefreshLayout;

    RollImageView mRollImageView;

    ImageView mImageView;

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
        mXmlExample = findViewById(R.id.xml_example);
        mCodeExample = findViewById(R.id.code_example);
        mAnimationWidget = findViewById(R.id.view_animation_widget);
        mPropertyAnimationView = findViewById(R.id.property_animation_widget);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        mRollImageView = findViewById(R.id.roll_image_view);
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

        mPropertyAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyAnimationView.startAnimation(3000);
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPropertyAnimationView.startAnimation(2000);
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
                                mPropertyAnimationView.startAnimation(1000);
                            }
                        });
                    }
                }).start();


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mRollImageView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("onPause");
        mRollImageView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("onStop");
        mRollImageView.onStop();
    }
}
