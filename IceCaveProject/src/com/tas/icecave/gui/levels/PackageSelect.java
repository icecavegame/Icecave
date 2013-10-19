package com.tas.icecave.gui.levels;

import com.tas.icecaveLibrary.general.Consts;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;

/**
 * This calls wraps the logic of the package selecting.
 * 
 * @author Sagie
 * 
 */
public class PackageSelect implements ILevelSelect
{
	private ArrayList<ImageView> mPackageButtons;

	/**
	 * Create a new instance of the package list items
	 */
	public PackageSelect()
	{
		mPackageButtons = new ArrayList<ImageView>();

		createPackageButtons();

		// RelativeLayout packageList = (RelativeLayout) MainActivity.getMainActivity().findViewById(R.id.package_list);

		// Create layout parameters for package buttons and statistics views
		LayoutParams packageButtonParams =
				new LayoutParams(new RelativeLayout.LayoutParams(	RelativeLayout.LayoutParams.WRAP_CONTENT,
																	RelativeLayout.LayoutParams.WRAP_CONTENT));
		packageButtonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		// Clear views first if exist, then add new views
		// packageList.removeAllViews();
		for (int i = 0; i < mPackageButtons.size(); i++)
		{
			// Set parameters for current views and add to the layout
			mPackageButtons.get(i).setLayoutParams(packageButtonParams);
			// packageList.addView(mPackageButtons.get(i));

			final int index = i;
			mPackageButtons.get(i).setOnClickListener(new OnClickListener()
			{
				final String PACKAGE_NAME = Consts.PACKAGE_NAMES[index]; // Use this as the id of a package?
				
				@Override
				public void onClick(View v)
				{
					// TODO Complete. Start select level activity & send selected package data to it
				}
			});
		}
	}

	@Override
	public void refresh()
	{
		// TODO Complete. Lock all package buttons which should be unavailable and darken them to lock as if they are locked..
	}

	@Override
	public boolean isChecked()
	{
		// TODO Irrelevant, remove when finished with radio button format
		return false;
	}

	/**
	 * Load package buttons to view lists
	 */
	private void createPackageButtons()
	{
		// TODO Complete. Should be receiving an id of some sort to suit an image to its button, or maybe just use name index from PACKAGE_NAMES...
	}
}
