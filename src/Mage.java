/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Mage class which handles the movement of
 * the mage unit in the game map
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import static java.lang.Math.*;

/**
 * Mage class for the game.
 * Represents a single Mage sprite on the screen
 */
public class Mage extends Enemy {
	private static final String MAGE_SRC = "assets/mage.png";
	
	public Mage(float x, float y) 
	throws SlickException {
		super(MAGE_SRC, x, y);
		
		this.setWidth(x);
		this.setHeight(y);
	}
	
	/** Returns sgn from mage algorithm
	 * @param x value that must be checked
	 * @return int if signum(x) is greater than 0, return -1,
	 * otherwise return 1
	 */
	public static int sgn(float x) {
		if (signum(x) > 0) {
			return 1;
		}
		
		return -1;
	}

	/** Overrides the parent super class, and allows the mage to move
	 * according to Algorithm 1 defined in the spec.
	 * @param world the World object passed as an arguement
	 * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void 
	 */
	@Override
	public void update(World world, Input input, int delta) {
		
		// Algorithm 1 used below
		
		// Obtain x distance between the player and the mage
		float distX = (world.getPlayer().getWidth() - getWidth())
				       * App.TILE_SIZE;
		
		// Obtain y distance between the player and the mage
		float distY = (world.getPlayer().getHeight() - getHeight()) 
				       * App.TILE_SIZE;
		
		// Get absolute values for comparison
		float absX = abs(distX);
		float absY = abs(distY);
		
		// If the player has moved
		if (world.getPlayer().makeMove) {
			
			// Move in x direction if true
			if (absX > absY) {
				
				// Deterimine which direction from sgn()
				if (sgn(distX) == -1) {
					if (!world.isBlocked(getHeight(), getWidth()-1) &&
						!world.isCracked(getHeight(), getWidth()-1)) {
						setDirection(LEFT);
						moveHorizontal(getWidth()-1);
					}
				} else {
					if (!world.isBlocked(getHeight(), getWidth()+1) &&
						!world.isCracked(getHeight(), getWidth()+1)) {
						setDirection(RIGHT);
						moveHorizontal(getWidth()+1);
					}
				}
				
			// Move in y direction if true
			} else {
				
				// Deterimine which direction from sgn()
				if (sgn(distY) == -1) {
					if (!world.isBlocked(getHeight()-1, getWidth()) &&
						!world.isCracked(getHeight()-1, getWidth())) {
						this.setDirection(UP);
						moveVertical(getHeight()-1);
					}
				} else {
					if (!world.isBlocked(getHeight()+1, getWidth()) &&
						!world.isCracked(getHeight()+1, getWidth()))  {
						setDirection(DOWN);
						moveVertical(getHeight()+1);
					}
				}
			}
			
			// Reset player move flag to false
			// This must be done since update() will keep calling
			// and the mage will move too quickly across the screen
			world.getPlayer().makeMove = false;
		}
		
	}
		
}