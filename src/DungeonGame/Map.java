package DungeonGame;

import java.util.Scanner;
import javax.swing.JFrame;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Lead Author(s):
 * 
 * @author Stephen Maule ;0005782489
 * @author
 *         <<add additional lead authors here, with a full first and last name>>
 * 
 *         Other contributors:
 *         <<add additional contributors (mentors, tutors, friends) here, with
 *         contact information>>
 * 
 *         References:
 *         Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented
 *         Problem Solving.
 *         Retrieved from
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 *         https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
 * 
 *         <<add more references here>>
 *         https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
 * 
 *         Version/date: 5/27/2023
 * 
 *         Responsibilities of class:
 *         keep track of combat and defence. Keep track of movement and paths,
 *         Keep track of button triggers
 */

// Map has a player, a path, and a display
public class Map
{
	// Map Has a player
	private Player currentPlayer; // Player object
	// Map Has a path
	private Path currentPath; // Level Path object
	// Map Has a scaling damage and level
	private int scalingDamage = 1, gameLevel = 0; // Increasing difficulty and
													// current level
	// Map has Triggers for each state
	private boolean attack = false, defend = false, left = false, right = false,
			straight = false, pickUpItem = false, discardItem = false,
			loadBlock = false, canCombat = false, canInteract = false,
			canMove = false, turnOrder = true;
	// Map Has a display
	Display screen = new Display(this);

	/**
	 * Constructor for map, initializing player object and first floor
	 */
	Map()
	{

		currentPlayer = new Player();
		generateNextFloor();
		// Init Over all Jframe options
		screen.setSize(500, 500);
		screen.setTitle("DUNGEON GAME!");
		screen.setResizable(false);
		screen.setVisible(true);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Promt for past save loading
		screen.updateConsole(
				"Press Load to Load A Save! Press A Move Option to Start A New Game!");
		screen.updatePlayer(currentPlayer.getHP(), currentPlayer.defended());
		screen.updateEnemy(0, false);
	}

	/**
	 * Generates new Random floor
	 */
	private void generateNextFloor()
	{
		// new floor with scaled damage factor
		currentPath = new Path(scalingDamage);
		// increase difficulty scale
		scalingDamage++;
		// Scale player
		currentPlayer.levelUp();
	}

	/**
	 * Determine what to do if the path is an item or an enemy
	 * 
	 * @param Entity in question
	 */
	private void pathOutcome(Entity pathOutcome)
	{
		// If the path is an enemy, open combat buttons and cycle turns until
		// either player or enemy is dead
		if (pathOutcome.ID.contains("Enemy"))
		{
			canCombat = true;
			screen.updateConsole("Player Turn: Attack ? Defend ");
			while (!combatSystem((Enemy) pathOutcome))
			{
			}
			canCombat = false;
		}
		// If the path is an item then run the item interaction for Picking up
		// or discarding
		if (pathOutcome.ID.contains("Item"))
		{
			itemInteraction((Item) pathOutcome);// Cast because we know it is an
												// Item
		}
	}

	/**
	 * Determine to pick up or drop the new item
	 * 
	 * @param Item for interaction
	 */
	private void itemInteraction(Item item)
	{
		// Communicate item information and Promt user to drop or pick up
		screen.updateConsole("You Found >" + item.getName() + "<"
				+ " Would you like to pick it up?");
		// Open item interaction buttons
		canInteract = true;
		while (pickUpItem == false && discardItem == false)
		{
			// if player chooses to pick up item
			if (pickUpItem == true)
			{
				currentPlayer.swapWeapon(item);
				screen.updateConsole("You Picked up >" + item.getName() + "<!");
			}
			// if player chooses to discard item
			else if (discardItem == true)
			{
				screen.updateConsole("Dropping...");
			}
			else
			{
				screen.updateConsole("You Found >" + item.getName() + "<"
						+ " Would you like to pick it up?");
			}
		}
		// reset buttons
		discardItem = false;
		pickUpItem = false;
		// Close item interaction
		canInteract = false;
	}

