package com.groupIrregular.wap;

import com.groupIrregular.wap.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends BaseActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        skipSplashScreen();
    }

    private void skipSplashScreen() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ImageView logo = (ImageView) findViewById(R.id.logoImageView);
        anim.reset();
        logo.clearAnimation();
        logo.startAnimation(anim);
        
        anim.setAnimationListener(new AnimationListener() {
        	
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
				startActivity(intent);
				SplashScreenActivity.this.finish();
            }

			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			
        });

    }

}
