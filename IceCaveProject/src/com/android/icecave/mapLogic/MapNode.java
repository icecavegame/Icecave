package com.android.icecave.mapLogic;

import java.util.ArrayList;
import java.util.Stack;

import com.android.icecave.mapLogic.tiles.ITile;
import com.android.icecave.utils.INode;

/**
 * Ice cave map node.
 * @author Tom
 *
 */
public class MapNode implements INode<ITile>
{
	// Indicates an unbreakable map node.
	final int UNBREAKABLE = -1;

	/**
	 * Stack of all the nodes.
	 */
	private Stack<MapNode> mMapStack;
	
	/**
	 * Parent node of the current.
	 */
	private MapNode mFather;
	
	/**
	 * Number of times that the player can reach this node.
	 */
	private int mThouchCounter;
	
	/**
	 * Tile the node holds.
	 */
	private ITile mTile;

	/**
	 * Array to hold all the children nodes of the current.
	 */
	ArrayList<MapNode> mChildren;

	/**
	 * Create a new instance of the MapNode object.
	 * @param father - Father of the node.
	 * @param tile - Value of the node to save.
	 */
	MapNode(MapNode father, ITile tile)
	{
		mThouchCounter = UNBREAKABLE;
		mFather = father;
		mTile = tile; // TODO: validate.
		mChildren = new ArrayList<MapNode>();

		// If is the root.
		if (father == null)
		{
			mMapStack = new Stack<MapNode>();
		}
		else
		{
			mMapStack = father.mMapStack;
		}

	}

	/**
	 * Get the children of the current node.
	 * @return Array of the childern of the current node.
	 */
	ArrayList<MapNode> getChildren()
	{
		return mChildren;
	}

	/**
	 * This function sets the number of touches this object can 'handle' till it will disassemble and disappear.
	 * 
	 * @param nNewValue
	 *            - The number of touches this object can handle from the player.
	 */
	void setTouchCounter(int nNewValue)
	{
		mThouchCounter = nNewValue;
	}

	/**
	 * This function return's the number of touches this object can 'handle' 
	 * till it will disassemble and disappear. 
	 * If this value is UNBREAKABLE (-1) then this object is unbreakable.
	 * 
	 * @return The number of touches this object can handle from the player.
	 */
	int getTouchCounter()
	{
		return mThouchCounter;
	}

	/**
	 * Returns an indication to whether or not this map node is breakable.
	 * 
	 * @return true if the node is breakable.
	 */
	boolean isBreakable()
	{
		return mThouchCounter != UNBREAKABLE;
	}

	/**
	 * Get the number of nodes.
	 * @return
	 */
	public int getNodesNum()
	{
		return mMapStack.size();
	}

	/**
	 * Clear all the stack of nodes.
	 */
	public void clear()
	{
		mMapStack.clear();
	}

	@Override
	public MapNode getParent()
	{
		return mFather;
	}

	/**
	 * Peek in the node stack.
	 * @return Get the last node inserted.
	 */
	public MapNode peek()
	{
		if (mMapStack.isEmpty())
		{
			return null;
		}

		return mMapStack.peek();
	}

	/**
	 * Peek on the next node in the stack.
	 * @param nCounter
	 * @return
	 */
	MapNode peekNext(int nCounter)
	{
		if (nCounter > mMapStack.size())
		{
			return null;
		}

		return mMapStack.get(nCounter);
	}

	/**
	 * Peek on the last node in the stack.
	 * @return Last node in the stack.
	 */
	MapNode peekLast()
	{
		if (mMapStack.isEmpty())
		{
			return null;
		}

		return mMapStack.lastElement();
	}

	@Override
	public MapNode push(INode<ITile> node)
	{
		MapNode ndResult = new MapNode(this, node.getValue());

		mMapStack.push(ndResult);
		mChildren.add(ndResult);

		return ndResult;
	}
	
	@Override
	public MapNode push(ITile value)
	{
		MapNode ndResult = new MapNode(this, value);
		
		mMapStack.push(ndResult);
		mChildren.add(ndResult);
		
		return ndResult;
	}

	@Override
	public INode<ITile> pop()
	{
		if (this.getParent() == null)
		{
			return null;
		}

		// Remove the object.
		mMapStack.remove(this);

		return this;
	}

	/**
	 * Used only to add the root node.
	 */
	public void addRoot()
	{
		mMapStack.push(this);
	}

	@Override
	public int getLevel()
	{
		final int NUMERIC_ERROR = 999;

		MapNode curNode = this;
		int nLevelCounter = 0;
		curNode = (MapNode) curNode.getParent();
		while (curNode != null && nLevelCounter < NUMERIC_ERROR)
		{
			nLevelCounter++;
			curNode = (MapNode) curNode.getParent();
		}

		if (nLevelCounter == NUMERIC_ERROR)
		{
			nLevelCounter = 0;
		}

		return nLevelCounter + 1;
	}

	@Override
	public ITile getValue() {
		return mTile;
	}
}