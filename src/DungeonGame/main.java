package DungeonGame;

import java.io.IOException;
import java.util.Scanner;

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
 *         <<add more references here>>
 *         https://www.w3resource.com/java-tutorial/file-input-and-output.php
 * 
 *         Version/date:5/15/2023
 *         yes
 *         Responsibilities of class:
 *         Main class and method: Displays current room, saves game to file,
 *         reads input for game based info like saving or quitting
 */

public class main
{
	// AKA Game object
	public static void main(String[] args)
	{
		// initialize map object and input
		Map map = new Map();
		map.display();
		Scanner SystemIn = new Scanner(System.in);
		// Promt for past save loading
		System.out.println("Would you like to load a save? yes/no");
		String input = SystemIn.nextLine();
		switch (input.toLowerCase())
		{
			case "yes":
				map.loadGame();
				break;
			default:
				break;
		}
		// Play while Player is alive and not exiting
		while (map.roundPlayerInput())
		{
		}
		// promt for save
		if (map.doSave())
		{
			// try to save
			try
			{
				map.saveGame();
				System.out.println("Saved!");
			}
			// catch and handle error
			catch (IOException e)
			{
				System.out.println("Unable to save game.");
			}
			
		}

		// Finish the Game
		System.out.println("Game Over!");
	}

}
