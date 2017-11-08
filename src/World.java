/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the World class which handles
 * the entire game state
 */

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Word class for the game.
 * Represents entire game world
 */
public class World {
	
	// Level file components
	private final static String LEVEL_FOLDER = "levels/";
	private final static String LEVEL_EXTENSION = ".lvl";
	
	// Sound file Constants
	private static final String FINISH_LEVEL_SOUND = 
			                      "assets/Ta_Da-SoundBible_com-1884170640.ogg";
	
	private static final String EXPLOSION_SOUND = 
			      "assets/Explosion_Sound_Effect_Powerpuff_Girls_Version_.ogg";
	
	private static final String DIE_SOUND = 
			                         "assets/neck_snap-Vladimir-719669812.ogg";
	
	private static final String DOOR_OPEN_SOUND = 
			       "assets/Creaking_Door_Spooky-SoundBible_com-1909842345.ogg";
	
	private static final String DOOR_CLOSE_SOUND = "assets/Metal_Door.ogg";
	
	private static final String MATTS_FACE = "assets/profile.png";
	
	private static final String ENDING_MUSIC = 
			                    "assets/arcade_slot_machine_sound_FX.ogg";
	
	private static final String IN_GAME_MUSIC = "assets/Puzzle-Game.ogg";
	
	private static final float PITCH = 0.5f;
	private static final float VOLUME = 0.5f;
	
	// Variable indicating current level
	private int LEVEL = 0;
	private static int MAX_LEVEL = 5;
	
	// Move counter
	public int MOVE_COUNT = 0;
	
	// World data 
	private Object[] worldData = null;
	private static final int BLOCKED = 0;
	private static final int BLOCKS = 1;
	private static final int TARGETS = 2;
	private static final int CRACKED = 3;
	private static final int ENEMY = 4;
	
	// Array containing map sizes
	private int[] mapSizes = null; 
	
	public int MAP_WIDTH;
	public int MAP_HEIGHT;
	
	// All aprites contained in an array
	// defaultSprites used for level restarting
	// sprites used for modifying
	private ArrayList<Sprite> defaultSprites;
	public ArrayList<Sprite> sprites;
	
	// Array containing which tiles are blocked
	// This will include Walls and Doors
	public boolean[][] blocked;
	
	// Array containing which tiles are blocks
	public boolean[][] blocks;
	
	// Array containing target locations
	private boolean[][] targets;
	private int numTargets = 0;
	
	// ArrayList containting the crackedWall locations
	private ArrayList<Coordinates<Integer, Integer>> crackedWallLocations;
	
	// Some helpful index variables
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int NUM_COORDS = 2;
	
	// boolean indicating if a cracked wall has been destroyed
	private boolean wallDestroyed = false;
	
	// explosion timestamp variables
	private int timeStamp = 0;
	private static int MAX_TIME = 400;
	
	// boolean indicating if the level should be restarted
	public boolean restartLevel = false;
	
	// boolean indicating if the player has died
	public boolean killPlayer = false;
	
	// Sound variables
	private Sound finishedLevel;
	private Sound explosion;
	private Sound die;
	private Sound doorOpen;
	private Sound doorClose;
	
	// Music variables
	private Music music;
	private Music ending;
	
	// enemy units list
	public ArrayList<Enemy> enemies;
	
	// Image of Matt
	private Image Matt;
	
	// Hardcoded variables used to print Matt in the right locations
	private static final int START = 12;
	private static final int MAX = 612;
	private static final int TOP = 30;
	private static final int BOTTOM = 400;
	private static final int INCREMENT = 200;
	private int timeMatt = 0;
	private int duration = 100;
	
	public World() 
	throws SlickException {
		// Lets play some music and make this game interesting
    	music = new Music(IN_GAME_MUSIC);
    	music.setVolume(VOLUME);
    	music.loop();
    	
    	// Initialise first level
		newLevel();
	}
	