	/**
	 * Handles the Combat System between player and enemy
	 * 
	 * @param Enemy for combat
	 * 
	 * @return Name of dead entity
	 */
	private boolean combatSystem(Enemy enemy)
	{
		Random random = new Random(); // Randomizor object
		// Update player and enemy stats for begining of round
		screen.updateEnemy(enemy.getHealth(), enemy.defended());
		screen.updatePlayer(currentPlayer.getHP(), currentPlayer.defended());
		if (turnOrder) // Player turn
		{
			canCombat = true; // Enable player input
			while (canCombat) // Loop until successful player input
			{
				screen.updateConsole("Player Turn: Attack ? Defend "); // Promt
																		// user
				if (attack) // Attack button
				{
					if (!enemy.defended()) // If the enemy cant block deal
											// damage
					{
						enemy.updateHP(-currentPlayer.attack());
						screen.updateEnemy(enemy.getHealth(), enemy.defended());
					}
					if (currentPlayer.defended())// if the player defended on
													// their last turn, end
													// their defence
					{
						currentPlayer.defend();
						screen.updatePlayer(currentPlayer.getHP(),
								currentPlayer.defended());
					}
					attack = false; // Reset Attack button
					canCombat = false; // Break out of waiting for button
				}
				else if (defend) // Defend button
				{
					if (!currentPlayer.defended()) // If player isnt already
													// defended then defend
					{
						currentPlayer.defend();
						screen.updatePlayer(currentPlayer.getHP(),
								currentPlayer.defended());
						canCombat = false;// Break out of waiting for button
					}
					defend = false;
				}
			}
			turnOrder = false; // Switch to enemy
		}
		else // Enemy Turn
		{
			boolean randOption = random.nextBoolean(); // True == Intent to
														// attack, False ==
														// Intent to defend
			if (!enemy.defended() && !randOption)// intent to defend
			{
				enemy.defend();
			}
			else // If Intent to attack or cant defend
			{
				if (!currentPlayer.defended()) // deal damage if the player cant
												// block
				{
					currentPlayer.updateHP(-enemy.attack());
					screen.updatePlayer(currentPlayer.getHP(),
							currentPlayer.defended());
				}
				if (enemy.defended()) // if the enemy defended last turn then
										// let them next time
				{
					enemy.defend();
					screen.updateEnemy(enemy.getHealth(), enemy.defended());
				}
			}

			turnOrder = true; // Switch to player turn
		}

		if (enemy.getHealth() <= 0)
		{ // If enemy HP is below 0 enemy is dead
			screen.updateConsole(enemy.ID + " Has been Defeated");
			screen.updatePlayer(currentPlayer.getHP(),
					currentPlayer.defended());
			screen.updateEnemy(0, false);
			return true; // end combat loop
		}
		else if (currentPlayer.getHP() <= 0)
		{// If player HP is below 0 player is dead
			screen.updateConsole("Game Over! " + currentPlayer.ID
					+ " Has been Defeated!" + " Close to exit.");
			screen.updatePlayer(0, currentPlayer.defended());
			screen.updateEnemy(enemy.getHealth(), false);
			while (true)
			{
				// Loop until player closes
			}
		}
		else
		{
			return false; // continue combat loop
		}
	}

	/**
	 * Promts player input for new level generation
	 */
	public boolean roundPlayerInput()
	{
		// Update Health value
		screen.updatePlayer(currentPlayer.getHP(), currentPlayer.defended());
		// print out dynamic map
		screen.updateMap(currentPath.printMap());
		// Open Movement buttons
		canMove = true;
		// wait for path decision if the player should be able to move
		while (canMove)
		{
			if (left)
			{
				left = false;// reset all path buttons to false
				right = false;
				straight = false;
				loadBlock = true; // If player gives input stop allowing to load
									// game
				canMove = false;// Stop accepting Movement input
				pathOutcome(currentPath.getPaths().get(0));
			}
			else if (straight)
			{
				left = false;// reset all path buttons to false
				right = false;
				straight = false;
				loadBlock = true; // If player gives input stop allowing to load
									// game
				canMove = false;// Stop accepting Movement input
				pathOutcome(currentPath.getPaths().get(1));
			}
			else if (right)
			{
				left = false; // reset all path buttons to false
				right = false;
				straight = false;
				loadBlock = true; // If player gives input stop allowing to load
									// game
				canMove = false; // Stop accepting Movement input
				pathOutcome(currentPath.getPaths().get(2));
			}
			else
			{
				screen.updatePlayer(currentPlayer.getHP(),
						currentPlayer.defended());
			}
		}
		// increases current level
		gameLevel++;
		screen.updateConsole(
				"Level " + gameLevel + "! Which Path do you want to go down?");
		// generate next floor
		generateNextFloor();
		return true;
	}

