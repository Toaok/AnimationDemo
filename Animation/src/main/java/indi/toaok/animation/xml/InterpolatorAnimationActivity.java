package indi.toaok.animation.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import indi.toaok.animation.R;

public class InterpolatorAnimationActivity extends AppCompatActivity {

    AppCompatButton mInterpolatorAccelerateDecelerate;
    Animation accelerateDecelerateAnimation;

    AppCompatButton mInterpolatorAccelerate;
    Animation accelerateAnimation;

    AppCompatButton mInterpolatorAnticipate;
    Animation anticipateAnimation;

    AppCompatButton mInterpolatorAnticipateOvershoot;
    Animation anticipateOvershootAnimation;

    AppCompatButton mInterpolatorBounce;
    Animation bounceAnimation;

    AppCompatButton mInterpolatorCycle;
    Animation cycleAnimation;

    AppCompatButton mInterpolatorDecelerate;
    Animation decelerateAnimation;

    AppCompatButton mInterpolatorLinear;
    Animation linearAnimation;

    AppCompatButton mInterpolatorOvershoot;
    Animation overshootAnimation;

    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator_animation);
        init();
    }


    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        accelerateDecelerateAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_decelerate_anim);
        accelerateAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_anim);
        anticipateAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_anticipate_anim);
        anticipateOvershootAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_anticipate_overshoot_anim);
        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_bounce_anim);
        cycleAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_cycle_anim);
        decelerateAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_decelerate_anim);
        linearAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_linear_anim);
        overshootAnimation = AnimationUtils.loadAnimation(this, R.anim.interpolator_overshoot_anim);
    }

    private void initView() {
        mInterpolatorAccelerateDecelerate = findViewById(R.id.interpolator_accelerate_decelerate);
        mInterpolatorAccelerate = findViewById(R.id.interpolator_accelerate);
        mInterpolatorAnticipate = findViewById(R.id.interpolator_anticipate);
        mInterpolatorAnticipateOvershoot = findViewById(R.id.interpolator_anticipate_overshoot);
        mInterpolatorBounce = findViewById(R.id.interpolator_bounce);
        mInterpolatorCycle = findViewById(R.id.interpolator_cycle);
        mInterpolatorDecelerate = findViewById(R.id.interpolator_decelerate);
        mInterpolatorLinear = findViewById(R.id.interpolator_linear);
        mInterpolatorOvershoot = findViewById(R.id.interpolator_overshoot);

        mImage = findViewById(R.id.image);

    }

    private void initEvent() {

        mInterpolatorAccelerateDecelerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(accelerateDecelerateAnimation);
            }
        });

        mInterpolatorAccelerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(accelerateAnimation);
            }
        });

        mInterpolatorAnticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(anticipateAnimation);
            }
        });

        mInterpolatorAnticipateOvershoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(anticipateOvershootAnimation);
            }
        });

        mInterpolatorBounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(bounceAnimation);
            }
        });

        mInterpolatorCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(cycleAnimation);
            }
        });

        mInterpolatorDecelerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(decelerateAnimation);
            }
        });

        mInterpolatorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(linearAnimation);
            }
        });

        mInterpolatorOvershoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(overshootAnimation);
            }
        });

    }

}
