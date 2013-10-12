package com.tas.icecave.gui.levels;

import com.tas.icecave.R;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.tas.icecave.gui.ILockableView;

public class HardButton extends RadioButton implements ILockableView
{
	RadioGroup mFather;
	
	/**
	 * Create a new instance of the hard button.
	 * @param context
	 */
	public HardButton(Context context)
	{
		super(context);
	}
	
	public HardButton(Context context, RadioGroup father)
	{
		super(context);
		mFather = father;
	}

	@Override
	public void release()
	{
		setTextColor(getResources().getColor(R.color.white));
		setOnClickListener(null);
	}
	
	@Override
	public void lock()
	{
		// Make view look unclickable
		setTextColor(getResources().getColor(R.color.dark_red));
		
		setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Cancel check of this view
				Toast.makeText(v.getContext(), 
							   R.string.level_lock_warning, 
							   Toast.LENGTH_LONG).show();
				
				// Clear check
				mFather.clearCheck();
			}
		});
	}
}
