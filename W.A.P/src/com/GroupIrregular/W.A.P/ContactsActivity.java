package com.groupIrregular.wap;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupIrregular.wap.background.AddFriendAsyncTask;


public class ContactsActivity extends BaseActivity {
	
	private static final int ADD_FRIEND_DIALOG = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
			case ADD_FRIEND_DIALOG:
				dialog = getAddFriendDialog();
				break;
			default:
				dialog = null;
		}
		
		return dialog;
	}
	
	private Dialog getAddFriendDialog() {
		
		final Dialog addFriendDialog = new Dialog(this);
		
		addFriendDialog.setContentView(R.layout.add_friend_dialog);
		addFriendDialog .setTitle(getResources().getString(R.string.add_friend));
		
		final EditText addFriendEditText = (EditText) addFriendDialog.findViewById(R.id.usernameDialogEditText);
		
		Button addFriendButton = (Button) addFriendDialog.findViewById(R.id.addAddFriendDialogButton);
		addFriendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Editable usernameEditable = addFriendEditText.getText();
				String username = (usernameEditable != null) ? usernameEditable.toString() : "";
            	addFriend(username);
            	addFriendDialog.dismiss();
            }
        });
		
		Button cancelButton = (Button) addFriendDialog.findViewById(R.id.cancelAddFriendDialogButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	addFriendDialog.dismiss();
            }
        });
		
		return addFriendDialog;
		
	}
	
	private void addFriend(String friendUsername) {
		
		if(TextUtils.isEmpty(friendUsername)) {
			String message = getResources().getString(R.string.toast_empty_parameter_error, "Username");
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			return;
		}
		
		FragmentManager fragmentManager = getFragmentManager();
		ListFragment contactsListFragment = (ListFragment) fragmentManager.findFragmentById(R.id.contactsListFragment);
		AddFriendAsyncTask task = new AddFriendAsyncTask(this, contactsListFragment);
		task.execute(friendUsername);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu_contacts, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, MenuActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.contactsAddActionMenuItem:
	            showDialog(ADD_FRIEND_DIALOG);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
		
	}
	
}
