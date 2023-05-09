package DungeonGame;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
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
 *         Version/date: 5/1/2023
 * 
 *         Responsibilities of class:
 *         keep track of combat and defence. Keep track of movement and paths
 */

// Map has a player and a path
public class Map
{
	// Map Has a input
	Scanner userInput = new Scanner(System.in); // input object
	// Map Has a player
	private Player currentPlayer; // Player object
	// Map Has a path
	private Path currentPath; // Level Path object
	// Map Has a scaling damage and level
	private int scalingDamage = 1, gameLevel = 0; // Increasing difficulty and
													// current level

	/**
	 * Constructor for map, initializing player object and first floor
	 */
	Map()
	{
		currentPlayer = new Player();
		generateNextFloor();
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
			String loser = combatSystem((Enemy) pathOutcome);
			System.out.println(loser + " Has been Defeated"); // Cast
																// because
																// we
																// know
																// it
																// is
																// an
																// Enemy
			if (loser.contains(currentPlayer.ID))
			{
				System.out.println("Game Over!");
				userInput.close();
				System.exit(0);
			}
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
		System.out.println("You Found >" + item.getName() + "<");
		System.out.println("Would you like to pick it up Yes ? No");
		String nextLine = userInput.next();
		// Promt user to drop or pick up

		switch (nextLine.toLowerCase())
		{
			case "yes":
				currentPlayer.swapWeapon(item);
				System.out.println("You Picked up >" + item.getName() + "<!");
				break;
			case "no":
				System.out.println("Dropping...");
				break;
			default:
				nextLine = userInput.next();
		}
	}

	/**
	 * Handles the Combat System between player and enemy
	 * 
	 * @param Enemy for combat
	 * 
	 * @return Name of dead entity
	 */
	private String combatSystem(Enemy enemy)
	{
		Random random = new Random(); // Randomizor object
		boolean turnOrder = true; // Turn order, True = Player, False = Enemy
		String nextLine = "";
		while ((enemy.getHealth() >= 1) && (currentPlayer.getHP() >= 1))
		{
			if (turnOrder)
			{ // If it is the players turn
				System.out.println("Player Turn: Attack ? Defend ");
				do
				{
					nextLine = userInput.nextLine();
					if (nextLine.toLowerCase().contains("attack"))
					{ // When Attacking attempt to attack, if enemy is defending
						// it is blocked, otherwise deal damage
						if (currentPlayer.defended())
						{
							currentPlayer.defend();
						}
						System.out.println("Player Attacks!");
						if (!enemy.defended())
						{
							enemy.updateHP(-currentPlayer.attack());
							System.out.println(
									currentPlayer.getHeldWeapon().getDamage()
											+ " Damage! " + enemy.getHealth()
											+ " Remaining! ");
						}
						else
						{
							System.out.println("Blocked!");
							if (enemy.defended())
							{
								enemy.defend();
							}
						}
						turnOrder = !turnOrder;
					}
					if (nextLine.toLowerCase().contains("defend")
							&& !currentPlayer.defended())
					{ // If Defending and is not already defended then defend
						System.out.println("Player Defends!");
						currentPlayer.defend();
						turnOrder = !turnOrder;
					}
				} while (!nextLine.toLowerCase().contains("attack")
						|| !nextLine.toLowerCase().contains("defend")
								&& currentPlayer.defended());
			}
			else
			{
				System.out.println("Enemy Turn!");
				boolean randOption = random.nextBoolean();
				if (randOption == false && enemy.defended() == false)
				{ // If Defending and is not already defended then defend
					System.out.println("Enemy Defends!");
					enemy.defend();
					turnOrder = !turnOrder;
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
						System.out.println("Enemy Attacks! "
								+ enemy.getWeapon().getDamage() + " Damage! "
								+ currentPlayer.getHP() + " Remaining! ");
					}
					else
					{
						System.out.println("Blocked!");
					}
					if (currentPlayer.defended())
					{
						currentPlayer.defend();
					}
					turnOrder = !turnOrder;
				}

			}
		}
		if (enemy.getHealth() <= 0)
		{ // If enemy HP is below 0 enemy is dead
			return enemy.ID;
		}
		else
		{// If player HP is below 0 player is dead
			return currentPlayer.ID;
		}
	}

	/**
	 * Promts player input for new level generation
	 */
	public boolean roundPlayerInput()
	{
		String input = ""; // player input data

		// Player input value
		System.out.println(
				"Would you like to exit the dungeon? (>exit< to exit)");
		input = userInput.next();
		// if the input is exit, then do not proceed
		if (input.toLowerCase().contains("exit"))
		{
			return false;
		}
		System.out.println("Level " + gameLevel + "!");
		System.out.println("Which Path do you want to go down?");
		System.out.println("Left : Straight : Right");
		// print out dynamic map
		currentPath.printMap();
		// promt for path decision
		input = userInput.next();
		switch (input.toLowerCase())
		{
			case "left":
				pathOutcome(currentPath.getPaths()[0]);
				break;
			case "straight":
				pathOutcome(currentPath.getPaths()[1]);
				break;
			case "right":
				pathOutcome(currentPath.getPaths()[2]);
				break;
		}
		// increases current level
		gameLevel++;
		System.out.println("Next Level: " + gameLevel + "!");
		// generate next floor
		generateNextFloor();
		return true;
	}

	/**
	 * Promts player to save game before closing
	 */
	public boolean doSave()
	{
		// Promt user for save
		System.out.println("Would you like to save your game? yes/no");
		String input = userInput.next();
		// return true if promted
		switch (input.toLowerCase())
		{
			case "yes":
				// Increase game level for next start
				gameLevel++;
				userInput.close();
				return true;
			case "no":
				userInput.close();
				return false;
			default:
				input = userInput.next();
		}
		userInput.close();
		return false;
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
			System.out.println("Game save error, exiting...");
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
		// File Objects and scanners
		Scanner fileIn = null;
		Item playerStartItem = new Item();
		// try to open file
		try
		{
			System.out.println("Attempting to load " + "save.txt" + "...");
			fileIn = new Scanner(new File("save.txt"));
		} // Catch error and handle
		catch (IOException e)
		{
			userInput.close();
			System.out.println("save.txt" + " Not Found.");
			System.exit(0);
		}
		// try to grab data from file and populate variables
		try
		{
			// if (fileIn.hasNextLine())
			// {
			gameLevel = Integer.parseInt(fileIn.next());
			scalingDamage = Integer.parseInt(fileIn.next());
			playerStartItem.setName(fileIn.next());
			playerStartItem.setDamage(Integer.parseInt(fileIn.next()));
			int hp = Integer.parseInt(fileIn.next());
			int level = Integer.parseInt(fileIn.next());
			currentPlayer = new Player(hp, level, playerStartItem);
			fileIn.close();
			// }
		}
		// Catch error and handle
		catch (NumberFormatException e)
		{
			System.out.println("File Read Error. " + e);
			userInput.close();
			System.exit(0);
		}
		// close resources
	}

}
