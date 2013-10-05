package com.tas.icecave.gui;

import android.util.AttributeSet;

import android.view.ViewPropertyAnimator;
import android.widget.TextView;
import com.android.icecave.R;
import com.tas.icecave.general.Consts;
import com.tas.icecave.guiLogic.LoadingThread;

import android.content.Context;

import android.widget.RelativeLayout;

public class LoadingScreen extends RelativeLayout implements ILoadingScreen
{
	private GameActivity mContext;

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

	@Override
	public void preLoad(ILoadable loadableData)
	{
		bringToFront();

		TextView stageMessage = (TextView) findViewById(R.id.player_stage_moves);

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

			stageMessage.setText(text);
		} else
		{
			stageMessage.setText(R.string.initial_stage_message);
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
//		final Runnable endAction = new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				// Show views on top of board once loading screen is done
//				mContext.drawForeground();
//			}
//		};
		
		// Hide screen (alpha to 0), set the duration of animation and animate
		animator.alpha(0).setDuration(loadableData.getAnimationDuration());
//		animator.withEndAction(endAction);
		animator.start();
	}

}
