package com.groupIrregular.wap.background;

import java.util.Iterator;
import java.util.List;

import com.groupIrregular.wap.MappingActivity;
import com.groupIrregular.wap.R;
import com.groupIrregular.wap.db.DatabaseManager;
import com.groupIrregular.wap.db.Profil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class CalculateDistanceAsyncTask extends AsyncTask<Location, Void, Location> {

	private Context context;
	private SharedPreferences preferences;
	private Notification.Builder notificationBuilder;
	
	public CalculateDisntaceAsyncTask(Context context) {
		super();
		this.context = context;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	@Override
	protected Location doInBackground(Location... params) {
		return getFriendLocation(params[0]);
	}
	
	private Location getFriendLocation(Location location) {
		
		boolean locationGonder = preferences.getBoolean("PREF_LOCATION_GONDER", true);
		boolean isDistanceWarning = preferences.getBoolean("PREF_FRIENDS_WARNING", true);
		String distanceMeasurement = preferences.getString("PREF_DISTANCE_MEASUREMENT", "1");
		
		String userName = getUserName();
		if(TextUtils.isEmpty(userName))
			return null;
		
		if(locationGonder) {
			Location location = new Location(userName, location.getLatitude(), location.getLongitude());
			NetworkManager.saveProfile(location);
		}
		
		if(isDistanceWarning) {
			return getFriendLocationFromFriendsList (userName, location, distanceMeasurement);
		}
		
		return null;
		
	}
	
	private Location getFriendLocationFromFriendsList(String userName, Location location, String distanceMeasurement) {
		
		List<Location> friendsLocationList = NetworkManager.konumSorgula(userName);
		if(friendsLocationList == null 
|| friendsLocationList.size() == 0)
			return null;
		
		Iterator<Location> iterator = friendsLocationList.iterator();
		while (iterator.hasNext()) {
			Location location = (Location) iterator.next();
			
			Location friendsLocation = new Location("");
			friendsLocation.setLatitude(location.getLatitude());
			friendsLocation.setLongitude(location.getLongitude());
			
			float distance = location.distanceTo(friendsLocation);
			float distanceMeasurementFloat = Float.valueOf(distanceMeasurement) * 1000;
			
			if(distance <= distanceMeasurementFloat)
				return location;
		}
		
		return null;
		
	}
	
	@Override
	protected void onPostExecute(Location location) {
		
		((Service)context).stopSelf();
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates((KonumGuncelleService)context);
		
		if(location == null)
			return;
		
		showNotification(location);
		
	}

	@SuppressWarnings("deprecation")
	private void showNotification(Location location) {
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		String notificationHead = context.getResources().getString(R.string.distance_notification_head);
		String notificationDetail = context.getResources().getString(R.string.distance_notification_detail, location.getUserName());
		Uri notificationToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		String notificationTone = preferences.getString("PREF_NOTIFICATION_TONE", null);
		if(!TextUtils.isEmpty(notificationTone))
			notificationToneUri = Uri.parse(notificationTone);
		
		Intent intent = new Intent(context, MappingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(MappingActivity.FRIEND_INTENT_EXTRA, location.getUserName());
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		notificationBuilder = new Notification.Builder(context);
		notificationBuilder.setAutoCancel(true)
						.setContentIntent(contentIntent)
						.setSound(notificationToneUri)
						.setContentTitle(notificationHead)
						.setContentText(notificationDetail)
						.setSmallIcon(R.drawable.ic_notification);
		
		notificationManager.notify(0, notificationBuilder.getNotification());
		
	}
	
	private String getUserName() {
		
		DatabaseManager manager = new DatabaseManager(context);
		
		Profil profile = manager.searchProfile(null);
		
		if(profile == null)
			return null;
		
		return profile.getUserName();
	}
	
}
