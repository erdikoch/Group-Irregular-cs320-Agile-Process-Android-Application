package com.groupIrregular.wap.background;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.groupIrregular.wap.HaritaActivity;
import com.groupIrregular.wap.R;
import com.groupIrregular.wap.db.DatabaseManager;
import com.groupIrregular.wap.db.Profil;
import com.groupIrregular.wap.map.ArkadasKonumItemizedOverlay;
import com.groupIrregular.wap.map.MevcutKonumItemizedOverlay;

public class LocationSearchAsynTask extends AsyncTask<Void, Void, List<Location>>{

	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private Context context;
	private MapView mapView;
	private Location location;
	
	public LocationSearchAsyncTask(Context context, Location location) {
		super();
		this.context = context;
		this.location = location;
		mapView = (MapView) ((Activity)context).findViewById(R.id.mapView);
	}

	@Override
	protected List<Location> doInBackground(Void... params) {
		return getLocationList();
	}
	
	private List<Location> getLocationList() {
		
		String username = getUsername();
		if(TextUtils.isEmpty(kullaniciAdi))
			return new ArrayList<Konum>();
		

		return NetworkManager.searchLocation(username);
	}
	
	@Override
	protected void onPostExecute(List<Location> result) {
		
		if(result == null || result.size() == 0)
			return;
		
		Drawable friendLocation = context.getResources().getDrawable(R.drawable.ic_friend_location);
        Drawable availableLocation = context.getResources().getDrawable(R.drawable.ic_available_location);
        FriendLocationItemizedOverlay friendLocationOverlay = new FriendLocationItemizedOverlay(friendLocation, context);
        AvailableLocationItemizedOverlay availableLocationOverlay = new AvailableLocationItemizedOverlay(availableLocation);

        mapView.getOverlays().clear();
        //availableLocationOverlay.clearOverlays();
        //friendLocationOverlay.clearOverlays();
        
        String friendInMap = getFriendInMap();
        
		Iterator<Location> iterator = result.iterator();
		while (iterator.hasNext()) {
			Location location = (Location) iterator.next();
			GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (konum.getBoylam() * 1e6));
			String updatingTime = context.getResources().getString(R.string.last_update, format.format(location.getUpdatingTime()));
			OverlayItem item = new OverlayItem(point, location.getUsername(), updatingTime);
			
			if(TextUtils.isEmpty(friendInMap))
				friendLocationOverlay.addOverlay(item);
			else if(friendInMap.equalsIgnoreCase(location.getUsername()))
					friendLocationOverlay.addOverlay(item);
		}
		
		mapView.getOverlays().add(friendLocationOverlay);
		
		GeoPoint cPoint = new GeoPoint((int) (location.getLatitude() * 1e6),
										(int) (location.getLongitude() * 1e6));
		OverlayItem item = new OverlayItem(cPoint, "", "");
		availableLocationOverlay.addOverlay(item);
		mapView.getOverlays().add(availableLocationOverlay);
		
		mapView.postInvalidate();
		
		centerMap(result, location);
		
	}
	
	private void centerMap(List<Location> result, Location location) {
		
		result.add(new Location(null, location.getLatitude(), location.getLongitude()));
		
		double north = -90;
		double south = 90;
		double west = 180;
		double east = -180;

		Iterator<Location> iterator = result.iterator();
		while (iterator.hasNext()) {
			Location location = (Location) iterator.next();
			north = Math.max(north, location.getLatitude());
			south = Math.min(south, location.getLatitude());
			west = Math.min(west, location.getLongitude());
			east = Math.max(east, location.getLongitude());
		}
		
		double verticalCenter = (north + south) / 2;
		double horizontalCenter = (west+east) / 2;
		
		GeoPoint center = new GeoPoint((int) (verticalCenter * 1e6), (int) (horizontalCenter * 1e6));
		
		int verticalDistance = (int) (Math.abs(north - south) * 1e6 * 1.2);
	    int horizontalDistance = (int) (Math.abs(west - east) * 1e6 * 1.2);

	    mapView.getController().animateTo(center);
	    mapView.getController().zoomToSpan(verticalDistance, horizontalDistance);
	}

	private String getFriendInMap() {
		
		Intent intent = ((Activity)context).getIntent();
        
        if(intent.getExtras() == null || intent.getExtras().getString(MapActivity.FRINED_INTENT_EXTRA) == null)
        	return null;
        
        return intent.getExtras().getString(MapActivity.FRINED_INTENT_EXTRA);
	}

	private String getUsername() {
		
		DatabaseManager manager = new DatabaseManager(context);
		
		Profile profile = manager.searchProfile(null);
		
		if(profile == null)
			return null;
		
		return profile.getUsername();
	}
	
	
}
