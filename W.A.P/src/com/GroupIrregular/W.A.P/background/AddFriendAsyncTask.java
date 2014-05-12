package com.groupIrregular.wap.background;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.groupIrregular.wap.R;
import com.groupIrregular.wap.adapter.ContactsListAdapter;
import com.groupIrregular.wap.db.DatabaseManager;
import com.groupIrregular.wap.db.Profile;

public class AddFriendAsyncTask extends AsyncTask<String, String, List<Profile>> {
	
	public static final String SUCCESSFUL = "1";
	public static final String UNSUCCESSFUL = "-1";
	public static final String CONTACT_NOT_FOUND_ERROR = "-2";
	public static final String CONTACT_AVAILABLE_ERROR = "-3";
	public static final String PROFILE_NOT_FOUND_ERROR = "-4";
	private String resultCode;
	
	
	private Context context;
	private ProgressDialog progressDialog;
	private ListFragment contactsListFragment;
	
	public AddFriendAsyncTask(Context context, ListFragment contactsListFragment) {
		super();
		this.context = context;
		this.contactsListFragment = contactsListFragment;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Please Wait...", "Loading...", true, true);
	}
	
	@Override
	protected List<Profile> doInBackground(String... params) {
		return addFriend(params[0]);
	}
	
	private List<Profile> addFriend(String friendUserName) {
		
		String userName = getUserName();
		if(TextUtils.isEmpty(userName)) {
			resultCode = PROFILE_NOT_FOUND_ERROR;
			return new ArrayList<Profile>();
		}
		
		publishProgress("Adding Contact...");
		
		resultCode = NetworkManager.addFriend(userName, friendUserName);
		
		if(SUCCESSFUL.equals(resultCode)) {
			publishProgress("Updating List...");
			return NetworkManager.searchFriend(userName);
		}
			
		return new ArrayList<Profile>();
	}

	@Override
	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}
	
	@Override
	protected void onPostExecute(List<Profile> result) {
		
		String resultMessage = getAddFriendResultMessage(resultCode);
		Toast.makeText(context, resultMessage, Toast.LENGTH_LONG).show();
		
		if(result == null || result.size() == 0) {
			progressDialog.cancel();
			return;
		}
		
		ContactsListAdapter adapter = new ContactsListAdapter(context, R.layout.contacts_list_item, result);
		contactsListFragment.getListView().setAdapter(adapter);
		contactsListFragment.setListShown(true);
    	progressDialog.cancel();
		
	}

	private String getAddFriendResultMessage(String result) {
		
		if(SUCCESSFUL.equals(result))
			return context.getResources().getString(R.string.toast_add_friend_successfully);
		else if(CONTACT_NOT_FOUND_ERROR.equals(result))
			return context.getResources().getString(R.string.toast_notFound_friend_profile);
		else if(CONTACT_AVAILABLE_ERROR.equals(result))
			return context.getResources().getString(R.string.toast_friend_avaiable);
		else if(PROFILE_NOT_FOUND_ERROR.equals(result))
			return context.getResources().getString(R.string.toast_no_registered_profile);
		else
			return context.getResources().getString(R.string.toast_unknown_error);
		
	}
	
	private String getUserName() {
		
		DatabaseManager manager = new DatabaseManager(context);
		
		Profile profile = manager.searchProfile(null);
		
		if(profile == null)
			return null;
		
		return profile.getUserName();
	}

}
