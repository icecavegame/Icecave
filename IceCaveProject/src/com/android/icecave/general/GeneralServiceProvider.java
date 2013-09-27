package com.android.icecave.general;

import android.graphics.Bitmap;
import java.util.Random;

/**
 * General service provider.
 * @author Tom
 *
 */
public class GeneralServiceProvider {
	
	/**
	 * Random member to provide with random function.
	 */
	private Random mRandom;
	
	private SelectTilesTheme mTheme;
	
	/**
	 * Private instance of this class.
	 */
	private static GeneralServiceProvider mServiceProvider = 
			new GeneralServiceProvider();
	
	/**
	 * Create a new instance of the GeneralServiceProvider class.
	 */
	private GeneralServiceProvider(){
		mRandom = new Random();
		mTheme = new SelectTilesTheme();
	}
	
	public void setTheme(Bitmap themeId) {
		mTheme.setThemeId(themeId);
	}
	
	public Bitmap getTheme() {
		return mTheme.getTheme();
	}
	
	/**
	 * Get the random member of the service provider.
	 * @return Random class.
	 */
	public Random getRandom(){
		return mRandom;
	}
	
	/**
	 * Get the static instance of the service provider.
	 * @return Service provider.
	 */
	public static GeneralServiceProvider getInstance(){
		return mServiceProvider;
	}
}
