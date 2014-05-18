package com.groupIrregular.wap.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groupIrregular.wap.MapActivity;
import com.groupIrregular.wap.R;
import com.groupIrregular.wap.background.SearchFriendsAsyncTask;

public class ContactsListFragment extends ListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		ListView contactsListView = getListView();
		
		contactsListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView arg0, View view, int position, long id) {
				RelativeLayout layout = (RelativeLayout) view;
				TextView contactsListUsernameTextView = (TextView) layout.findViewById(R.id.contactsListUsernameTextView);
				
				Intent intent = new Intent(getActivity(), MapActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra(MapActivity.FRIEND_INTENT_EXTRA, contactsListUsernameTextView.getText());
				startActivity(intent);
			}
        });
		
		setListShown(true);
		
		SearchFriendsAsyncTask task = new SearchFriendsAsyncTask(getActivity(), this);
		task.execute();
		
		contactsListView.setDividerHeight(5);
		
	}
	
}
