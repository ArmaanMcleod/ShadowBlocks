/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the CrackedWall class which handles
 * the cracked wall tile on the game map
 */

import org.newdawn.slick.SlickException;


/**
 * Cracked Wall class for the game.
 * Represents a cracked wall sprite. 
 */
public class CrackedWall extends Sprite {
	private static final String CRACKED_WALL_SRC = "assets/cracked_wall.png";
	
	public CrackedWall(float x, float y) 
	throws SlickException {
		super(CRACKED_WALL_SRC, x, y);

			this.setWidth(x);
			this.setHeight(y);
	}
	
	/** Checks if coordinates(x and y) match this.
	 * location of the cracked wall.
	 * @param x the coordinate position in the x direction.
	 * @param y the coordinate position in the y direction.
     * @return boolean if coordinates are at the crackedwall position,
     * otherwise false.
     */
	public boolean hasBlock(int x, int y) {
		 return (x == (int)this.getWidth() &&
		         y == (int)this.getHeight());
	}
}
