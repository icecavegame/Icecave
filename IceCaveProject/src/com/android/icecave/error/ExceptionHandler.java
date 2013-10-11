package com.android.icecave.error;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

import com.tas.icecave.gui.MainActivity;

import android.app.Activity;

import android.widget.Toast;

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
		
		Toast.makeText((Activity)mToHandle, "Sorry, it seems the game has crashed :(", Toast.LENGTH_SHORT).show();
		// TODO What else do we want to do here?
		
		if (mToHandle instanceof MainActivity) {
			SharedPreferences shared = ((MainActivity)mToHandle).getSharedPreferences(Consts.PREFS_FILE_TAG, 0);
			shared.edit().remove(Consts.LEVEL_SELECT_TAG).commit();
		}
	}

}
