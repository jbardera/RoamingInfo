/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity 
{
	ToggleButton sound;
	
    @Override public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
		LinearLayout rootLL = (LinearLayout) findViewById(R.id.settings);
		TextView sTextView = new TextView(this);
		sTextView.setText(getString(R.string.string9));
		rootLL.addView(sTextView);
		sound= (ToggleButton) findViewById(R.id.esound);
		
    }
    
    public void doSaveButtonClick() 
    {
		Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
	}
}