	/** Creates a new level in the World.
     * @return void
     */
	@SuppressWarnings("unchecked")
	public void newLevel() {
		
		// Concatenates appropriate level file
		String levelFile = LEVEL_FOLDER + String.valueOf(LEVEL) + 
				           LEVEL_EXTENSION;
			
		// Obtains map widths and heights
		mapSizes = Loader.loadMapSize(levelFile);
		MAP_WIDTH = mapSizes[WIDTH];
		MAP_HEIGHT = mapSizes[HEIGHT];
		
		// Obtains sprite arraylists
		defaultSprites = Loader.loadSprites(levelFile);
		sprites = defaultSprites;
		
		// World data obtained
		worldData = initialiseWorld();
		blocked = (boolean[][]) worldData[BLOCKED];
		blocks = (boolean[][]) worldData[BLOCKS];
		targets = (boolean[][]) worldData[TARGETS];
		crackedWallLocations = (ArrayList<Coordinates<Integer, Integer>>) 
				                worldData[CRACKED];
		enemies = (ArrayList<Enemy>) worldData[ENEMY];
		
		// Create sounds for the world
		try {
			finishedLevel = new Sound(FINISH_LEVEL_SOUND);
			explosion = new Sound(EXPLOSION_SOUND);
			die = new Sound(DIE_SOUND);
			doorOpen = new Sound(DOOR_OPEN_SOUND);
			doorClose = new Sound(DOOR_CLOSE_SOUND);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		// If game is finished
		if (LEVEL == MAX_LEVEL) {
			try {
				Matt = new Image(MATTS_FACE);
				ending = new Music(ENDING_MUSIC);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
			// Stop in game music
			music.stop();
			
			// Play ending music
			ending.setVolume(VOLUME);
			ending.loop();

		}
	}
	
	/** Checks if level is complete.
	 * Looks at the targets array and blocks array and checks whether both
	 * those locations are true, then adds to a counter. If the counter is
	 * equal to the number of targets on a level, then the level is complete.
     * @return boolean indicating if level is complete
     */
	public boolean isLevelComplete() {
		int coveredTargets = 0;
		for (int row = 0; row < MAP_HEIGHT; row++) {
			for (int col = 0; col < MAP_WIDTH; col++) {
				
				// If targets location is occupied and
				// blocks location is occupied, then target location
				// is covered
				if (targets[row][col] && blocks[row][col]) {
					coveredTargets++;
				} 
			}
		}
		
		if (coveredTargets == numTargets) {
			return true;
		}
		
		return false;
	}
	
	 /** Initialises data for a world.
	  * Aggregates multiple boolean arrays determining locations of blocks, 
	  * walls and targets. Also contains an arraylist of cracked wall
	  * locations. 
	  * @return an Object array containing multiple arrays and a arraylist
     */
	public Object[] initialiseWorld() {
		boolean[][] blocked = new boolean[MAP_HEIGHT][MAP_WIDTH];
		boolean[][] blocks = new boolean[MAP_HEIGHT][MAP_WIDTH];
		boolean[][] targets = new boolean[MAP_HEIGHT][MAP_WIDTH];
		
		// Split crackedWall from normal walls, because blocks can be pushed
		// onto them
		ArrayList<Coordinates<Integer, Integer>> crackedWallLocations = 
				              new ArrayList<Coordinates<Integer, Integer>>();
		
		ArrayList<Sprite> enemies = new ArrayList<Sprite>();
		
		// Go through all the sprites
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				float x = sprite.getHeight();
				float y = sprite.getWidth();
				
				// Determine type of sprite
				// Added door and walls together, as units/blocks cannot pass  
				// walls and cannot pass doors when closed.
				if (sprite instanceof Wall || sprite instanceof Door) {
					blocked[(int) x][(int) y] = true;
				} else if (sprite instanceof CrackedWall) {
					Coordinates<Integer, Integer> crackedWallLocation = 
							    Coordinates.newCoordinates((int)x, (int)y);
					crackedWallLocations.add(crackedWallLocation);
				} else if (sprite instanceof Pushable) {
					blocks[(int) x][(int) y] = true;
				} else if (sprite instanceof Target) {
					targets[(int) x][(int) y] = true;
					numTargets++;
				} else if (sprite instanceof Enemy) {
					enemies.add(sprite);
				}
			}
		}
		
		// Return the Object array
		return new Object[] {blocked, blocks, targets, 
				             crackedWallLocations, enemies};
	}
	
