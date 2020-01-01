package com.zbk.animationapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private ImageView imgStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnScale = findViewById(R.id.btnScale);
        Button btnFade = findViewById(R.id.btnFade);
        Button btnRotate = findViewById(R.id.btnRotate);
        Button btnBgColor = findViewById(R.id.btnBgcolor);
        Button btnTranslate = findViewById(R.id.btnTranslate);
        Button btnShower = findViewById(R.id.btnShower);
        imgStar = findViewById(R.id.imgStar);

        btnBgColor.setOnClickListener(this);
        btnShower.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnFade.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnTranslate.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRotate:
                rotater();
                break;
            case R.id.btnScale:
                scaler();
                break;
            case R.id.btnFade:
                fader();
                break;
            case R.id.btnShower:
                showShower();
                break;
            case R.id.btnBgcolor:
                changeColor();
                break;
            case R.id.btnTranslate:
                mover();
                break;
        }
    }

    private void rotater() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgStar,
                View.ROTATION_Y, 0, 360f);
        animator.setDuration(500);
        animator.start();
    }

    private void scaler() {
        PropertyValuesHolder scaley = PropertyValuesHolder.ofFloat(View.SCALE_Y,
                4f, 0, 2f);
        PropertyValuesHolder scalex = PropertyValuesHolder.ofFloat(View.SCALE_X,
                4f, 0, 2f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imgStar,
                scaley, scalex);
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    private void fader() {
        ObjectAnimator.ofFloat(imgStar, View.ALPHA, 1f, 0f, 0.5f)
                .setDuration(500)
                .start();
    }

    private void mover() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgStar, View.TRANSLATION_X,
                300);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    private void changeColor() {
        ConstraintLayout parent = (ConstraintLayout) imgStar.getParent();
        ObjectAnimator animator = ObjectAnimator.ofArgb(parent,
                "backgroundColor",
                Color.BLACK, Color.YELLOW);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(2000);
        animator.start();
    }

    private void showShower() {
        // Create a new star view in a random X position above the container.
        // Make it rotateButton about its center as it falls to the bottom.

        // Local variables we'll need in the code below
        final ConstraintLayout container = (ConstraintLayout) imgStar.getParent();
        int containerW = container.getWidth();
        int containerH = container.getHeight();
        float starW = imgStar.getWidth();
        float starH = imgStar.getHeight();

        // Create the new star (an ImageView holding our drawable) and add it to the container
        final AppCompatImageView newStar = new AppCompatImageView(this);
        newStar.setImageResource(android.R.drawable.btn_star_big_on);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        newStar.setLayoutParams(params);
        container.addView(newStar);

        // Scale the view randomly between 10-160% of its default size
        newStar.setScaleX((float) Math.random() * 1.5f + .1f);
        newStar.setScaleY(newStar.getScaleX());
        starW *= newStar.getScaleX();
        starH *= newStar.getScaleY();

        // Position the view at a random place between the left and right edges of the container
        newStar.setTranslationX((float) Math.random() * containerW - starW / 2);

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel
        ObjectAnimator mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH);
        mover.setInterpolator(new AccelerateInterpolator(1f));

        // Create an animator to rotateButton the view around its center up to three times
        ObjectAnimator rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, ((float) Math.random() * 1080));
        rotator.setInterpolator(new LinearInterpolator());

        // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
        // of a half-second to two seconds
        AnimatorSet set = new AnimatorSet();
        set.playTogether(mover, rotator);
        set.setDuration((long) (Math.random() * 1500 + 500));


        // When the animation is done, remove the created view from the container
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(newStar);
            }
        });

        // Start the animation
        set.start();
    }

    @Override
    public boolean onLongClick(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgStar,
                View.TRANSLATION_X,
                -150);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        return true;
    }
}
