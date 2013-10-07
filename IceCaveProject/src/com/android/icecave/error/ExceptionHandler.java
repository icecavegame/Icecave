package com.android.icecave.error;

import android.util.Log;
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
		
		// TODO What else do we want to do here?
	}

}
