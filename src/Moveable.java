/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the absract Moveable class which 
 * represents the movement and common attributes
 * of moveable objects in the game map
 */

import java.util.Stack;

import org.newdawn.slick.SlickException;

/**
 * Abstract class for moveable objects
 * Allows movable subjects to share similar attributes 
 */
public abstract class Moveable extends Sprite {

	// History stack
	public Stack<Coordinates<Float, Float>> history;
	
	private int direction;
	
	public Moveable(String image_src, float x, float y) 
	throws SlickException {
		super(image_src, x, y);
		
		this.setWidth(x);
		this.setHeight(y);
		
		this.setDirection(NO_DIRECTION);
		
		history = new Stack<Coordinates<Float, Float>>();
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/** Check the location of a moveable object
	 * @param x the coordinate in the x direction
	 * @param y
	 * @return boolean indicating whether the location of the moveable
	 * object matches the x and y coordinates
	 */
	public boolean checkLocation(float x, float y) {
		if ((int)this.getWidth() == (int)x && (int)this.getHeight() == (int)y) {
			return true;
		}
		
		return false;
	}
	
	/** Moves a moveable object in the horizontal direction
	 * @param x the x direction in which the movable object should move
	 * @return void
	 */
	public void moveHorizontal(float x) {
		this.setWidth(x);
	}
	
	/** Moves a moveable object in the vertical direction
	 * @param y the y direction in which the movable object should move
	 * @return void
	 */
	public void moveVertical(float y) {
		this.setHeight(y);
	}
	
	
}