package com.tas.icecaveGeneral.utils.random;

/**
 * Interface used to wrap object to get random values of them.
 * @author Tom
 *
 * @param <random_type> Type to wrap.
 */
public interface IRandomTypeWrapper<random_type>
{
	/**
	 * Get a random value.
	 * @return A random value.
	 */
	random_type getRandomValue();
	
	/**
	 * Get a random value.
	 * 
	 * @param ceiling - The max value to generate.
	 * @return A random value.
	 */
	random_type getRandomValue(random_type ceiling);
	
	/**
	 * Get a random value.
	 * 
	 * @param ceiling - The max value to generate.
	 * @pararm floor - The min value to generate. 
	 * @return A random value.
	 */
	random_type getRandomValue(random_type floor, random_type ceiling);
}
