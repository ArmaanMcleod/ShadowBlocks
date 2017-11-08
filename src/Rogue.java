/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Rogue class which handles the movement of
 * the mage rogue in the game map
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Rogue class for the game.
 * Represents a single Rogue sprite on the screen
 */
public class Rogue extends Enemy {
	private static final String ROGUE_SRC = "assets/rogue.png";
	
	public Rogue(float x, float y) 
	throws SlickException {
		super(ROGUE_SRC, x, y);

		this.setWidth(x);
		this.setHeight(y);
		
		this.setDirection(LEFT);
	}
	
	/** Overrides the parent super class, and allows the Rogue to move
	 * left and right when the player moves
	 * @param world the World object passed as an arguement
	 * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void 
	 */
	@Override
	public void update(World world, Input input, int delta) {
		
		// If the player has moved
		if (world.getPlayer().makeMove) {
			
			// If rogue is set to left direction
			if (getDirection() == LEFT) {
				
				// Don't move rogue is these conditions are met
				if ((world.isBlocked(getHeight(), getWidth()-1) ||
				     world.isCracked(getHeight(), getWidth()-1)) || 
				    (world.isBlock(getHeight(), getWidth()-1) &&
				     world.isBlocked(getHeight(), getWidth()-2)) ||
				    (world.isBlock(getHeight(), getWidth()-1) &&
				     world.isBlock(getHeight(), getWidth()-2))) {
					
					setDirection(RIGHT);
				} else {
					
					// Otherwise move rogue one space in the left direction
					setDirection(LEFT);
					moveHorizontal(getWidth()-1);
				}
				
				// Addition added to game
				// if the rogue pushes a block onto the player, 
				// the player dies.
				if (world.isBlock(getHeight(), getWidth()) &&
				world.getPlayer().checkLocation(getWidth()-1, getHeight())) {
					
					world.killPlayer = true;
				}
				
			}
			
			// If rogue is set to right direction
			else if (getDirection() == RIGHT) {
				
				// Don't move rogue if these conditions are met
				if ((world.isBlocked(getHeight(), getWidth()+1) ||
					 world.isCracked(getHeight(), getWidth()+1)) || 
					(world.isBlock(getHeight(), getWidth()+1) &&
					 world.isBlocked(getHeight(), getWidth()+2)) ||
					(world.isBlock(getHeight(), getWidth()+1) && 
					 world.isBlock(getHeight(), getWidth()+2))) {
					
					setDirection(LEFT);
				}  else {
					
					// Move Rogue in right direction
					setDirection(RIGHT);
					moveHorizontal(getWidth()+1);
				}
				
				// Kill player if true
				if (world.isBlock(getHeight(), getWidth()) && 
				world.getPlayer().checkLocation(getWidth()+1, getHeight())) {
					world.killPlayer = true;
				} 
			}
			
			// Reset player makeMove flag to ensure rogue doesnt keep moving
			// even when player is not moving
			world.getPlayer().makeMove = false;
		}
		

	}
	
	
}