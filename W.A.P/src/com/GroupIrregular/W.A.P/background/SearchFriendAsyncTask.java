package com.groupIrregular.wap.background;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.groupIrregular.wap.adapter.ContactsListAdapter;
import com.groupIrregular.wap.db.DatabaseManager;
import com.groupIrregular.wap.db.Profile;
import com.groupIrregular.wap.R;

public class SearchFriendAsyncTask extends AsyncTask<Void, String, List<Profile>> {
	
	public static final String PROFILE_NOT_FOUND_ERROR = "-1";
	private String resultCode;
	
	private Context context;
	private ProgressDialog progressDialog;
	private ListFragment contactsListFragment;
	
	public SearchFriendAsyncTask(Context context, ListFragment contactsListFragment) {
		super();
		this.context = context;
		this.contactsListFragment = contactsListFragment;
	}
	
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Please Wait...", "Loading...", true, true);
	}

	@Override
	protected List<Profile> doInBackground(Void... params) {
		return getContactList();
	}

	private List<Profile> getContactList() {
		
		String userName = getUserName();
		if(TextUtils.isEmpty(userName)) {
			resultCode = PROFILE_NOT_FOUND_ERROR;
			return new ArrayList<Profile>();
		}
		
		publishProgress("Searching Contact List...");
		
		return NetworkManager.searchFriend(userName);
		
	}
	
	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}

	@Override
	protected void onPostExecute(List<Profile> result) {
		
		String resultMessage = getSearchFriendResultMessage(resultCode);
		
		if(!TextUtils.isEmpty(resultMessage)) {
			Toast.makeText(context, resultMessage, Toast.LENGTH_LONG).show();
			progressDialog.cancel();
			return;
		}
		
		if(result == null || result.size() == 0) {
    		String message = context.getResources().getString(R.string.toast_notFound_friend);
    		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    		progressDialog.cancel();
			return;
    	}
		
		ContactsListAdapter adapter = new ContactsListAdapter(context, R.layout.contacts_list_item, result);
		contactsListFragment.getListView().setAdapter(adapter);
		contactsListFragment.setListShown(true);
    	progressDialog.cancel();
    	
	}
	
	private String getSearchFriendResultMessage(String result) {
		
		if(PROFILE_NOT_FOUND_ERROR.equals(result))
			return context.getResources().getString(R.string.toast_no_registered_profile);
		
		return null;
		
	}
	
	private String getUserName() {
		
		DatabaseManager manager = new DatabaseManager(context);
		
		Profile profile = manager.searchProfile(null);
		
		if(profile == null)
			return null;
		
		return profile.getUserName();
	}

}
