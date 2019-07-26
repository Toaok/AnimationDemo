package indi.toaok.animation;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import indi.toaok.animation.core.property.widget.coustom.PropertyAnimationView;
import indi.toaok.animation.core.property.widget.refresh.SwipeRefreshLayout;
import indi.toaok.animation.core.view.ViewAnimationView;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout mRootLayout;

    AppCompatButton mXmlExample;
    AppCompatButton mCodeExample;

    ViewAnimationView mAnimationWidget;

    PropertyAnimationView mPropertyAnimationView;

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
        mPropertyAnimationView = findViewById(R.id.property_animation_widget);

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

}
