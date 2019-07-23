package indi.toaok.animation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_TYPE = "extra_type";

    AppCompatButton mBaseAnimation;
    AppCompatButton mInterpolatorAnimation;


    int type;

    /**
     * @param contextPackage
     * @param type           0:xml;1:code
     */
    public static void startActivity(Context contextPackage, int type) {
        Intent intent = new Intent();
        intent.setClass(contextPackage, ExampleActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        contextPackage.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        init();
    }

    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mBaseAnimation = findViewById(R.id.base_animation);
        mInterpolatorAnimation = findViewById(R.id.interpolator_animation);
    }

    private void initData() {
        type = getIntent().getIntExtra(EXTRA_TYPE, 0);
    }

    private void initEvent() {

        mBaseAnimation.setOnClickListener(this);
        mInterpolatorAnimation.setOnClickListener(this);
    }


    private void xmlEventHandle(View view) {
        int i = view.getId();
        if (i == R.id.base_animation) {
            startActivity(new Intent(ExampleActivity.this, indi.toaok.animation.core.view.xml.BaseAnimationActivity.class));
        } else if (i == R.id.interpolator_animation) {
            startActivity(new Intent(ExampleActivity.this, indi.toaok.animation.core.view.xml.InterpolatorAnimationActivity.class));
        }
    }

    private void codeEventHandle(View view) {
        int i = view.getId();
        if (i == R.id.base_animation) {
            startActivity(new Intent(ExampleActivity.this, indi.toaok.animation.core.view.code.BaseAnimationActivity.class));
        } else if (i == R.id.interpolator_animation) {
            startActivity(new Intent(ExampleActivity.this, indi.toaok.animation.core.view.code.InterpolatorAnimationActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        if (type == 0) {
            xmlEventHandle(view);
        } else {
            codeEventHandle(view);
        }
    }
}
