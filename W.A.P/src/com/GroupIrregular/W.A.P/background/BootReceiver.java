package com.groupIrregular.wap.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	
	private static final String TAG = "BootReceiver";
	private static final long TIME_PERIOD = 60 * 1000;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(TAG, "Started");
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		boolean autoStart = preferences.getBoolean("PREF_AUTO_START", true);
		
		Log.i(TAG, "Auto-Start : " + autoStart);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent serviceIntent = new Intent(context, LocationUpdateService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 1, serviceIntent, 0);
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, TIME_PERIOD, TIME_PERIOD, pendingIntent);
		
		if(autoStart) {
			manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, TIME_PERIOD, TIME_PERIOD, pendingIntent);
		} else {
			manager.cancel(pendingIntent);
		}
		
		Log.i(TAG, "Ended");

	}

}
