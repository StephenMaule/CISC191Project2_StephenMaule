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
 *         Decide to attach or defend against player increase damage depending
 *         on enemies fought
 */

// Enemy Is an Entity
// Enemy is combatable
public class Enemy extends Entity implements Combatable
{
	// Enemy has a item
	private Item heldItem = new Item("Enemy Weapon", 1);; // Held
															// Item
															// object
	// Enemy has a defence status
	private boolean defended; // Can Defend? boolean
	// Enemy has a health pool
	private int health = 0; // Enemy Health Points

	/**
	 * Default Constructor for an enemy
	 */
	Enemy()
	{
		// Sets Entity ID
		super("Enemy");
	}

	/**
	 * Constructor for setting increased damage
	 */
	Enemy(int newDamage)
	{
		// Sets Entity ID
		super("Enemy");
		// Sets held item to item of new Damage
		heldItem = new Item("Enemy Weapon", newDamage);
		health = newDamage * 3;
	}

	/**
	 * Returns the damage of the currently held item
	 * 
	 * @return Held Item object Damage int value
	 */
	public int attack()
	{
		return heldItem.getDamage();
	};

	/**
	 * returns a boolean for if the prior move was defence
	 */
	public void defend()
	{
		defended = !defended;
	};

	/**
	 * Updates health based on damage or healing
	 * 
	 * @param intended health change
	 * 
	 * @return dead or alive boolean
	 */
	public void updateHP(int healthChange)
	{
		health += healthChange; // health == the change in hp constrained to the
								// current lvl * hp gained per level AKA max HP
	}

	/**
	 * Returns held item
	 * 
	 * @return Held Item object
	 */
	public Item getWeapon()
	{
		return heldItem;
	}

	/**
	 * returns if the health value of the enemy
	 * 
	 * @return int health
	 */
	public int getHealth()
	{
		return health;
	}

	/**
	 * returns if the enemy is defended
	 * 
	 * @return bool defended
	 */
	public boolean defended()
	{
		return defended;
	}
}
