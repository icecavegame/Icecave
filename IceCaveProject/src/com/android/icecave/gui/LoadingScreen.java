package com.android.icecave.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.icecave.R;
import com.android.icecave.general.Consts;
import com.android.icecave.guiLogic.LoadingThread;
import com.google.ads.AdView;

public class LoadingScreen extends RelativeLayout implements ILoadingScreen
{
	private GameActivity mContext;
	private TextView mStageMessage;
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
		mStageMessage = (TextView) findViewById(R.id.player_stage_moves);
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
			// Update text
			String text =
					mContext.getString(R.string.end_stage_message_1) +
							" " +
							Integer.toString(loadableData.getGuiBoardManager().getMovesCarriedOutThisStage()) +
							"/" +
							Integer.toString(loadableData.getGuiBoardManager().getMinimalMovesForStage()) +
							" " + mContext.getString(R.string.end_stage_message_2);

			mStageMessage.setText(text);
		} else
		{
			mStageMessage.setText(R.string.initial_stage_message);
		}

		// Set animation & Go!
		final ViewPropertyAnimator animator = animate();

		// Show view with fade in animation
		animator.alpha(1).setDuration(loadableData.getAnimationDuration()).start();

		// Start creating a new stage
		LoadingThread load =
				new LoadingThread(	loadableData.getGuiBoardManager(),
									Consts.DEFAULT_START_POS,
									Consts.DEFAULT_WALL_WIDTH,
									mContext,
									loadableData.getGameTheme());
		load.start();
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

}
