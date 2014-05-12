package com.groupIrregular.wap.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.groupIrregular.wap.db.Profile;
import com.groupIrregular.wap.R;

public class SaveProfileAsyncTask extends AsyncTask<Profile, String, String>{
	
	public static final String SUCCESSFUL = "1";
	private Context context;
	private ProgressDialog progressDialog;

	public SaveProfileAsyncTask(Context context) {
		super();
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Please wait...", "Loading...", true, true);
	}

	@Override
	protected String doInBackground(Profile... params) {
		return profileFile(params[0]);
	}
	
	private String profileFile(Profile profile) {
		publishProgress("Saving profile...");
		return NetworkManager.saveProfile(profile);
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		String SavingProfileResultMessage = getSavingProfileResultMessage(result);
		Toast.makeText(context, SavingProfileResultMessage, Toast.LENGTH_LONG).show();
		progressDialog.cancel();
	}
	
	private String getSavingProfileResultMessage(String result) {
		
		if(SUCCESSFUL.equals(result))
			return context.getResources().getString(R.string.toast_save_profile_successfully);
		
		return context.getResources().getString(R.string.toast_unknown_error);
	}

}
