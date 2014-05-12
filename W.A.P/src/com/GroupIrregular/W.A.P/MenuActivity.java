package com.groupIrregular.wap;

import com.groupIrregular.wap.SettingsActivity;
import com.groupIrregular.wap.MenuActivity;
import com.groupIrregular.wap.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends BaseActivity {
	
	private ImageButton settingsImageButton;
	private ImageButton profileImageButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        initLayoutWidgets();
    }
	
	private void initLayoutWidgets() {
		
		settingsImageButton = (ImageButton) findViewById(R.id.settingsImageButton);
		settingsImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
            	startActivity(intent);
            }
        });
		
		profileImageButton = (ImageButton) findViewById(R.id.profileImageButton);
		profileImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
            	startActivity(intent);
            }
        });
		
	}

}
