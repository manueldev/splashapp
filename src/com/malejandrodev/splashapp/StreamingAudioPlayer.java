package com.malejandrodev.splashapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import com.spoledge.aacdecoder.AACPlayer;


public class StreamingAudioPlayer extends Activity {

	protected static final String LOG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_streaming_audio_player);
		// Show the Up button in the action bar.
		setupActionBar();
		
		String url = "http://190.54.48.154:8000"; // your URL here
		
		AACPlayer aacPlayer = new AACPlayer();
		try {
	        java.net.URL.setURLStreamHandlerFactory( new java.net.URLStreamHandlerFactory(){
	            public java.net.URLStreamHandler createURLStreamHandler( String protocol ) {
	                Log.d( LOG, "Asking for stream handler for protocol: '" + protocol + "'" );
	                if ("icy".equals( protocol )) return new com.spoledge.aacdecoder.IcyURLStreamHandler();
	                return null;
	            }
	        });
	    }
	    catch (Throwable t) {
	        Log.w( LOG, "Cannot set the ICY URLStreamHandler - maybe already set ? - " + t );
	    }
		aacPlayer.playAsync(url);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.streaming_audio_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
