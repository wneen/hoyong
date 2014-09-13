package com.kasund.mqtt.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PushActivity extends Activity {
	private String mDeviceID;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);         
  	  	((TextView) findViewById(R.id.target_text)).setText("Topic : good");
 
  	  	final Button startButton = ((Button) findViewById(R.id.start_button));
  	  	final Button stopButton = ((Button) findViewById(R.id.stop_button));
  	  	final Button saveButton = ((Button) findViewById(R.id.save_button));
	  	final TextView label =((TextView) findViewById(R.id.target_text));
	  	final EditText input0 =((EditText) findViewById(R.id.edit0));
	  	final EditText input1 =((EditText) findViewById(R.id.edit1));
	  	final EditText input2 =((EditText) findViewById(R.id.edit2));
	  	
  	  	startButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		    	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
		    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
		    	editor.commit();
				PushService.actionStart(getApplicationContext());		        
		  		startButton.setEnabled(false);
		  		stopButton.setEnabled(true);				
			}
		});
  	  	stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PushService.actionStop(getApplicationContext());		        								
		  		startButton.setEnabled(true);
		  		stopButton.setEnabled(false);				
			}
		});
  	  saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = getSharedPreferences(PushService.PREF_TOPIC, MODE_PRIVATE).edit();
		    	editor.putString(PushService.PREF_TOPIC, input0.getText().toString());
		    	editor.putString(PushService.PREF_HOST, input1.getText().toString());
		    	editor.putString(PushService.PREF_PORT, input2.getText().toString());
		    	editor.commit();
		    	label.setText("Topic : "+input0.getText());
		   
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
  	  	
  	  	SharedPreferences p = getSharedPreferences(PushService.TAG, MODE_PRIVATE);
  	  	boolean started = p.getBoolean(PushService.PREF_STARTED, false);
  	   SharedPreferences pref = getSharedPreferences(PushService.PREF_TOPIC, MODE_PRIVATE);
  	   String topic = pref.getString(PushService.PREF_TOPIC, "good");
  	   String host = pref.getString(PushService.PREF_HOST, "192.168.0.0");
  	   String port = pref.getString(PushService.PREF_PORT, "1883");
	   
 		((TextView) findViewById(R.id.target_text)).setText("Topic : "+topic);
 		((EditText) findViewById(R.id.edit0)).setText(topic);
 		((EditText) findViewById(R.id.edit1)).setText(host);
 		((EditText) findViewById(R.id.edit2)).setText(port);
 		  	  	
  		((Button) findViewById(R.id.start_button)).setEnabled(!started);
  		((Button) findViewById(R.id.stop_button)).setEnabled(started);
  		
  		
    }
    
   
}