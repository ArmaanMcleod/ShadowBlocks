/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file location contains the Tnt class which
 * represents a tnt sprite on the game map
 */

import org.newdawn.slick.SlickException;

/**
 * Tnt class for the game.
 * Represents a single pushable Tnt block on the screen
 */
public class Tnt extends Pushable {
	private static final String TNT_SRC = "assets/tnt.png";
	
	public Tnt(float x, float y) 
	throws SlickException {
		super(TNT_SRC, x, y);

	}

}

