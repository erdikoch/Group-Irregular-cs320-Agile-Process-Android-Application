package com.groupIrregular.wap.map;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class FriendLocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	private Context context;
	
	public FriendLocationItemizedOverlay(Drawable drawable, Context context) {
		super(boundCenterBottom(drawable));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
		overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}
	
	public void clearOverlays() {
		overlays.clear();
		populate();
	}
	
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = overlays.get(index);
		AlertDialog.Builder dialoge = new AlertDialog.Builder(context);
		dialoge.setTitle(item.getTitle());
		dialoge.setMessage(item.getSnippet());
		dialoge.show();
		return true;
	}
	
}
