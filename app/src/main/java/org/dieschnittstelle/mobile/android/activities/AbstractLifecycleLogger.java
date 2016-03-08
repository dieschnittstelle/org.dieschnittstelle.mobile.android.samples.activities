package org.dieschnittstelle.mobile.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract class AbstractLifecycleLogger extends Activity {

	protected static final String logger = "AbstractLifecycleLogger";
	
	/**
	 *
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(logger,"onCreate(): " + this);
		super.onCreate(savedInstanceState);
	}

	/**
	 * 
	 */
	@Override
	public void onStart() {
		Log.i(logger,"onStart(): " + this);
		super.onStart();
	}

	/**
	 * 
	 */
	@Override
	public void onResume() {
		Log.i(logger,"onResume(): " + this);
		super.onResume();
	}
	
	/**
	 * 
	 */
	@Override
	public void onPause() {
		//Log.i(logger,"onPause(): " + this);
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(logger,"onStop(): " + this);
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		Log.i(logger,"onDestroy(): " + this);
		super.onDestroy();
	}
	
	@Override
	public void onRestart() {
		Log.i(logger,"onRestart(): " + this);
		super.onRestart();
	}


	


}
