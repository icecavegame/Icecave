package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;
import com.tas.icecaveLibrary.general.Consts;

public class SelectBoardSizeSharedWorker extends BaseSharedWorker
{

	public SelectBoardSizeSharedWorker(SharedPreferences shared)
	{
		super(shared);
		
		mKey = Consts.SELECT_BOARD_SIZE_SIZE;
	}

	@Override
	public Object getObject()
	{
		int result = mShared.getInt(mKey, Consts.DEFAULT_BOARD_SIZE);
		
		// Validity check
		if (result <= 0) {
			result = Consts.DEFAULT_BOARD_SIZE;
		}
		
		return result;
	}

	@Override
	public void putObject(Object value)
	{
	}
}
