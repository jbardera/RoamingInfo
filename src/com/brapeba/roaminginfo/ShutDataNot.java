package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ShutDataNot extends Activity {
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override protected void onStart() 
	{
		super.onStart();
	}

	@Override protected void onRestart() 
	{
		super.onRestart();
	}

	@Override protected void onResume() 
	{
		SharedPreferences mySettings;
		
		super.onResume();
		mySettings = getSharedPreferences("MyPrefs", MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = mySettings.edit();
		editor.putBoolean("showdata",false);
		editor.commit();
		finish();
	}
}