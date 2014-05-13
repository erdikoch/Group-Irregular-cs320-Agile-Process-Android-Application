package com.groupIrregular.wap.db;

import android.provider.BaseColumns;

public final class WAPDatabaseContract {
	
	public static final String DATABASE_NAME = "WAP";
	public static final String TABLE_NAME = "profile";
	public static final int DATABASE_VERSION = 1;
	
	public static class Profile implements BaseColumns {
		
		private Profile() {}
		
		public static final String COLUMN_USERNAME = "username";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_SURNAME = "surname";
		public static final String COLUMN_PHONE = "phone";
		public static final String COLUMN_EMAIL = "e-mail";
		
		public static final String DEFAULT_SORT_ORDER = "name ASC";
		
		public static final String[] FULL_PROJECTION = new String[] { _ID, 
															COLUMN_USERNAME, 
															COLUMN_NAME,
															COLUMN_SURNAME,
															COLUMN_PHONE,
															COLUMN_EMAIL};
		
	}
	
}
