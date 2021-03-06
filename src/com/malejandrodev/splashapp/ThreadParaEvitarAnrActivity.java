package com.malejandrodev.splashapp;

import java.lang.ref.WeakReference;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ThreadParaEvitarAnrActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_para_evitar_anr);
		// Show the Up button in the action bar.
		setupActionBar();
		
	}
	public void onClickBtnConHilos(View v){
    	//instancio la tarea que se ejecutar� en otro hilo
		CargandoTask tarea = new CargandoTask(this);
		int segundosDeLaTarea = 6;
		tarea.execute(segundosDeLaTarea);
    }
	
	/**
	 * Tarea que carga nada, es solo un ejemplo de AsyncTask.
	 * 
	 * Clase estatica que se ejecute en un hilo aparte.
	 * Se define el tipo de dato que se le mandar� a la tarea en ejecucion,
	 * el tipo de dato que devolvera el progreso (por ej: porcenaje de descarga Float)
	 * y el tipo de dato que devolvera la tarea (tama�o del archivo: Integer)
	 * Parametros que no se necesiten se pone Void.
	 * 
	 * Se instancia y llama a .execute para usar.
	 * myCargandoTask tarea = new CargandoTask(this);
	 * tarea.execute(parametros);
	 * 
	 */
	static class CargandoTask extends AsyncTask<Integer, Float, Integer> {
		WeakReference<ThreadParaEvitarAnrActivity> context;
		private ProgressBar pbarProgreso;
		
		public CargandoTask(ThreadParaEvitarAnrActivity activity) {
			context = new WeakReference<ThreadParaEvitarAnrActivity>(activity);
		}
		
		/*
		 * tarea en otro hilo. 
		 * Recibe un numero indeterminado de parametros del tipo String. params sera un array.
		 * No se puede interactuar con la UI desde aqui.
		 */
		@Override
		protected Integer doInBackground(Integer... params) {
			while(! isCancelled() ){
				//aca lo que har� la tarea
				long tiempoMuerto = (params[0]*1000)/100;
				 for(int i=1; i<=100; i++) {
		             try {
		            	 Thread.sleep(tiempoMuerto);
		            	 publishProgress(123f);
					} catch (InterruptedException e) {	}
		         }
				 return 0;
			}
			return 0;
		}
		
		/*
		 * Se ejecuta antes de doInBackground, 
		 * sirve para avisar el comienzo de una tarea en la UI. (loading etc)
		 */
		protected void onPreExecute() {
			ThreadParaEvitarAnrActivity activity = context.get();
			if (activity != null && !activity.isFinishing()){
				//aqui se realiza la modificacion del UI para la preparacion
				Button btnCorrerTarea = (Button)activity.findViewById(R.id.btnConHilos);
				btnCorrerTarea.setEnabled(false);
				pbarProgreso = (ProgressBar)activity.findViewById(R.id.pbarProgresoAsync);
				pbarProgreso.setMax(100);
		    	pbarProgreso.setProgress(0);
		    	
			}
			
		}
		
		/*
		 * Permite actualizar la UI mientras se ejecuta la tarea.
		 * Se debe llamar a publishProgress(parametrosPertinentes) desde doInBackground
		 */
		protected void onProgressUpdate(Float... values){
			pbarProgreso.incrementProgressBy(1); 
	         
		}
		/*
		 * Se ejecuta tras terminar doInbackground
		 * recibe como parametro lo que devolver�
		 */
		protected void onPostExecute(Integer result) {
			ThreadParaEvitarAnrActivity activity = context.get();
			if (activity != null && !activity.isFinishing()){
				//aqui se realiza la modificacion del UI para el resultado final
				Toast.makeText(activity.getApplicationContext(), "Tarea finalizada!",
		                 Toast.LENGTH_LONG).show();
				Button btnCorrerTarea = (Button)activity.findViewById(R.id.btnConHilos);
				btnCorrerTarea.setEnabled(true);
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
