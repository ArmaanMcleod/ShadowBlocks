/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Pushable abstract class which represents
 * the features of a pushable block in the game map
 * 
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Abstract Pushable class for the game.
 * Represents common properties of pushable blocks.
 */
public abstract class Pushable extends Moveable {
	
	public Pushable(String image_src, float x ,float y) 
	throws SlickException {
		super(image_src, x ,y);
		
		this.setWidth(x);
		this.setHeight(y);
		
		history.push(Coordinates.newCoordinates(this.getWidth(), 
				                                this.getHeight()));
	}

	/** Overrides the parent super class, and allows blocks to move.
	 * In my implmentation, only the player, skeleton and rogue
	 * can push blocks. The mage is too unpredictable and doesn't
	 * really interact with blocks in the levels.
	 * @param world the World object passed as an arguement
	 * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void 
	 */
	@Override
	public void update(World world, Input input, int delta) {

		// If pusbable block can validly move up
		// And a skeleton or a player is pushing
		if ((isBlockMove(world, getWidth(), getHeight()-1) && 
			(isPlayerPush(world, getWidth(), getHeight(), UP) ||
			 isSkeletonPush(world, getWidth(), getHeight(), UP)))) {

			// move block up
			moveVertical(getHeight() - 1);
			
			// Reset blocks array
			world.blocks[(int) getHeight()+1][(int) getWidth()] = false;
			world.blocks[(int) getHeight()][(int) getWidth()] = true;
		}
		
		// If pusbable block can validly move down
		// And a skeleton or a player is pushing
		if ((isBlockMove(world, getWidth(), getHeight()+1) &&
			 isPlayerPush(world, getWidth(), getHeight(), DOWN) ||
			 isSkeletonPush(world, getWidth(), getHeight(), DOWN))) {
			
			// move block down
			moveVertical(getHeight() + 1);
				
			world.blocks[(int) getHeight()-1][(int) getWidth()] = false;
			world.blocks[(int) getHeight()][(int) getWidth()] = true;

		}
		
		// If pusbable block can validly move left 
		// And a rogue or a player is pushing
		if (isBlockMove(world, getWidth()-1, getHeight()) &&
		   (isPlayerPush(world, getWidth(), getHeight(), LEFT) ||
			isRoguePush(world, getWidth(), getHeight(), LEFT))) {

			// move left
			moveHorizontal(this.getWidth() - 1);
			
			world.blocks[(int) getHeight()][(int) getWidth()+1] = false;
			world.blocks[(int) getHeight()][(int) getWidth()] = true;

		}
		
		// If pusbable block can validly move right
		// And a rogue or a player is pushing
		if (isBlockMove(world, getWidth()+1, getHeight()) &&
		   (isPlayerPush(world, getWidth(), getHeight(), RIGHT) ||
			isRoguePush(world, getWidth(), getHeight(), RIGHT))) {
			
			// move right
			moveHorizontal(getWidth() + 1);
			
			world.blocks[(int) getHeight()][(int) getWidth()-1] = false;
			world.blocks[(int) getHeight()][(int) getWidth()] = true;

		}
		
	}
	
	/** Checks if rogue is pushing against a block in some 
	 * specified direction. 
	 * @param world the world passed as an arguement
	 * @param x the x coordinate to be compared if equal against the rogue
	 * @param y the y coordinate to be compared if equal against the rogue
	 * @param direction the direction the block is is being pushed from
	 * @return boolean if Rogue is pushing against block, return true, 
	 * otherwise false
	 */
	public boolean isRoguePush(World world, float x, 
			                   float y, int direction) {
		if (world.getRogue() != null) {
			if ((world.getRogue().getDirection() == direction) &&
			   ((int)y == (int)world.getRogue().getHeight()) &&
			   ((int)x == (int)world.getRogue().getWidth())) {
					return true;
			}
		}
				
		return false;
	}
	
	/** Checks if position is a valid position for a block to move to
	 * @param world the World arguement passed as an arguement
	 * @param x the x coordinate to be checked
	 * @param y the y coordinate to be checked
	 * @return boolean indicating if destination can be reached
	 */
	public boolean isBlockMove(World world, float x, float y) {
		
		if (!world.isBlocked(y, x) &&
			(!world.isBlock(y, x))) {
			return true;
		}

		return false;
	}
	
	/** Checks if a player is pushing against a block in some 
	 * specified direction. 
	 * @param world the world passed as an arguement
	 * @param x the x coordinate to be compared if equal against the rogue
	 * @param y the y coordinate to be compared if equal against the rogue
	 * @param direction the direction the block is is being pushed from
	 * @return boolean if player is pushing against block, return true, 
	 * otherwise false
	 */
	public boolean isPlayerPush(World world, float x, float y, int direction) {
		if (world.getPlayer() != null) {
			if ((world.getPlayer().getDirection() == direction) &&
			   ((int)y == (int)world.getPlayer().getHeight()) &&
			   ((int)x == (int)world.getPlayer().getWidth())) {
				return true;
			}

		}
		return false;
	}
	
	/** Checks if a skeleton is pushing against a block in some 
	 * specified direction. 
	 * @param world the world passed as an arguement
	 * @param x the x coordinate to be compared if equal against the rogue
	 * @param y the y coordinate to be compared if equal against the rogue
	 * @param direction the direction the block is is being pushed from
	 * @return boolean if skeleton is pushing against block, return true, 
	 * otherwise false
	 */
	public boolean isSkeletonPush(World world, float x, 
			                      float y, int direction) {
		if (world.getSkeleton() != null) {
			if ((world.getSkeleton().getDirection() == direction) &&
			   ((int)y == (int)world.getSkeleton().getHeight()) &&
			   ((int)x == (int)world.getSkeleton().getWidth())) {
				return true;
			}
		}
		
		return false;
	}

}