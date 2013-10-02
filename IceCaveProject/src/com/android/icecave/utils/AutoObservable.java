package com.android.icecave.utils;

import java.util.Observable;

/**
 * Observable object that does not require extending in order to use
 * @author Sagie
 *
 */
public class AutoObservable extends Observable 
{
	@Override
	public void notifyObservers()
	{
		// Automatically set changed flag before notifying observers
		setChanged();
		super.notifyObservers();
	}
	
	@Override
	public void notifyObservers(Object data)
	{
		// Automatically set changed flag before notifying observers
		setChanged();
		super.notifyObservers(data);
	}
}
