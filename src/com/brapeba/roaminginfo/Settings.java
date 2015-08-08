/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity 
{
	ToggleButton tBsound;
	Button btn;
	SharedPreferences mySettings;
	final String PREFS = "MyPrefs";
	
    @Override public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
		tBsound= (ToggleButton) findViewById(R.id.esound);
		mySettings = getSharedPreferences(PREFS, MODE_MULTI_PROCESS);
		Boolean bsound= mySettings.getBoolean("sound",true);
		tBsound.setChecked(bsound);
		tBsound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				//save settings
				SharedPreferences.Editor editor = mySettings.edit();
				if (tBsound.isChecked()) editor.putBoolean("sound",true); else editor.putBoolean("sound",false); 
				editor.commit();
			}
		});
		btn= (Button) findViewById(R.id.ssexit);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				moveTaskToBack(true);
			}
		});
    }
    @Override public void onPause() 
    {
    	startService(new Intent(this,RoamingInfoService.class));
    	Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
    	super.onPause();
    }
}