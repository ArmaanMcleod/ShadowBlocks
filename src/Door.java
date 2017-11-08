/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Door class which handles
 * the Door on the game map
 */

import org.newdawn.slick.SlickException;


/**
 * Door class for the game.
 * Represents a Door sprite. 
 */
public class Door extends Sprite {
	private static final String DOOR_SRC = "assets/door.png";
	
	// boolean flag indicating if door is open
	public boolean doorOpen = false;
	
	public Door(float x, float y) 
	throws SlickException {
		super(DOOR_SRC, x, y);

		this.setWidth(x);
		this.setHeight(y);
	}
	
	/** Locks the door in World if called
	 * @param world the World passed as an arguement
     * @return void
     */
	public void lockDoor(World world) {
		world.blocked[(int)getHeight()][(int)getWidth()] = true;
	}
	
	/** Opens the door in World if called
	 * @param world the World passed as an arguement
     * @return void
     */
	public void openDoor(World world) {
		world.blocked[(int)getHeight()][(int)getWidth()] = false;
	}
}