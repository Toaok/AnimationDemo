package indi.toaok.animation.core.view.code;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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
    AnimationSet setAnimation;

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
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        scaleAnimation = new ScaleAnimation(0.0f, 1.8f, 0.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.50f, Animation.RELATIVE_TO_SELF, 0.50f);
        scaleAnimation.setDuration(3000);
        rotateAnimation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.50f, Animation.RELATIVE_TO_SELF, 0.50f);
        rotateAnimation.setDuration(3000);
        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(3000);
        setAnimation = new AnimationSet(true);
        setAnimation.addAnimation(alphaAnimation);
        setAnimation.addAnimation(scaleAnimation);
        setAnimation.addAnimation(rotateAnimation);
        setAnimation.addAnimation(translateAnimation);
        setAnimation.setDuration(3000);
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
