/* SWEN20003 Object Oriented Software Development 2017
 * Project 1
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Loader class which handles
 * the loading of the game map from  file
 */

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;


/**
 * Loader class for the game.
 * Handles loading of game map from 0.lvl.
 */
public class Loader {	
	
	// delimiter of csv file
	private static final String CSV_DELIMITER = ",";
	
	// Array size of map sizes
	public static final int MAPSIZE = 2;

	// Indexes of map sizes
	public static final int X = 0;
	public static final int Y = 1;
	
	// Locations of tile data
	private static final int TYPE_LOC = 0;
	private static final int WIDTH_LOC = 1;
	private static final int HEIGHT_LOC = 2;
	
	// Strings used to compare what tile was read
	private static final String FLOOR = "floor";
	private static final String WALL = "wall";
	private static final String STONE = "stone";
	private static final String TARGET = "target";
	private static final String PLAYER = "player";
	private static final String TNT = "tnt";
	private static final String ICE = "ice";
	private static final String SKELETON = "skeleton";
	private static final String ROGUE = "rogue";
	private static final String MAGE = "mage";
	private static final String CRACKED_WALL = "cracked";
	private static final String SWITCH = "switch";
	private static final String DOOR = "door";
	
	/** Returns array containing map sizes
     * @param filename The file containing game map
     * @return integer array of map sizes
     */
	public static int[] loadMapSize(String filename) {
		int[] mapSizes = new int[MAPSIZE];
		
		// Read the file
		try (BufferedReader br =
		     new BufferedReader(new FileReader(filename))) {
				
			// Read the first line
			 String text = br.readLine();
			 
			 // Parse csv line by comma
			 String[] columns = text.split(CSV_DELIMITER);

			 // Convert map sizes to integers
			 mapSizes[X] = Integer.parseInt(columns[X]);
			 
			 mapSizes[Y] = Integer.parseInt(columns[Y]);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapSizes;
	}
		
	/** Returns array containing sprites
     * @param filename The file containing game map
     * @return arrayList of sprites
     */
	public static ArrayList<Sprite> loadSprites(String filename) {
		
		// Initially using arraylist to store sprites
		// easier to add sprites one at a time
		ArrayList<Sprite> spritesList = new ArrayList<Sprite>();
		
		// Reading the file 
		try (BufferedReader br =
			 new BufferedReader(new FileReader(filename))) {
			
			// Consume first line of file
			// Only concerned with tile data after the first line
			String text = br.readLine();
			
			// Read the tile data until EOF
			while ((text = br.readLine()) != null) {
				
				// parse csv line by comma
				String[] columns = text.split(CSV_DELIMITER);
				
				// Convert the x and y tile coordinates to floats
				float tileX = Float.parseFloat(columns[WIDTH_LOC]);
				float tileY = Float.parseFloat(columns[HEIGHT_LOC]);
				
				String spriteType = columns[TYPE_LOC];
				
				// add sprite to sprites to array list
				addSprite(spritesList, spriteType, tileX, tileY);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return spritesList;
	}
	
	/** addes sprites to array list
     * @param spriteslist The array list of sprites
     * @param sprite The sprite to be compared
     * @param tileX The width coordninate of the sprite
     * @param tileY The height coordinate of the sprite
     * @return void
     */
	public static void addSprite(ArrayList<Sprite> spritesList, String sprite,
			                     float tileX, float tileY) 
	throws SlickException {
		
		// Checking all possible tiles
		// Adds sprite extended objects when found
		switch(sprite) {
			case FLOOR:
				spritesList.add(new Floor(tileX, tileY));
				break;
			case WALL:
				spritesList.add(new Wall(tileX, tileY));
				break;
			case STONE:
				spritesList.add(new Stone(tileX, tileY));
				break;
			case TARGET:
				spritesList.add(new Target(tileX, tileY));
				break;
			case PLAYER:
				spritesList.add(new Player(tileX, tileY));
				break;
			case TNT:
				spritesList.add(new Tnt(tileX, tileY));
				break;
			case ICE:
				spritesList.add(new Ice(tileX, tileY));
				break;
			case SKELETON:
				spritesList.add(new Skeleton(tileX, tileY));
				break;
			case ROGUE:
				spritesList.add(new Rogue(tileX, tileY));
				break;
			case MAGE:
				spritesList.add(new Mage(tileX, tileY));
				break;
			case CRACKED_WALL:
				spritesList.add(new CrackedWall(tileX, tileY));
				break;
			case SWITCH:
				spritesList.add(new Switch(tileX, tileY));
				break;
			case DOOR:
				spritesList.add(new Door(tileX, tileY));
				break;
		}
	}
	
}
