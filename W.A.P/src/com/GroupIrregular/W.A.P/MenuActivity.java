package com.groupIrregular.wap;

import com.groupIrregular.wap.MenuActivity;
import com.groupIrregular.wap.SettingsActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends BaseActivity {

	private ImageButton ayarlarImageButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		// ActionBar actionBar = getActionBar();
		// actionBar.setTitle(R.string.menu_action_bar_title);

		initLayoutWidgets();
	}

	private void initLayoutWidgets() {

		ayarlarImageButton = (ImageButton) findViewById(R.id.ayarlarImageButton);
		ayarlarImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						SettingsActivity.class);
				startActivity(intent);
			}
		});

	}

}
