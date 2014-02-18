package com.malejandrodev.splashapp;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ConsumiendoServicioActivity extends Activity {
	
	private static final String LOG = null;
	private ConsumidoService consumidoServ; //acceso a metodos del servicio
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumiendo_servicio);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		bindService(new Intent(this, ConsumidoService.class), mConnection, 0);
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    unbindService(mConnection);
	}
	
	public void onClickStartService(View v){
		bindService(new Intent(this, ConsumidoService.class), mConnection, 0);
		startService(new Intent(getBaseContext(), ConsumidoService.class));
	}
	public void onClickStopService(View v){
		stopService(new Intent(getBaseContext(), ConsumidoService.class));
	}
	public void onClickGetFechaHora(View v){
		TextView horafechaTx = (TextView) findViewById(R.id.textView1);
		if (consumidoServ != null){ //se verifica que exista la instancia del servicio.
			Log.d(LOG, "Fecha-hora desde servicio a activity: " + consumidoServ.getFechaHoraActual());
			horafechaTx.setText(consumidoServ.getFechaHoraActual() + "");
		} else {
			Log.d(LOG, "No se puede obtener fecha-hora, servicio no iniciado.");
			horafechaTx.setText("No se puede obtener fecha-hora, servicio no iniciado.");
		}
	}
	
	
	/* Conexion al servicio */
	private ServiceConnection mConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className, IBinder binder) {
	    	ConsumidoService.MyBinder b = (ConsumidoService.MyBinder) binder;
	    	consumidoServ = b.getService();
	    	Log.d(LOG, "Conectado");
	    }

	    public void onServiceDisconnected(ComponentName className) {
	    	consumidoServ = null;
	    	//vuelvo a realizar el bind en espera de que se inicie el servicio nuevamente
	    }
	  };
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consumiendo_servicio, menu);
		return true;
	}

}