	/** Update the game state of the world.
	 * Also checks for explosions, enemy collisions, door opening and closing
	 * and whether a level should be restarted or is complete.
     * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void
     */
	public void update(Input input, int delta) 
	throws SlickException {
		
		// time stamp for ending
		if (LEVEL == MAX_LEVEL) {
			timeMatt += delta;
		}
		
		// Update every sprite
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.update(this, input, delta);
			}
		}
		
		// Checks explosion
		checkExplosion();
		if (wallDestroyed) {
			timeStamp += delta;
			explosion.play(PITCH, VOLUME);
			removeExplosion();
		}
		
		
		// Checks if door should be opened or closed
		lockOpenDoor();

		// Checks for enemy collisions and whether the level
		// should be restarted
		if (isEnemyCollision() || restartLevel || killPlayer) {
			updateLevel();
			restartLevel = false;
			killPlayer = false;
			die.play(PITCH, VOLUME);
		}
		
		// Checks if level is complete and that the next level is legal
		if (isLevelComplete() && LEVEL < MAX_LEVEL) {
			LEVEL++;
			updateLevel();
			finishedLevel.play();
		}

	}
	
	/** Updates the level in the world.
	 * This can either be the next level
     * @return void
     */
	public void updateLevel() {
		numTargets = 0;
		MOVE_COUNT = 0;
		newLevel();
	}
	
	/** Determines if the player has contacted the player.
     * @return boolean one whether a player has contacted an enemy.
     */
	public boolean isEnemyCollision() {
		for (Sprite sprite : sprites) {
			if (sprite != null && sprite instanceof Enemy) {
				if (((Enemy) sprite).isContactPlayer(this)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** Checks a tnt block as hit a cracked wall.
	 * If a tnt block intercepts a cracked wall, then the crackedWall tile is
	 * destroyed, and removed from the sprite array, along with the tnt block.
     * @return void
     */
	public void checkExplosion() {
		
		// Items to be destroyed
		// Chose to use an arraylist as items are easier to delete
		ArrayList <Sprite> toDestroy = new ArrayList<Sprite>();
		
		// Coordinates of crackedWall/tnt
		Coordinates<Integer, Integer> tntLoc = null;

		boolean explode = false;
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				
				// If sprite is a TNT
				if(sprite instanceof Tnt) {
					
					// Go through the cracked wall locations 
					for (Coordinates<Integer, Integer> loc : 
						                               crackedWallLocations) {
						
						// if the Tnt hits a cracked wall, add it to the 
						//arraylist of items to destroy
						if (((Tnt) sprite).checkLocation(loc.getY(), 
								                         loc.getX())) {
							explode = true;
							toDestroy.add(sprite);
							tntLoc = loc;
						}
					}
					
				// When cracked wall is found, and it contains the tnt block,
				// Add it to the list of items to be destroyed
				} else if (sprite instanceof CrackedWall && 
						   tntLoc != null && 
						   ((CrackedWall) sprite).hasBlock(tntLoc.getY(), 
								                           tntLoc.getX())) {
					
							toDestroy.add(sprite);
				}
			}
		}
		
		// Checks if explosion is possible
		if (explode && toDestroy.size() == NUM_COORDS && tntLoc != null) {
			
			// Remove the cracked wall and tnt from the sprite list
			for (Sprite sprite : toDestroy) {
				sprites.remove(sprite);
			}
			
			// Set the previous location of the tnt to null
			// This is to ensure the player can walk through here
			blocks[tntLoc.getY()][tntLoc.getX()] = false;
			
			// Attempt to add an explosion to the sprite list
			try {
				sprites.add(new Explosion((float)tntLoc.getX(), 
						                  (float)tntLoc.getY()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
			// Remove the cracked wall
			crackedWallLocations.remove(tntLoc);
			
			wallDestroyed = true;
		} 

	}
	
	/** Locks or opens door within level.
	 * If the switch is activated, open the door, otherwise close the door.
     * @return void
     */
	public void lockOpenDoor() {
		for (Sprite sprite : sprites) {
			if (sprite != null && sprite instanceof Door) {
				
				// If switch is activated
				if (checkSwitch()) {
					
					// Open the door
					((Door) sprite).openDoor(this);
					if (!((Door) sprite).doorOpen) {
						doorOpen.play(PITCH, VOLUME);
						((Door) sprite).doorOpen = true;
					}
					
				// Lock the door
				} else {
					((Door) sprite).lockDoor(this);
					if (((Door) sprite).doorOpen) {
						doorClose.play(PITCH, VOLUME);
						((Door) sprite).doorOpen = false;
					}

				}
			}
		}

	}
	
	/** Checks if the Switch is active.
     * @return boolean indicating if switch is active.
     */
	public boolean checkSwitch() {
		for (Sprite sprite : sprites) {
			if (sprite != null && sprite instanceof Switch) {
				if (((Switch) sprite).isActive(this)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/** Removes explosion from sprites array when 0.4 second has passed.
	 * Sets the timestamp back to zero ready for next explosion.
     * @return void
     */
	public void removeExplosion() {
		Sprite explosion = null;
		if (timeStamp >= MAX_TIME) {
			for (Sprite sprite : sprites) {
				if (sprite != null && sprite instanceof Explosion) {
					explosion = sprite;
				}
			}
			
			// Remove the explosion from the sprite list
			if (explosion != null) {
				sprites.remove(explosion);
			}
			wallDestroyed = false;
			timeStamp = 0;
		}
	}
	
	/** Gets the Player object from the sprite list.
     * @return Player object.
     */
	public Player getPlayer() {
		for (Sprite sprite : sprites) {
			if (sprite != null && sprite instanceof Player) {
				return (Player) sprite;
			}
		}
		return null;
	}
	
	/** Gets the Rogue from the enemies list.
     * @return Rogue object.
     */
	public Rogue getRogue() {
		for (Enemy sprite : enemies) {
			if (sprite != null && sprite instanceof Rogue) {
				return (Rogue) sprite;
			}
		}
		return null;
	}
	
	/** Gets the Skeleton from the enemies list.
     * @return Skeleton object.
     */
	public Skeleton getSkeleton() {
		for (Enemy sprite : enemies) {
			if (sprite != null && sprite instanceof Skeleton) {
				return (Skeleton) sprite;
			}
		}
		return null;
	}
	
	/** Returns True if tile is a Wall or Door, false otherwise.
     * @param x The tile location in the x direction.
     * @param y The tile location in the y direction.
     * @return boolean.
     */
	public boolean isBlocked(float x, float y) {
		return blocked[(int) x][(int) y];
	}
	
	
	/** Returns True if tile is a block, false otherwise.
     * @param x The block location in the x direction.
     * @param y The block location in the y direction.
     * @return boolean.
     */
	public boolean isBlock(float x, float y) {
		return blocks[(int) x][(int) y];
	}
	
	/** Returns True if tile is a crackedWall, false otherwise.
     * @param x The cracked wall location in the x direction.
     * @param y The cracked wall location in the y direction.
     * @return boolean.
     */
	public boolean isCracked(float x, float y) {
		
		// Go through the cracked wall list
		for (Coordinates<Integer, Integer> loc : crackedWallLocations) {
			if ((int)x == loc.getX() && 
			   (int)y == loc.getY()) {
				return true;
			}
		}
		
		return false;
	}

	/** Render the entire world.
	 * Also handles if the door should be rendered.
	 * Also draws number of moves in the top left corner of the screen.
     * @param g The Slick graphics object, used for drawing.
     * @return void
     */
	public void render(Graphics g) {

		for (Sprite sprite : sprites) {
			if (sprite != null) {
				if (sprite instanceof Door) {
					if (!checkSwitch()) {
						sprite.render(this, g);
					}
				} else {
					sprite.render(this, g);
				}
			}
		}
		
		// Print out moves
		if (LEVEL < MAX_LEVEL) {
			g.drawString("Moves:" + String.valueOf(MOVE_COUNT), 11, -3);
		}
		
		// If game finished, render Matt onto the screen
		if (LEVEL == MAX_LEVEL) {
			if (timeMatt >= duration) {
				for (int i = START; i <= MAX; i += INCREMENT) {
					Matt.draw(i, TOP);
					Matt.draw(i, BOTTOM);
				}
				timeMatt = 0;
			}

		}

	}

	/** updates history of the player and blocks in the world. 
	 * Goes through the block locations, and if they are true, then push the
	 * locations onto the block history stacks. Same procedure for the player.
     * @return void
     */
	public void updateHistory() {
		for (int row = 0; row < MAP_HEIGHT; row++) {
			for (int col = 0; col < MAP_WIDTH; col++) {
				
				// if a block is at this location
				if (blocks[row][col]) {
					
					// Go through the sprites and find all the pushable blocks
					for (Sprite sprite : sprites) {
						
						// if location is also a pushable block, push location
						// onto the history stack
						if (sprite instanceof Pushable && 
						  ((Pushable) sprite).checkLocation(col, row)) {
							
							// Add location to the stack
							((Pushable) sprite).history.push(
						    Coordinates.newCoordinates((float)col, (float)row));
						}
					}
				} 
				
				// If the player is at this location, do the same
				if (getPlayer().checkLocation(col, row)) {
					
					getPlayer().history.push(
					Coordinates.newCoordinates((float)col, (float)row));
				}
			}
		}
	}

	/** undos all the Pushable block objects.
	 * Goes through all the pushable block objects and undoes their moves.
     * @return void
     */
	public void undoPushables() {
		for (Sprite sprite : sprites) {
			if (sprite instanceof Pushable) {
				
				// If history is not empty
				if (!(((Pushable) sprite).history).isEmpty()) {
					
					// Pop history from stack
					Coordinates<Float, Float> topBlockHistory = 
							                 ((Pushable) sprite).history.pop();
						
					float blockXHistory = topBlockHistory.getX();
					float blockYHistory = topBlockHistory.getY();
					
					// Reset location
					sprite.setWidth(blockXHistory);
					sprite.setHeight(blockYHistory);
					
					// Reset Ice block if possible
					if (sprite instanceof Ice) {
						((Ice) sprite).setDestinationX(blockXHistory);
						((Ice) sprite).setDestinationY(blockYHistory);
					}
				}
			}
		}
	}
	
	/** Resets blocks array, filling in true in locations that currently
	 * are occupied, and false in locations that contain no block
     * @return void
     */
	public void resetBlocks() {
		for (Sprite sprite : sprites) {
			float x = sprite.getHeight();
			float y = sprite.getWidth();
			if (sprite instanceof Pushable) {
				blocks[(int)x][(int)y] = true;
			} else {
				blocks[(int)x][(int)y] = false;
			}
		}
	}
}




