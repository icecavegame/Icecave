package com.tas.icecave.gui.levels;

/**
 * Interface for selecting a level.
 * @author Tom
 *
 */
public interface ILevelSelect
{
	/**
	 * Refresh the view that the class delegates.
	 */
	void refresh();
	
	/**
	 * Check if any radio button is checked in the radio group
	 */
	public boolean isChecked();
}
