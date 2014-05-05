package com.groupIrregular.wap.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.groupIrregular.wap.R;

public class TonesPreferencesFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tones_preferences);
	}

}
