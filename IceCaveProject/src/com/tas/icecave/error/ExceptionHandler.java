package com.tas.icecave.error;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler
{
	Object mToHandle;
	
	public ExceptionHandler (Object toHandle) {
		mToHandle = toHandle;
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1)
	{
		Log.e("Ice Cave", arg1.getMessage());
		
		Toast.makeText((Activity)mToHandle, "Sorry, it seems the game has crashed :(", Toast.LENGTH_SHORT).show();
		// TODO What else do we want to do here?
	}

}
