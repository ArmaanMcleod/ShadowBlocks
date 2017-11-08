/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Floor class which handles
 * the Floor tile on the game map.
 */

import org.newdawn.slick.SlickException;

/**
 * Floor class for the game.
 * Represents a single Floor tile on the screen
 */
public class Floor extends Sprite {
	private static final String FLOOR_SRC = "assets/floor.png";
	
	public Floor(float x, float y) 
	throws SlickException {
		super(FLOOR_SRC, x, y);

	}
}