package com.malejandrodev.splashapp;


import com.spoledge.aacdecoder.AACPlayer;
import com.spoledge.aacdecoder.PlayerCallback;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioTrack;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AudioStreamingService extends Service {
	protected static final String LOG = null;
	private AACPlayer aacPlayer = null;
	private boolean reproduciendo = false;
	public AudioStreamingService() {
	}

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if(!reproduciendo){
        	Toast.makeText(this, "Servicio en Ejecucion", Toast.LENGTH_SHORT).show();
            iniciarPlayer();
            playPlayer("http://190.54.48.154:8000/");
        }
        return START_STICKY;
    }
 
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
        destruirPlayer();
    }
    
    private void playPlayer(String url){
    	aacPlayer.playAsync(url);
    	reproduciendo = true;
    	setNotificacion();
    	
    }
    
    private void setNotificacion(){
    	//agrega notificacion de reproductor, permite correr servicio en foreground, se enlaza con la actividad
    	
    	//se configura el intent que se usara al hacer touch en la notificacion
    	Intent intent = new Intent(this, StreamingServiceActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
    	
    	//Seteo notificacion
    	Notification notification = new NotificationCompat.Builder(getApplicationContext())
     	.setContentTitle(getString(R.string.title_activity_streaming_service))
     	.setContentText(getString(R.string.radioname_activity_streaming_service))
     	.setSmallIcon(R.drawable.ic_launcher)
     	.setContentIntent(pi)
     	.build();

    	startForeground(123, notification);
    }
    
    private void iniciarPlayer(){
    	PlayerCallback clb = new PlayerCallback() {
			public void playerStarted() {
			}

			public void playerPCMFeedBuffer(boolean isPlaying, int bufSizeMs,
					int bufCapacityMs) {
			}

			public void playerStopped(int perf) {
			}

			public void playerException(Throwable t) {
				errorPlayer(t);
			}

			public void playerMetadata(String key, String value) {
			}

			public void playerAudioTrackCreated(AudioTrack arg0) {
				// TODO Auto-generated method stub
				
			}
		};
    	
    	
    	aacPlayer = new AACPlayer(clb);
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
    }
    
    private void errorPlayer(Throwable t){
    	Log.w( LOG, "ERROR AACPLAYER " + t );
    	stopSelf(); // llama a onDestroy 
    }
    
    
    private void detenerPlayer(){
    	aacPlayer.stop();
    	reproduciendo = false;
    }
   
    private void destruirPlayer(){
    	detenerPlayer();
    	aacPlayer = null;
    	stopSelf();
    }
}
