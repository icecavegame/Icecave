package com.tas.icecave.gui.levels;

import android.app.Activity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.icecave.R;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.general.EDifficulty;


/**
 * This calls wraps the logic of the level selecting.
 * @author Tom
 *
 */
public class RadioButtonLevelSelect implements ILevelSelect
{
	final String LEVEL_SELECT_TAG = "levelSelect";
	
	/**
	 * The radio group for selecting a level.
	 */
	RadioGroup mLevelSelect;
	
	/**
	 * The reset statistics button.
	 */
	RadioButton mResetStatistics;
	
	/**
	 * Create a new instance of the radio button level select.
	 * @param activity - The main activity. 
	 * TODO: We need to use the trick that let us use the main activity
	 * context globally and stop sending it in functions.
	 */
	public RadioButtonLevelSelect(Activity activity)
	{
		mLevelSelect = (RadioGroup) activity.findViewById(R.id.level_select);
		mResetStatistics = (RadioButton) activity.findViewById(R.id.reset_statistics);
		
		// Clear before creating buttons
		mLevelSelect.removeAllViews();
		
		mResetStatistics.setChecked(false);
		
		for (EDifficulty difficulty : EDifficulty.values())
		{
			RadioButton newButton;
			// TODO: Create level view factory instead of radio buttons.
			// Check if hard.
			if(!difficulty.equals(EDifficulty.Hard))
			{
				newButton = new RadioButton(activity);
				newButton.setTextColor(activity.getResources().getColor(R.color.white));
			}
			else
			{
				newButton = new HardButton(activity);
			}
			
			// An alternative to this is to set another variable (string id) in the enum
			newButton.setText(difficulty.name());
			mLevelSelect.addView(newButton);
		}

		mLevelSelect.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// Save level to prefs
				SharedPreferencesFactory.getInstance().set(LEVEL_SELECT_TAG,
						group.indexOfChild(group.findViewById(checkedId)));
			}
		});
	}

	@Override
	public void refresh()
	{
		HardButton hardButton = null;
		
		// Get the HardButton.
		for (int i = 0; i < mLevelSelect.getChildCount(); i++)
		{
			if(mLevelSelect.getChildAt(i) instanceof HardButton)
			{
				hardButton = (HardButton) mLevelSelect.getChildAt(i);
			}
		}
		
		if(hardButton != null)
		{
			if((Boolean)SharedPreferencesFactory.getInstance().
						  get(Consts.LOCK_HARD_DIFFICULTY))
			{
				hardButton.lock();
			}
			else
			{
				hardButton.release();
			}
		}
		int currentDifficulty = 
				(Integer)SharedPreferencesFactory.getInstance().get(LEVEL_SELECT_TAG);

		// TODO: This should be more generic, not by index.
		mLevelSelect.check(mLevelSelect.getChildAt(currentDifficulty).getId());
	}
}
