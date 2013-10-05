package com.tas.icecaveGeneral.utils.random;

import java.util.HashMap;
import java.util.Random;

/**
 * Provides non crypto random values.
 * @author Tom
 *
 */
public class NonCryptoRandomServiceProvider implements IRandomServices
{
	// Random generator.
	Random mRandom;
	
	// Array of wrappers.
	HashMap<Class<?>, IRandomTypeWrapper<?>> wrappers;

	/**
	 * Create a new instance of the NonCryptoRandomServiceProvider object. 
	 */
	public NonCryptoRandomServiceProvider()
	{
		mRandom = new Random();
	}
	
	/**
	 * Create a new instance of the NonCryptoRandomServiceProvider object.
	 * 
	 *  @param randomSeed - seed for the random generator.
	 */
	public NonCryptoRandomServiceProvider(int randomSeed)
	{
		mRandom = new Random(randomSeed);
	}

	@Override
	public IRandomTypeWrapper<?> getRandomWrapper(Class<?> type)
	{
		if(wrappers.containsKey(type))
		{
			return wrappers.get(type);
		}
		
		return null;
	}

	@Override
	public int getRandomInt()
	{
		return mRandom.nextInt();
	}

	@Override
	public double getRandomDouble()
	{
		return mRandom.nextDouble();
	}

	@Override
	public int getRandomInt(int ceiling)
	{
		return mRandom.nextInt(ceiling);
	}

	@Override
	public long getRandomLong()
	{
		return mRandom.nextLong();
	}

	@Override
	public float getRandomFloat()
	{
		return mRandom.nextFloat();
	}
}
