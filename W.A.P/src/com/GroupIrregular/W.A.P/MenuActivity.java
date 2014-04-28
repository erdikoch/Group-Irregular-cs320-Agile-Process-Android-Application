package com.kodlab.kimnerede;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MenuActivity extends BaseActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        //ActionBar actionBar = getActionBar();
        //actionBar.setTitle(R.string.menu_action_bar_title);

    }

}
