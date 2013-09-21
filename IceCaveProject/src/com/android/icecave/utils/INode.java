package com.android.icecave.utils;

/**
 * Interface for a tree node.
 * @author Tom
 *
 */
public interface INode<value_type> {
	
	/**
	 * Get the value of the node.
	 * @return
	 */
	value_type getValue();
	
	/**
	 * Return the parent node of the current.
	 * @return parent node.
	 */
	INode<value_type> getParent();

	/**
	 * Add a node to the current.
	 * @param node - node to add.
	 * @return node pushed, null if error.
	 */
	INode<value_type> push(INode<value_type> node);
	
	/**
	 * Add a node to the current.
	 * @param value - value to create a new node with.
	 * @return node pushed, null if error.
	 */
	INode<value_type> push(value_type value);
	
	/**
	 * pop the node from the tree.
	 * @return node popped out.
	 */
	INode<value_type> pop();
	
	/**
	 * Get the level of the node in the tree.
	 * @return The level of the node in the tree.
	 */
	int getLevel();
}
