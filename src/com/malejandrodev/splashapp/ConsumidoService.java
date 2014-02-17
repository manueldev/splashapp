package com.malejandrodev.splashapp;

import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ConsumidoService extends Service {
	private static final String LOG = null;
	//implementacion Binder, comun.
	private final IBinder mBinder = new MyBinder();
	public class MyBinder extends Binder {
		ConsumidoService getService() {
	      return ConsumidoService.this;
	    }
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	//fin implementacion Binder.

	/* Inicio codigo del servicio */
	
	//se ejecuta cuando se inicia el servicio.
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d( LOG, "Consumido Iniciado" );
		return Service.START_STICKY;
	}
	
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
    }
    
    public Date getFechaHoraActual(){
    	return Calendar.getInstance().getTime();
    }
}
