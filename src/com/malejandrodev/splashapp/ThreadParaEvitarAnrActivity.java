package com.malejandrodev.splashapp;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ThreadParaEvitarAnrActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_para_evitar_anr);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
	}
	
	/**
	 * Tarea que carga nada, es solo un ejemplo de AsyncTask.
	 * 
	 * Clase estatica que se ejecute en un hilo aparte.
	 * Se define el tipo de dato que se le mandará a la tarea en ejecucion,
	 * el tipo de dato que devolvera el progreso (por ej: porcenaje de descarga Float)
	 * y el tipo de dato que devolvera la tarea (tamaño del archivo: Integer)
	 * Parametros que no se necesiten se pone Void.
	 * 
	 * Se instancia y llama a .execute para usar.
	 * myCargandoTask tarea = new CargandoTask(this);
	 * tarea.execute(parametros);
	 * 
	 */
	static class CargandoTask extends AsyncTask<String, Float, Integer> {
		WeakReference<ThreadParaEvitarAnrActivity> context;
		
		public CargandoTask(ThreadParaEvitarAnrActivity activity) {
			context = new WeakReference<ThreadParaEvitarAnrActivity>(activity);
		}
		
		/*
		 * tarea en otro hilo. 
		 * Recibe un numero indeterminado de parametros del tipo String. params sera un array.
		 * No se puede interactuar con la UI desde aqui.
		 */
		@Override
		protected Integer doInBackground(String... params) {
			while(! isCancelled() ){
				//aca lo que hará la tarea
				
			}
			return null;
		}
		
		/*
		 * Se ejecuta antes de doInBackground, 
		 * sirve para avisar el comienzo de una tarea en la UI. (loading etc)
		 */
		protected void onPreExecute() {
			
		}
		
		/*
		 * Permite actualizar la UI mientras se ejecuta la tarea.
		 * Se debe llamar a publishProgress(parametrosPertinentes) desde doInBackground
		 */
		protected void onProgressUpdate(Float... values){
			
		}
		/*
		 * Se ejecuta tras terminar doInbackground
		 * recibe como parametro lo que devolverá
		 */
		protected void onPostExecute(Integer result) {
			ThreadParaEvitarAnrActivity activity = context.get();
			if (activity != null && !activity.isFinishing()){
				//aqui se realiza la modificacion del UI para el resultado final
				
			}
		}
		
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
		getMenuInflater().inflate(R.menu.thread_para_evitar_anr, menu);
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
