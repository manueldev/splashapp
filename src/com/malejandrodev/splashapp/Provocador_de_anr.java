package com.malejandrodev.splashapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Provocador_de_anr extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provocador_de_anr);
        
    }
    
	public void onClickBtnSinHilos(View v){
    	ProgressBar pbarProgreso = (ProgressBar) findViewById(R.id.pbarProgreso);
    	pbarProgreso.setMax(100);
    	pbarProgreso.setProgress(0);
    	 
         for(int i=1; i<=10; i++) {
             tareaLarga();
             pbarProgreso.incrementProgressBy(10);
         }
         Toast.makeText(Provocador_de_anr.this, "Tarea finalizada!",
                 Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void tareaLarga(){
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
    }
}
