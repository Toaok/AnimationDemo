package indi.toaok.animation;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import indi.toaok.animation.code.widget.PropertyAnimationWidget;
import indi.toaok.animation.code.widget.ViewAnimationWidget;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout mRootLayout;

    AppCompatButton mXmlExample;
    AppCompatButton mCodeExample;

    ViewAnimationWidget mAnimationWidget;
    PropertyAnimationWidget mPropertyAnimationWidget;

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

    private void initView() {

        mRootLayout = findViewById(R.id.root_layout);
        mXmlExample = findViewById(R.id.xml_example);
        mCodeExample = findViewById(R.id.code_example);
        mAnimationWidget = findViewById(R.id.view_animation_widget);
        mPropertyAnimationWidget = findViewById(R.id.property_animation_widget);

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
    }

}
