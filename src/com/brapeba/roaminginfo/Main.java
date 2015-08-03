/* 
 * app icon from http://crazeric.deviantart.com/ - CC license
 * 
 */


package com.brapeba.roaminginfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity 
{
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startService(new Intent(this,RoamingInfoService.class));
		Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
		finish();
	}
}