package com.groupIrregular.wap.background;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.text.TextUtils;
import android.util.Log;

import com.groupIrregular.wap.BaseActivity;
import com.groupIrregular.wap.db.Profile;

public class NetworkManager {
	
	private static final String TAG = "NetworkManager";
	
	public static String saveProfile(Profile profile) {
		
		BufferedReader in = null;
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(BaseActivity.WAP_SAVE_PROFILE_URL);
			
			List<NameValuePair> parametreList = new ArrayList<NameValuePair>();
			parametreList.add(new BasicNameValuePair("username", profile.getUserName()));
			parametreList.add(new BasicNameValuePair("name", profile.getName()));
			parametreList.add(new BasicNameValuePair("surname", profile.getSurname()));
			parametreList.add(new BasicNameValuePair("phone", profile.getPhone()));
			parametreList.add(new BasicNameValuePair("e-mail", profile.getEmail()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametreList);
			request.setEntity(entity);
						
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			return in.readLine();
		
		} catch (Exception e) {
			Log.d(TAG, "Error occured while saving profile", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public static List<Profile> searchFriend(String userName) {
		
		if(TextUtils.isEmpty(userName))
			return new ArrayList<Profile>();
		
		InputStream content = null;
		
		try {
			
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(BaseActivity.WAP_LIST_FRIENDS_URL);
			
			List<NameValuePair> parametreList = new ArrayList<NameValuePair>();
			parametreList.add(new BasicNameValuePair("username", userName));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametreList);
			request.setEntity(entity);
						
			HttpResponse response = client.execute(request);

			content = response.getEntity().getContent();
			
			List<Profile> contactList = getContactListInputStream(content);
			
			return contactList;
			
			
		} catch (Exception e) {
			Log.d(TAG, "Error occured while connecting to the HTTP", e);
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return new ArrayList<Profile>();
		
	}
	
	private static List<Profile> getContactListInputStream(InputStream content) {
		
		List<Profile> contactList = new ArrayList<Profile>();
		
		try {
			
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(content);
			Element root = document.getDocumentElement();
			NodeList friendNodeList = root.getElementsByTagName("friend");
			
			if(friendNodeList == null || friendNodeList.getLength() == 0)
				return contactList;
			
			int numFriends = friendNodeList.getLength();
			
			for (int i = 0; i < numFriends; i++) {
				Element friend = (Element) friendNodeList.item(i);
				Node userNameNode = friend.getElementsByTagName("username").item(0);
				String userName = userNameNode.getFirstChild().getNodeValue();
				
				Node nameNode = friend.getElementsByTagName("name").item(0).getFirstChild();
				String name = nameNode.getNodeValue();
				
				Node surnameNode = friend.getElementsByTagName("surname").item(0).getFirstChild();
				String surname = surnameNode.getNodeValue();
				
				Node phoneNode = friend.getElementsByTagName("phone").item(0).getFirstChild();
				String phone = (phoneNode != null) ? phoneNode.getNodeValue() : "";
				
				Node emailNode = friend.getElementsByTagName("e-mail").item(0).getFirstChild();
				String email = (emailNode != null) ? emailNode.getNodeValue() : "";
				
				Profile profile = new Profile();
				profile.setUserName(userName);
				profile.setName(name);
				profile.setSurname(surname);
				profile.setPhone(phone);
				profile.setEmail(email);
				
				contactList.add(profile);
			}
			
		} catch (Exception e) {
			Log.d(TAG, "Error occured while parsing XML...", e);
		}
		
		return contactList;
		
	}
	
	
	public static String addFriend(String username, String friendUsername) {
		
		BufferedReader in = null;
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(BaseActivity.WAP_ADD_FRIENDS_URL);
			
			List<NameValuePair> parametreList = new ArrayList<NameValuePair>();
			parametreList.add(new BasicNameValuePair("username", username));
			parametreList.add(new BasicNameValuePair("friend_username", friendUsername));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametreList);
			request.setEntity(entity);
						
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			return in.readLine();
		
		} catch (Exception e) {
			Log.d(TAG, "Error occured while adding friend...", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
		
	}

}
