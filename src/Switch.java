/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Target class which handles
 * the Target tile on the game map
 */

import org.newdawn.slick.SlickException;

/**
 * Switch class for the game.
 * Represents a single Switch sprite on the screen
 */
public class Switch extends Sprite {
	private static final String SWITCH_SRC = "assets/switch.png";

	public Switch(float x, float y) 
	throws SlickException {
		super(SWITCH_SRC, x, y);
		
		this.setWidth(x);
		this.setHeight(y);

	}
	
	/** Checks if Switch is active from a block being pushed onto it.
	 * @param world the World passed as an arguement
	 * @return boolean if switch is active, return true, false otherwise
	 */
	public boolean isActive(World world) {
		if (hasBlock(world)) {
			return true;
		}
		return false;
	}
	
	/** Checks if Switch has a block on it.
	 * @param world the World passed as an arguement
	 * @return boolean if switch is active, return true, false otherwise
	 */
	public boolean hasBlock(World world) {
		if (world.isBlock(getHeight(), getWidth())) {
			return true;
		}
		
		return false;
	}
	
}