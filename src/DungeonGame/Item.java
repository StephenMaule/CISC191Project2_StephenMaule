package DungeonGame;

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
 *         Version/date: 5/1/2023
 * 
 *         Responsibilities of class:
 *         Stores item information
 */

// Item Is an Entity
public class Item extends Entity
{
	// Item Has a Name
	private String name; // Name of Item
	// Item Has a Damage value
	private int damage; // Item Damage Value

	/**
	 * Default Constructor for item
	 */
	Item()
	{
		super("Item");
		name = "New_Weapon";
		damage = 1;
	}

	/**
	 * Constructor for Difficulty scaling
	 */
	Item(int scalingDamage)
	{
		super("Item");
		name = "+" + scalingDamage + "_Weapon";
		damage = scalingDamage * 2; // Multiply by two to keep up with enemy
									// progression
	}

	/**
	 * Constructor for item, setting a new value for name and damage
	 */
	Item(String newName, int newDamage)
	{
		super("Item");
		name = newName;
		damage = newDamage * 2;
	}

	/**
	 * Returns the name of the item
	 * 
	 * @return Item Name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the Damage of the item
	 * 
	 * @return Item Damage
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * Sets the name of the item
	 * 
	 * @param Item Name
	 */
	public void setName(String newName)
	{
		name = newName;
	}

	/**
	 * Sets the Damage of the item
	 * 
	 * @param Item Damage
	 */
	public void setDamage(int newDamage)
	{
		damage = newDamage;
	}
}
