package com.groupIrregular.wap.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class LocationUpdateService extends Service implements LocationListener {
	
	private LocationManager locationManager;
	
	@Override
	public void onCreate() {
		Log.d("LocationUpdateService", "created");
		super.onCreate();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d("LocationUpdateService", "started");
		
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onLocationChanged(Location location) {
		CalculateTheDistanceAsyncTask task = new CalculateTheDistanceAsyncTask(this);
		task.execute(new Location[] {location});
	}
	
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	public void onProviderEnabled(String provider) {}
	public void onProviderDisabled(String provider) {}

}
