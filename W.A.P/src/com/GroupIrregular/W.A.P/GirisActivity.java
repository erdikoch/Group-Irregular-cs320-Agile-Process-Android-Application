package com.kodlab.kimnerede;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class GirisActivity extends BaseActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);

        menuEkraninaGec();
    }

    private void menuEkraninaGec() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ImageView girisLogo = (ImageView) findViewById(R.id.girisLogoImageView);
        anim.reset();
        girisLogo.clearAnimation();
        girisLogo.startAnimation(anim);
        
        anim.setAnimationListener(new AnimationListener() {
        	
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(GirisActivity.this, MenuActivity.class);
				startActivity(intent);
				GirisActivity.this.finish();
            }

			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			
        });

    }

}
