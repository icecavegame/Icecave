package com.tas.icecave.general;

import com.tas.icecave.R;

import com.tas.icecave.gui.ITheme;
import com.tas.icecave.guiLogic.theme.ThemeHolder;

public class PlayerThemes implements ITheme
{
	ThemeHolder[] mHolders;

	public PlayerThemes()
	{
		mHolders =
				new ThemeHolder[]
					{ new ThemeHolder(R.drawable.penguin_player, "Gavin"),
				  	  new ThemeHolder(R.drawable.lior_penguin_sprite, "Pingo") };
	}

	@Override
	public int getThemeId(int index)
	{
		return mHolders[index].getThemeId();
	}

	@Override
	public String getThemeName(int index)
	{
		return mHolders[index].getThemeName();
	}

	@Override
	public String[] getThemeNames()
	{
		String[] result = new String[mHolders.length];

		// Get all names
		for (int i = 0; i < mHolders.length; i++)
		{
			result[i] = mHolders[i].getThemeName();
		}

		return result;
	}

	@Override
	public int getTilePositionById(int id)
	{
		int result = -1;

		for (int i = 0; i < mHolders.length; i++)
		{
			if (id == mHolders[i].getThemeId())
			{
				result = i;
			}
		}

		return result;
	}
}
