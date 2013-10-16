package com.tas.icecave.gui.levels;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
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
	private ArrayList<TextView> mPackageStatistics;

	/**
	 * Create a new instance of the package list items
	 */
	public PackageSelect()
	{
		mPackageButtons = new ArrayList<ImageView>();
		mPackageStatistics = new ArrayList<TextView>();
		
		loadPackages();

		// RelativeLayout packageList = (RelativeLayout) MainActivity.getMainActivity().findViewById(R.id.package_list);

		// Create layout parameters for package buttons and statistics views
		LayoutParams packageButtonParams =
				new LayoutParams(new RelativeLayout.LayoutParams(	RelativeLayout.LayoutParams.WRAP_CONTENT,
																	RelativeLayout.LayoutParams.WRAP_CONTENT));
		packageButtonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		LayoutParams packageStatisticParams =
				new LayoutParams(new RelativeLayout.LayoutParams(	RelativeLayout.LayoutParams.WRAP_CONTENT,
																	RelativeLayout.LayoutParams.WRAP_CONTENT));
		packageStatisticParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		// Clear views first if exist, then add new views
		// packageList.removeAllViews();
		for (int i = 0; i < mPackageButtons.size(); i++) {
			// Set parameters for current views and add to the layout
			mPackageButtons.get(i).setLayoutParams(packageButtonParams);
			mPackageStatistics.get(i).setLayoutParams(packageStatisticParams);
			// packageList.addView(mPackageButtons.get(i));
			// packageList.addView(mPackageStatistics.get(i));
			
			mPackageButtons.get(i).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Complete. Start game activity with selected package (current level index is selected in GameActivity..)
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
	 * Load packages to view lists
	 */
	private void loadPackages() {
		// TODO Complete
	}
}
