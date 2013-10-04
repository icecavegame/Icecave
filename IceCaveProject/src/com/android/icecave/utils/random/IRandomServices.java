package com.android.icecave.utils.random;


/**
 * Class for random functions.
 * @author Tom
 *
 */
public interface IRandomServices
{
	/**
	 * Get random int.
	 * @return a random int.
	 */
	int getRandomInt();
	
	/**
	 * Get a random double value.
	 * @return random double value.
	 */
	double getRandomDouble();
	
	/**
	 * Get a random long value.
	 * @return random long value.
	 */
	long getRandomLong();
	
	/**
	 * Get a random float value.
	 * @return random float value.
	 */
	float getRandomFloat();
	
	/**
	 * Get random int.
	 * @param ceiling - The max value of random values to get.
	 * @return a random int.
	 */
	int getRandomInt(int ceiling);
	
	/**
	 * Get a random wrapper.
	 * @param type - Type for a random wrapper to return as random value.
	 * @return Random wrapper.
	 */
	IRandomTypeWrapper<?> getRandomWrapper(Class<?> type);
}
