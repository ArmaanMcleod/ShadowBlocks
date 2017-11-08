/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Stone class which handles the Stone
 * tile on the game map.
 */


import org.newdawn.slick.SlickException;

/**
 * Stone class for the game.
 * Represents a single Stone tile on the screen
 */
public class Stone extends Pushable {
	private static final String STONE_SRC = "assets/stone.png";

	public Stone(float x, float y) 
	throws SlickException {
		super(STONE_SRC, x, y);

	}
	
}