	/**
	 * Saves all relevant data in specific format for reading
	 * 
	 * @throws IOException
	 *                     File Data Order:
	 *                     game level
	 *                     scaling damage
	 *                     player item Name
	 *                     player item damage
	 *                     player health
	 *                     player level
	 * 
	 */
	public void saveGame() throws IOException
	{
		PrintWriter save = null;
		File sanityCheck = new File("save.txt");
		if (!sanityCheck.exists())
		{
			throw new IOException();
		}
		try
		{
			// save all relevant data in order
			save = new PrintWriter("save.txt");
			save.println(gameLevel);
			save.println(scalingDamage);
			save.println(currentPlayer.getHeldWeapon().getName());
			save.println(currentPlayer.getHeldWeapon().getDamage());
			save.println(currentPlayer.getHP());
			save.println(currentPlayer.getLevel());
			// close resources
		}
		catch (IOException e)
		{
			screen.updateConsole("Game save error...");
		}
		finally
		{
			save.close();
		}
	}

	/**
	 * Loads the relevant information for a game from the save format
	 */
	public void loadGame()
	{
		// If the player should be able to load
		if (!loadBlock) // True = cant load, false == can load
		{
			// File Objects and scanners
			Scanner fileIn = null;
			Item playerStartItem = new Item();
			// try to open file
			try
			{
				fileIn = new Scanner(new File("save.txt"));
			} // Catch error and handle
			catch (IOException e)
			{
				screen.updateConsole(
						"save.txt Not Found... Starting new Game. ");
				return;
			}
			// try to grab data from file and populate variables
			try
			{
				gameLevel = Integer.parseInt(fileIn.next());
				scalingDamage = Integer.parseInt(fileIn.next());
				playerStartItem.setName(fileIn.next());
				playerStartItem.setDamage(Integer.parseInt(fileIn.next()));
				int hp = Integer.parseInt(fileIn.next());
				int level = Integer.parseInt(fileIn.next());
				currentPlayer = new Player(level, hp, playerStartItem);
			}
			// Catch error and handle
			catch (NumberFormatException e)
			{
				screen.updateConsole("File Read Error... Starting new Game.");
				gameLevel = 0;
				scalingDamage = 1;
				currentPlayer = new Player(1, 5, new Item());
			}
			catch (NoSuchElementException e)
			{
				screen.updateConsole("File Read Error... Starting new Game.");
				gameLevel = 0;
				scalingDamage = 1;
				currentPlayer = new Player(1, 5, new Item());
			}
			finally
			{
				fileIn.close();
				screen.updatePlayer(currentPlayer.getHP(),
						currentPlayer.defended());
				screen.updateEnemy(0, false);
				screen.updateConsole("Starting at Level " + gameLevel + "!");
				loadBlock = true;
			}
		}
	}

	/**
	 * Attempts to use the attack button
	 */
	public void attack()
	{
		if (canCombat)
		{

			attack = true;
		}
	}

	/**
	 * Attempts to use the defend button
	 */
	public void defend()
	{
		if (canCombat)
		{
			defend = true;
		}
	}

	/**
	 * Attempts to use the pickup button
	 */
	public void pickUp()
	{
		if (canInteract)
		{
			pickUpItem = true;
		}
	}

	/**
	 * Attempts to use the discard button
	 */
	public void discard()
	{
		if (canInteract)
		{
			discardItem = true;
		}
	}

	/**
	 * Attempts to use the left button
	 */
	public void moveLeft()
	{
		if (canMove)
		{
			left = true;
		}
	}

	/**
	 * Attempts to use the right button
	 */
	public void moveRight()
	{
		if (canMove)
		{
			right = true;
		}
	}

	/**
	 * Attempts to use the straight button
	 */
	public void moveStraight()
	{
		if (canMove)
		{
			straight = true;
		}
	}
}
