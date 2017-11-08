/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Wall class which handles
 * the Wall tile on the game map
 */


import org.newdawn.slick.SlickException;

/**
 * Wall class for the game.
 * Represents a single Wall tile on the screen
 */
public class Wall extends Sprite {
	private static final String WALL_SRC = "assets/wall.png";
	
	public Wall(float x, float y) 
	throws SlickException {
		super(WALL_SRC, x, y);
		
	}
}