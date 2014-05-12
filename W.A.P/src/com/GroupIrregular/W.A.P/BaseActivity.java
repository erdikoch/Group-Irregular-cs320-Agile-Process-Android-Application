package com.groupIrregular.wap;

import android.app.Activity;

public class BaseActivity extends Activity {
	
	public static final String PROFILE_PHOTO_FILE_NAME = "ProfilFotografim.jpg";
	public static final String WAP_BASE_URL = "http://10.0.2.2/kimnerede/";
	public static final String WAP_SAVE_PROFILE_URL = WAP_BASE_URL + "profil_kaydet.php";
	public static final String WAP_LIST_FRIENDS_URL = WAP_BASE_URL + "arkadas_listele.php";
	public static final String WAP_ADD_FRIENDS_URL = WAP_BASE_URL + "arkadas_ekle.php";
	public static final String WAP_SEARCH_LOCATION_URL = WAP_BASE_URL + "konum_sorgula.php";
	public static final String WAP_SAVE_LOCATION_URL = WAP_BASE_URL + "konum_kaydet.php";
}
