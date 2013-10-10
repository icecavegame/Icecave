package com.tas.icecave.gui;

import com.tas.icecaveLibrary.general.Consts;

import android.graphics.Typeface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.icecave.R;
import com.tas.icecaveLibrary.general.GeneralServiceProvider;

public class LoadingScreen extends RelativeLayout implements ILoadingScreen
{
	private GameActivity mContext;
	private TextView mLoadingMessage, mLoadingResult;

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

		// Set styles
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(), Consts.STYLE_ICE_AGE);
		mLoadingMessage.setTypeface(tf);
		mLoadingResult.setTypeface(tf);
	}

	@Override
	public void preLoad(ILoadable loadableData)
	{
		bringToFront();

		// Show summary text if not initial loading screen
		if (!loadableData.isInitialLoading())
		{
			// Show how much moves player made / minimum moves required, then a random phrase
			mLoadingResult.setText(mContext.getString(R.string.finish_level_message) + " " +
					Integer.toString(loadableData.getMovesCarriedOutThisStage()) + "/" +
					Integer.toString(loadableData.getMinimalMovesForStage()));
		} else
		{
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
		// final Runnable endAction = new Runnable()
		// {
		// @Override
		// public void run()
		// {
		//
		// }
		// };

		// Hide screen (alpha to 0), set the duration of animation and animate
		animator.alpha(0).setDuration(loadableData.getAnimationDuration());
		// animator.withEndAction(endAction);
		animator.start();
	}

	private String getRandomPhrase()
	{
		String[] messages = mContext.getResources().getStringArray(R.array.initial_stage_messages);

		return messages[GeneralServiceProvider.getInstance().getRandom().nextInt(messages.length)];
	}
}
