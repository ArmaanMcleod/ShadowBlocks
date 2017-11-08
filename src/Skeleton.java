/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Skeleton class which handles the movement of
 * the mage skeleton in the game map
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Skeleton extends Enemy {
	private static final String SKELETON_SRC = "assets/skull.png";
	
	// time stamp to ensure that the skeleton moves one tile every second
	private int timeStamp = 0;
	private int MAX_TIME = 1000;
	
	public Skeleton(float x, float y) 
	throws SlickException {
		super(SKELETON_SRC, x, y);
		
		this.setWidth(x);
		this.setHeight(y);
		
		this.setDirection(UP);
	}
	
	/** Overrides the parent super class, and allows the skeleton to move
	 * up and down every second
	 * @param world the World object passed as an arguement
	 * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void 
	 */
	@Override
	public void update(World world, Input input, int delta) {
		
		// Add seconds passed from last frame to time stamp
		timeStamp += delta;
		
		// If current direction is up
		if (getDirection() == UP) {
			
			// Don't move skeleton is these conditions are met
			if ((world.isBlocked(getHeight()-1, getWidth()) ||
				 world.isCracked(getHeight()-1, getWidth())) || 
				(world.isBlock(getHeight()-1, getWidth()) &&
				 world.isBlocked(getHeight()-2, getWidth())) ||
				(world.isBlock(getHeight()-1, getWidth()) && 
				 world.isBlock(getHeight()-2, getWidth()))) {
				
				setDirection(DOWN);
				
			} else {
				setDirection(UP);
				
				// Otherwise move skeleton, if timeStamp has passed 1 second
				if (timeStamp >= MAX_TIME) {
					moveVertical(getHeight()-1);
					timeStamp = 0;
				}
			}
			
			// Kill player if skeleton pushes block onto player
			if (world.isBlock(getHeight(), getWidth()) && 
				world.getPlayer().checkLocation(getWidth(), getHeight()-1)) {
				world.killPlayer = true;
			}
		}
		
		// If current direction is down
		if (getDirection() == DOWN) {
			
			// Don't move if these conditions are met
			if ((world.isBlocked(getHeight()+1, getWidth()) ||
				 world.isCracked(getHeight()+1, getWidth())) || 
				(world.isBlock(getHeight()+1, getWidth()) &&
				 world.isBlocked(getHeight()+2, getWidth())) ||
				(world.isBlock(getHeight()+1, getWidth()) && 
				 world.isBlock(getHeight()+2, getWidth()))) {
				setDirection(UP);
			} else {

				setDirection(DOWN);
				
				// Otherwise move skeleton, if timeStamp has passed 1 second
				if (timeStamp >= MAX_TIME) {
					moveVertical(getHeight()+1);
					timeStamp = 0;
				}
			}
			
			// Kill player if skeleton pushes block onto player
			if (world.isBlock(getHeight(), getWidth()) &&
				world.getPlayer().checkLocation(getWidth(), getHeight()+1)) {
				world.killPlayer = true;
			}
		}
	}
}