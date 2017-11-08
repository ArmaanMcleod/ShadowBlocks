/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the target class which
 * represents a target sprite on the game map
 */

import org.newdawn.slick.SlickException;

/**
 * Target class for the game.
 * Represents a single Target tile on the screen
 */
public class Target extends Sprite {
	private static final String TARGET_SRC = "assets/target.png";
	
	public Target(float x, float y) 
	throws SlickException {
		super(TARGET_SRC, x, y);

	}

}