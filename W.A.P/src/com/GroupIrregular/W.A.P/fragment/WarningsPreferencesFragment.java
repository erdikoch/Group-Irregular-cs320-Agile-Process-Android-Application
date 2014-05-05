package com.groupIrregular.wap.fragment;

import com.groupIrregular.wap.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class WarningsPreferencesFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.warnings_preferences);
	}

}
