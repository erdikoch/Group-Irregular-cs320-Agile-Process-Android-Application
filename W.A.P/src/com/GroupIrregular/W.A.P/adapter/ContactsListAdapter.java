package com.groupIrregular.wap.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.groupIrregular.wap.db.Profile;
import com.groupIrregular.wap.R;

public class ContactsListAdapter extends ArrayAdapter<Profile> {

	private List<Profile> contactsList;
	private Context context;
	private int layoutResourceId;
	
	public ContactsListAdapter(Context context, int layoutResourceId, List<Profile> contactsList) {
		super(context, layoutResourceId, contactsList);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.contactsList = contactsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(layoutResourceId, null);
		}
		
		Profile profile = contactsList.get(position);
		
		TextView contactsListUserNameTextView = (TextView) view.findViewById(R.id.contactsListUsernameTextView);
		TextView contactsListNameSurnameTextView= (TextView) view.findViewById(R.id.contactsListNameSurnameTextView);
		
		contactsListUserNameTextView.setText(profile.getUserName());
		contactsListNameSurnameTextView.setText(profile.getName() + " " + profile.getSurname());

		return view;
	}

}
