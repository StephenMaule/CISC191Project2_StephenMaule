package DungeonGame;

import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
 *         Version/date: 5/22/2023
 * 
 *         Responsibilities of class:
 *         keep track of combat and defence. Keep track of movement and paths
 */

// Map has a player and a path
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
			load = false, canCombat = false, canInteract = false,
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
		screen.setSize(500, 500);
		screen.setTitle("DUNGEON GAME!");
		screen.setResizable(false);
		screen.setVisible(true);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Promt for past save loading
		screen.updateConsole(
				"Press Load to Load A Save! Press Discard to Start A New Game!");
		screen.updatePlayer(currentPlayer.getHP());
		screen.updateEnemy(0);
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

		if (pathOutcome.ID.contains("Enemy"))
		{
			canCombat = true;
			while (!combatSystem((Enemy) pathOutcome))
			{
			}
			canCombat = false;
		}
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
		// Communicate item information
		screen.updateConsole("You Found >" + item.getName() + "<"
				+ " Would you like to pick it up?");
		// Promt user to drop or pick up
		canInteract = true;
		// If the player should be able to Interact with item
		while (pickUpItem == false && discardItem == false)
		{
			if (pickUpItem == true)
			{
				currentPlayer.swapWeapon(item);
				screen.updateConsole("You Picked up >" + item.getName() + "<!");
			}
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
		discardItem = false;
		pickUpItem = false;
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

		if (turnOrder)
		{ // If it is the players turn
			screen.updateConsole("Player Turn: Attack ? Defend ");
			do
			{
				if (attack == true)
				{ // When Attacking attempt to attack, if enemy is defending
					// it is blocked, otherwise deal damage
					if (currentPlayer.defended())
					{
						currentPlayer.defend();
					}
					screen.updateConsole("Player Attacks!");
					if (!enemy.defended())
					{
						enemy.updateHP(-currentPlayer.attack());
						screen.updateConsole(
								currentPlayer.getHeldWeapon().getDamage()
										+ " Damage! " + enemy.getHealth()
										+ " Remaining! ");
						screen.updateConsole(
								"Player Attack" + enemy.getHealth());
					}
					else
					{
						screen.updateConsole("Blocked!");
						if (enemy.defended())
						{
							enemy.defend();
						}
						screen.updateConsole(
								"Player atack but defend" + enemy.getHealth());
					}
					attack = false;
					turnOrder = false;
				}
				if (defend == true && !currentPlayer.defended())
				{ // If Defending and is not already defended then defend
					screen.updateConsole("Player Defends!");
					currentPlayer.defend();
					defend = false;
					turnOrder = false;
					screen.updateConsole("Player Defend" + enemy.getHealth());
				}
			} while (attack == false && defend == false
					&& (defend == true || currentPlayer.defended()));
		}
		else
		{
			screen.updateConsole("Enemy Turn!");
			boolean randOption = random.nextBoolean();
			if (randOption == false && enemy.defended() == false)
			{ // If Defending and is not already defended then defend
				screen.updateConsole("Enemy Defends!");
				enemy.defend();
				turnOrder = true;
			}
			if (randOption == true)
			{// When Attacking attempt to attack, if player is defending it
				// is blocked, otherwise deal damage
				if (enemy.defended())
				{
					enemy.defend();
				}
				if (!currentPlayer.defended())
				{
					currentPlayer.updateHP(-enemy.attack());
					screen.updateConsole("Enemy Attacks! "
							+ enemy.getWeapon().getDamage() + " Damage! "
							+ currentPlayer.getHP() + " Remaining! ");
				}
				else
				{
					screen.updateConsole("Blocked!");
				}
				if (currentPlayer.defended())
				{
					currentPlayer.defend();
				}
				turnOrder = true;
			}

		}

		if (enemy.getHealth() <= 0)
		{ // If enemy HP is below 0 enemy is dead
			screen.updateConsole(enemy.ID + " Has been Defeated");
			screen.updateConsole("Enemy loose" + enemy.getHealth());
			screen.updatePlayer(currentPlayer.getHP());
			screen.updateEnemy(enemy.getHealth());
			return true;
		}
		else if (currentPlayer.getHP() <= 0)
		{// If player HP is below 0 player is dead
			screen.updateConsole(currentPlayer.ID
					+ " Has been Defeated! Game Over!" + "Close to exit.");
			screen.updatePlayer(currentPlayer.getHP());
			screen.updateEnemy(enemy.getHealth());
			while (true)
			{
				// Loop until player closes
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Promts player input for new level generation
	 */
	public boolean roundPlayerInput()
	{
		//Update Health value 
		screen.updatePlayer(currentPlayer.getHP());
		// Prompt Movement
		screen.updateConsole(
				"Level " + gameLevel + "! Which Path do you want to go down?");
		// print out dynamic map
		screen.updateMap(currentPath.printMap());
		canMove = true;
		// wait for path decision if the player should be able to move
		while (canMove)
		{
			if (left)
			{
				pathOutcome(currentPath.getPaths()[0]);
				left = false;
				canMove = false;
			}
			else if (straight)
			{
				pathOutcome(currentPath.getPaths()[1]);
				straight = false;
				canMove = false;
			}
			else if (right)
			{
				pathOutcome(currentPath.getPaths()[2]);
				right = false;
				canMove = false;
			}
			else
			{
				screen.updateConsole("Level " + gameLevel
						+ "! Which Path do you want to go down?");
			}
		}
		// increases current level
		gameLevel++;
		screen.updateConsole("Next Level: " + gameLevel + "!");
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
		if (load == false)
		{
			// File Objects and scanners
			Scanner fileIn = null;
			Item playerStartItem = new Item();
			// try to open file
			try
			{
				screen.updateConsole(
						"Attempting to load " + "save.txt" + "...");
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
				currentPlayer = new Player(hp, level, playerStartItem);
			}
			// Catch error and handle
			catch (NumberFormatException e)
			{
				screen.updateConsole("File Read Error... Starting new Game.");
				gameLevel = 0;
				scalingDamage = 1;
				currentPlayer = new Player(5, 1, new Item());
			}
			catch (NoSuchElementException e)
			{
				screen.updateConsole("File Read Error... Starting new Game.");
				gameLevel = 0;
				scalingDamage = 1;
				currentPlayer = new Player(5, 1, new Item());
			}
			finally
			{
				fileIn.close();
				screen.updatePlayer(currentPlayer.getHP());
				load = true;
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
		if (!defend && canCombat)
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
