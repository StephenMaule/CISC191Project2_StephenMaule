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
 *         Version/date: 5/27/2023
 * 
 *         Responsibilities of class:
 *         store held item, health information level information. Promt
 *         attacking or defending, interact with lootable objects
 */

// Player Is an Entity
// Player is combatable
public class Player extends Entity implements Combatable
{
	// Player has a Weapon
	private Item heldWeapon = new Item("Basic_Sword", 2); // Stores held item
	// Player has a Health pool and Level
	private int health = 5, level = 1; // Health and Level Values
	// Player has a Defence Status
	private boolean defended; // Can Defend? boolean

	/**
	 * Constructor for player
	 */
	Player()
	{
		// SetEntity ID
		super("Player");
	};

	Player(int startingLevel, int startingHealth, Item startingItem)
	{
		// SetEntity ID
		super("Player");
		level = startingLevel;
		health = startingHealth;
		heldWeapon = startingItem;
	}

	/**
	 * Updates health based on damage or healing
	 * 
	 * @param intended health change
	 * 
	 * @return dead or alive boolean
	 */
	public boolean updateHP(int healthChange)
	{
		health = (health + healthChange) % (level * 5); // health == the change
														// in hp constrained to
														// the current lvl * hp
														// gained per level AKA
														// max HP
		return true;
	}

	/**
	 * Updates health based on level
	 */
	public void levelUp()
	{
		health = (level * 5); // raise max hp
		level++;
	}

	/**
	 * Changes held weapon item
	 * 
	 * @param new Item
	 */
	public void swapWeapon(Item newWeapon)
	{
		// Set new damage
		heldWeapon.setDamage(newWeapon.getDamage());
		// Set new Name
		heldWeapon.setName(newWeapon.getName());
	}

	/**
	 * returns the weapon damage for an attack
	 * 
	 * @return int value weapon damage for an attack
	 */
	public int attack()
	{
		return heldWeapon.getDamage();
	};

	/**
	 * returns a boolean for if the prior move was defence
	 * 
	 * 
	 */
	public void defend()
	{
		defended = !defended;
	};

	/**
	 * returns the weapon currently held
	 * 
	 * @return the Item object currently held
	 */
	public Item getHeldWeapon()
	{
		return heldWeapon;
	}

	/**
	 * returns the player HP
	 * 
	 * @return int HP value
	 */
	public int getHP()
	{
		return health;
	}

	/**
	 * returns the player level
	 * 
	 * @return int level value
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * returns if the player is defended
	 * 
	 * @return bool defended
	 */
	public boolean defended()
	{
		return defended;
	}
}
