package DungeonGame;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

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
 *         Version/date: 5/27/2023
 * 
 *         Responsibilities of class:
 *         Main class and method: tracks and runs repeating rounds until game over
 */

public class main
{
	// AKA Game object
	public static void main(String[] args)
	{
		// initialize map object and input
		Map map = new Map();
		// Play while Player is alive and not exiting
		while (map.roundPlayerInput())
		{
		}
		// Finish the Game
		System.out.println("Game Over!");
	}

}
