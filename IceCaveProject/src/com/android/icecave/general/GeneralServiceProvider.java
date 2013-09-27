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
	
	private Bitmap mTilesTheme;
	private Bitmap mPlayerTheme;
	
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
	}
	
	/**
	 * Get the random member of the service provider.
	 * @return Random class.
	 */
	public Random getRandom(){
		return mRandom;
	}
	
	public void setTilesTheme(Bitmap tilesTheme) {
		mTilesTheme = tilesTheme;
	}
	
	public Bitmap getTilesTheme() {
		return mTilesTheme;
	}
	
	public void setPlayerTheme(Bitmap playerTheme) {
		mPlayerTheme = playerTheme;
	}
	
	public Bitmap getPlayerTheme() {
		return mPlayerTheme;
	}
	
	/**
	 * Get the static instance of the service provider.
	 * @return Service provider.
	 */
	public static GeneralServiceProvider getInstance(){
		return mServiceProvider;
	}
}
