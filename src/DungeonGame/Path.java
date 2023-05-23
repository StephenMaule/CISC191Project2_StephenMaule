package DungeonGame;

// Used for randomizing loot and enemies
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
 *         <<add more references here>>
 * 
 *         Version/date: 5/22/2023
 * 
 *         Responsibilities of class:
 *         Generate new rooms, Fill paths with loot enemy or empty, create and
 *         randomize improved stat weapon
 */

// Paths have entitys
public class Path
{
	// Path has path choices
	private Entity[] pathChoices = new Entity[3]; // Array of path options

	/**
	 * Constructor for the level path
	 * 
	 * @param increasing level difficulty
	 */
	Path(int difficulty)
	{
		Random random = new Random(); // Randomizor object
		// Creates 3 paths
		for (int i = 0; i < 3; i++)
		{
			// Generates random value of true or false
			boolean randOption = random.nextBoolean();
			if (randOption == true)
			{
				// if random is true set path direction to enemy
				pathChoices[i] = new Enemy(difficulty);
			}
			else
			{
				// if random is false set path direction to item
				pathChoices[i] = new Item(difficulty);
			}
		}
	};

	/**
	 * returns array of Entities that is either item or enemy, for all 3 paths
	 * 
	 * @return Array of Entities
	 */
	public Entity[] getPaths()
	{
		return pathChoices;
	}

	/**
	 * Prints dynamic map based on spawned entities using a 2 dimentional array
	 */
	public String printMap()
	{
		// Constants for different map resources
		String enemy = "        #       ";
		String loot = "        *       ";
		String player = "        ^       ";
		String wall = "0000000";
		String air = "                 ";
		String map = "";
		// Empty map structure
		String[][] mapArt = { { wall, air, wall }, { wall, air, wall },
				{ wall, air, wall }, { wall, air, wall }, { wall, air, wall },
				{ wall, air, wall }, { wall, air, wall }, { air, air, air },
				{ air, air, air }, { air, air, air }, { air, air, air },
				{ air, air, air }, { air, air, air }, { air, air, air },
				{ wall, air, wall }, { wall, air, wall }, { wall, air, wall },
				{ wall, player, wall }, { wall, air, wall },
				{ wall, air, wall }, { wall, air, wall } };
		// Itterate through map and update key positions for entities
		for (int row = 0; row < 21; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				if (row == 3 && column == 1)
				{
					if (pathChoices[1].ID.contains("Enemy"))
					{
						// System.out.print(enemy);
						map += enemy;
					}
					else
					{
						// System.out.print(loot);
						map += loot;
					}
				}
				else if (row == 10 && column == 0)
				{
					if (pathChoices[0].ID.contains("Enemy"))
					{
						// System.out.print(enemy);
						map += enemy;
					}
					else
					{
						// System.out.print(loot);
						map += loot;
					}
				}
				else if (row == 10 && column == 2)
				{
					if (pathChoices[2].ID.contains("Enemy"))
					{
						// System.out.print(enemy);
						map += enemy;
					}
					else
					{
						// System.out.print(loot);
						map += loot;
					}
				}
				else
				{
					// System.out.print(mapArt[row][column]);
					map += mapArt[row][column];
				}
			}
			// System.out.println();
			map += "\n";
		}
		return map;
	}
}
