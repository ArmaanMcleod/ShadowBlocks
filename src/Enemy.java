/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Enemy abstract class which describes similar
 * atrributes of enemy units
 */

import org.newdawn.slick.SlickException;

/**
 * Abstract Enemy class for the game.
 * Represents an enemy in the world.
 */
public abstract class Enemy extends Moveable {
	
	public Enemy(String image_src, float x, float y) 
	throws SlickException {
		super(image_src, x, y);
		
		this.setWidth(x);
		this.setHeight(y);
	}
	
	/** Checks if enemy object has contacted the player
	 * @param world the World passed as an arguement
     * @return boolean true if location is the same as the player object,
     * false otherwise
     */
	public boolean isContactPlayer(World world) {
		if (((int)getHeight() == (int)world.getPlayer().getHeight()) &&
			((int)getWidth() == (int)world.getPlayer().getWidth())) {
			return true;
		}
		
		return false;
	}
	
}