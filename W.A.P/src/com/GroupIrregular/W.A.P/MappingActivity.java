package com.groupIrregular.wap;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.kodlab.kimnerede.background.SearchLocationAsyncTask;

public class MappingActivity extends MapActivity {

	public static final String ARKADAS_INTENT_EXTRA = "friend";
	
	private LocationManager manager;
	private LocationListener locationListener;
	private MapView mapView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);

        locationListener = new LocationListener() {
			
			public void onLocationChanged(Location location) {
				updateMap(location);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		};

	}
	
	private void haritaGuncelle(Location location) {
		KonumSorgulaAsyncTask task = new KonumSorgulaAsyncTask(this, location);
		task.execute();
	}
	
	protected void onResume() {
		super.onResume();
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu_harita, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, MenuActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.mapActionRefreshMenuItem:
	        	mapUpdate();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
		
	}
	
	private void mapUpdate() {
		
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(location != null) {
			SearchLocationAsyncTask task = new KonumSorgulaAsyncTask(this, location);
			task.execute();
		}
		
	}
	
}
