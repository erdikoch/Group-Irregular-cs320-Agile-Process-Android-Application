package com.groupIrregular.wap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.groupIrregular.wap.R;
import com.groupIrregular.wap.db.DatabaseManager;
import com.groupIrregular.wap.db.Profile;

public class ProfileActivity extends BaseActivity {

	private DatabaseManager manager;
	private static final int CAMERA_REQUEST = 1;

	private ImageButton profileImageButton;
	private Bitmap profilePhoto;
	private EditText userNameEditText;
	private EditText nameEditText;
	private EditText surnameEditText;
	private EditText phoneEditText;
	private EditText emailEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		createScreenController();
	}

	private void createScreenController() {

		manager = new DatabaseManager(this);

		final Profile profile = manager.searchProfile(null);

		profileImageButton = (ImageButton) findViewById(R.id.profileImageButton);
		userNameEditText = (EditText) findViewById(R.id.usernameEditText);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		surnameEditText = (EditText) findViewById(R.id.surnameEditText);
		phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		emailEditText = (EditText) findViewById(R.id.emailEditText);

		updateScreen(profile);

		Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Profile profileToBeSaved = readValues();

				int result = manager.updateSavingProfile(profileToBeSaved);
				String savingProfileResultMessage = getSavingProfileResultMessage(result);
				Toast.makeText(getApplicationContext(),
						savingProfileResultMessage, Toast.LENGTH_LONG).show();

			}
		});

		profileImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

	}

	private void updateScreen(Profile profile) {

		if (profile == null)
			return;

		userNameEditText.setText(profile.getUserName());
		userNameEditText.setEnabled(false);

		nameEditText.setText(profile.getName());
		surnameEditText.setText(profile.getSurname());
		phoneEditText.setText(profile.getPhone());
		emailEditText.setText(profile.getEmail());

		if (profile.getProfilePhoto() != null)
			profileImageButton.setImageBitmap(profile.getProfilePhoto());

	}

	private Profile readValues() {

		Profile profile = new Profile();

		if (userNameEditText.getText() != null)
			profile.setUserName(userNameEditText.getText().toString());

		if (nameEditText.getText() != null)
			profile.setName(nameEditText.getText().toString());

		if (surnameEditText.getText() != null)
			profile.setSurname(surnameEditText.getText().toString());

		if (phoneEditText.getText() != null)
			profile.setPhone(phoneEditText.getText().toString());

		if (emailEditText.getText() != null)
			profile.setEmail(emailEditText.getText().toString());

		if (profilePhoto != null)
			profile.setProfilePhoto(profilePhoto);

		return profile;
	}

	private String getSavingProfileResultMessage(int result) {

		if (DatabaseManager.SUCCESSFUL == result)
			return getResources().getString(
					R.string.toast_save_profile_successfully);

		if (DatabaseManager.PROFILE_VALIDATION_ERROR == result)
			return getResources().getString(
					R.string.toast_profile_validation_error);

		return getResources().getString(R.string.toast_unknown_error);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case CAMERA_REQUEST:

			if (resultCode == Activity.RESULT_OK) {
				profilePhoto = (Bitmap) data.getExtras().get("data");

				if (profilePhoto != null) {
					profilePhoto = resizeProfilePhoto(profilePhoto);
					profileImageButton.setImageBitmap(null);
					profileImageButton.setImageBitmap(profilePhoto);
				}
			}

			break;
		}

	}

	private Bitmap resizeProfilePhoto(Bitmap profilePhoto) {

		int width = profilePhoto.getWidth();
		int height = profilePhoto.getHeight();

		int newWidth = (width >= height) ? 150
				: (int) ((float) 150 * ((float) width / (float) height));
		int newHeight = (height >= width) ? 150
				: (int) ((float) 150 * ((float) height / (float) width));

		return Bitmap.createScaledBitmap(profilePhoto, newWidth, newHeight, true);

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
