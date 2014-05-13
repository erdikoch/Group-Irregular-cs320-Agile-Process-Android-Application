package com.groupIrregular.wap.db;

import java.io.File;
import java.io.FileOutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.groupIrregular.wap.BaseActivity;

public class DatabaseManager {
	
	public static final int SUCCESSFUL = 1;
	public static final int UNKNOWN_ERROR = -1;
	public static final int PROFILE_VALIDATION_ERROR = -2;
	
	private DatabaseHelper helper;
	private Context context;
	
	public DatabaseManager(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context);
	}
	
	public Profile searchProfile(String userName) {
		
		String where = null;
    	String [] whereArgs = null;
		
		
		if(!TextUtils.isEmpty(userName)) {
			where = WAPDatabaseContract.Profile.COLUMN_USERNAME + "= ?";
	    	whereArgs = new String [] {userName};
		}
		
    	SQLiteDatabase db = helper.getReadableDatabase();
    	Cursor cursor = db.query(WAPDatabaseContract.TABLE_NAME, WAPDatabaseContract.Profile.FULL_PROJECTION, where, whereArgs, null, null, null);
		
		return buildProfile(cursor);
	}
	
	private Profile buildProfile(Cursor cursor) {
		
		if(cursor == null || cursor.getCount() != 1 || !cursor.moveToNext())
    		return null;
		
		Profile profile = new Profile();
		
		int idIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile._ID);
		profile.setId(cursor.getInt(idIndex));
		
		int userNameIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile.COLUMN_USERNAME);
		profile.setUserName(cursor.getString(userNameIndex));
		
		int nameIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile.COLUMN_NAME);
		profile.setName(cursor.getString(nameIndex));
		
		int surnameIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile.COLUMN_SURNAME);
		profile.setSurname(cursor.getString(surnameIndex));
		
		int phoneIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile.COLUMN_PHONE);
		profile.setPhone(cursor.getString(phoneIndex));
		
		int emailIndex = cursor.getColumnIndex(WAPDatabaseContract.Profile.COLUMN_EMAIL);
		profile.setEmail(cursor.getString(emailIndex));	
		
		Bitmap profilePhoto = searchProfilePhoto();
		profile.setProfilePhoto(profilePhoto);
		
		return profile;
		
	}
	
	public int updateSavingProfile(Profile profile) {
		
		if(!isProfileValid(profile))
			return PROFILE_VALIDATION_ERROR;
		
		ContentValues row = new ContentValues();
    	row.put(WAPDatabaseContract.Profile.COLUMN_USERNAME, profile.getUserName());
    	row.put(WAPDatabaseContract.Profile.COLUMN_NAME, profile.getName());
    	row.put(WAPDatabaseContract.Profile.COLUMN_SURNAME, profile.getSurname());
    	row.put(WAPDatabaseContract.Profile.COLUMN_PHONE, profile.getPhone());
    	row.put(WAPDatabaseContract.Profile.COLUMN_EMAIL, profile.getEmail());
    	
    	saveProfilePhoto(profile.getProfilePhoto());
    	
		Profile registeredProfile = searchProfile(profile.getUserName());
		
		if(registeredProfile != null)
			return updateProfile(registeredProfile.getId(), row);
		
		return saveProfile(row);
	}
	
	public int saveProfile(ContentValues row) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
    	long profileId = db.insert(WAPDatabaseContract.TABLE_NAME, null, row);
    	
    	if(profileId == -1)
    		return UNKNOWN_ERROR;
    	
    	return SUCCESSFUL;
		
	}
	
	public int updateProfile(int id, ContentValues row) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
    	String where = WAPDatabaseContract.Profile._ID + "=" + id;
    	
    	int updatedNumRow = db.update(WAPDatabaseContract.TABLE_NAME, row, where, null);
    	
    	if(updatedNumRow != 1)
    		return UNKNOWN_ERROR;
    	
    	return SUCCESSFUL;
		
	}
	
	private boolean isProfileValid(Profile profile) {
		
		if(profile == null)
			return false;
		
		
		if (TextUtils.isEmpty(profile.getUserName())
				|| TextUtils.isEmpty(profile.getName())
				|| TextUtils.isEmpty(profile.getSurname()))
			return false;
		
		return true;
	}
	
	private void saveProfilePhoto(Bitmap profilePhoto) {
		
		try {
			
			if(profilePhoto == null)
				return;
			
			FileOutputStream fos = context.openFileOutput(BaseActivity.PROFILE_PHOTO_FILE_NAME, Context.MODE_PRIVATE);
			profilePhoto.compress(CompressFormat.JPEG, 100, fos);
			fos.close();
			
        } catch (Exception e) {
            Log.e("DatabaseManager", "Error occured while saving profile photo", e);
        }
		
	}
	
	private Bitmap searchProfilePhoto() {
		
		File address = context.getFilesDir();
		
		if(address == null)
			return null;
		
		File profilePhotoFile = new File(address, BaseActivity.PROFILE_PHOTO_FILE_NAME);
		
		if(!profilePhotoFile.exists())
			return null;
		
		return BitmapFactory.decodeFile(profilePhotoFile.getAbsolutePath());
		
	}
	
	

}
