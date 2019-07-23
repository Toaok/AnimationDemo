package indi.toaok.animation.core.view.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import indi.toaok.animation.R;

public class BaseAnimationActivity extends AppCompatActivity {


    AppCompatButton mAlpha;
    Animation alphaAnimation;

    AppCompatButton mScale;
    Animation scaleAnimation;

    AppCompatButton mRotate;
    Animation rotateAnimation;

    AppCompatButton mTranslate;
    Animation translateAnimation;

    AppCompatButton mSet;
    Animation setAnimation;

    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_animation);
        init();
    }


    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.base_alpha_anim);
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.base_scale_anim);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.base_rotate_anim);
        translateAnimation = AnimationUtils.loadAnimation(this, R.anim.base_translate_anim);
        setAnimation = AnimationUtils.loadAnimation(this, R.anim.base_set_anim);
    }

    private void initView() {
        mAlpha = findViewById(R.id.alpha);
        mScale = findViewById(R.id.scale);
        mRotate = findViewById(R.id.rotate);
        mTranslate = findViewById(R.id.translate);
        mSet = findViewById(R.id.set);

        mImage = findViewById(R.id.image);

    }

    private void initEvent() {

        mAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(alphaAnimation);
            }
        });

        mScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(scaleAnimation);
            }
        });

        mRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(rotateAnimation);
            }
        });

        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(translateAnimation);
            }
        });

        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(setAnimation);
            }
        });

    }

}
