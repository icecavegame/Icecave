package com.tas.icecave.gui;

/**
 * Interface for a lockable view.
 * @author Tom
 *
 */
public interface ILockableView
{
	/**
	 * Lock of the object.
	 */
	void lock();
	
	/**
	 * Release of the object.
	 */
	void release();
}
