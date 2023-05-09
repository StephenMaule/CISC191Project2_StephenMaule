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
 *         define abstract entity object that all entities share
 */

public abstract class Entity
{
	// Entity has a ID
	public String ID;

	/**
	 * Constructor for the Entity ID
	 * 
	 * @param Type ID
	 */
	Entity(String objectID)
	{
		ID = objectID;
	}
}
