package com.tas.icecave.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.icecave.R;
import com.google.ads.AdView;
import com.tas.icecaveLibrary.general.GeneralServiceProvider;

public class LoadingScreen extends RelativeLayout implements ILoadingScreen
{
	private GameActivity mContext;
	private TextView mLoadingMessage, mLoadingResult;
	private AdView mAd;

	public LoadingScreen(Context context)
	{
		super(context);

		mContext = (GameActivity) context;
	}

	public LoadingScreen(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mContext = (GameActivity) context;
	}

	public void setViews()
	{
		mLoadingMessage = (TextView) findViewById(R.id.loading_screen_text);
		mLoadingResult = (TextView) findViewById(R.id.loading_screen_result);
		mAd = (AdView) findViewById(R.id.advertisment_loading_screen_top);
	}

	@Override
	public void preLoad(ILoadable loadableData)
	{
		bringToFront();

		// Enable the ad
		enableAd();

		// Show summary text if not initial loading screen
		if (!loadableData.isInitialLoading())
		{
			// Show how much moves player made / minimum moves required, then a random phrase
			mLoadingResult.setText(Integer.toString(loadableData.getMovesCarriedOutThisStage()) + "/" +
					Integer.toString(loadableData.getMinimalMovesForStage()));
		} else {
			mLoadingResult.setText(R.string.empty);
		}

		// Set a random initial text from the array
		mLoadingMessage.setText(getRandomPhrase());

		// Set animation & Go!
		final ViewPropertyAnimator animator = animate();

		// Show view with fade in animation
		animator.alpha(1).setDuration(loadableData.getAnimationDuration()).start();

		loadableData.onLoad();
	}

	@Override
	public void postLoad(final ILoadable loadableData)
	{
		final ViewPropertyAnimator animator = animate();
		final Runnable endAction = new Runnable()
		{
			@Override
			public void run()
			{
				// Disable the ad while loading screen is not active
				disableAd();
			}
		};

		// Hide screen (alpha to 0), set the duration of animation and animate
		animator.alpha(0).setDuration(loadableData.getAnimationDuration());
		animator.withEndAction(endAction);
		animator.start();
	}

	private void enableAd()
	{
		mAd.setVisibility(View.VISIBLE);
		mAd.setClickable(true);
	}

	private void disableAd()
	{
		mAd.setVisibility(View.GONE);
		mAd.setClickable(false);
	}

	private String getRandomPhrase()
	{
		String[] messages = mContext.getResources().getStringArray(R.array.initial_stage_messages);

		return messages[GeneralServiceProvider.getInstance().getRandom().nextInt(messages.length)];
	}

}
