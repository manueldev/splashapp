package com.malejandrodev.splashapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuPrincipalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
	}
	
	public void onClick_a_provocador_de_anr(View v){
		Intent anr = new Intent(getBaseContext(), Provocador_de_anr.class);
		startActivity(anr);
	}
	public void onClickThreadParaEvitarAnrActivity(View v){
		Intent anr = new Intent(getBaseContext(), ThreadParaEvitarAnrActivity.class);
		startActivity(anr);
	}
	public void onClickStreamingAudioPlayerActivity(View v){
		Intent anr = new Intent(getBaseContext(), StreamingAudioPlayer.class);
		startActivity(anr);
	}

	public void onClickStreamingServiceActivity(View v){
		Intent anr = new Intent(getBaseContext(), StreamingServiceActivity.class);
		startActivity(anr);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}

}
