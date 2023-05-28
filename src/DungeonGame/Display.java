package DungeonGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

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
 *         Display and manage GUI object / Components
 */

public class Display extends JFrame implements ActionListener
{
	// Display JPanel Sections
	private JPanel actions = new JPanel();
	private JPanel displays = new JPanel();
	private JPanel systemInteractions = new JPanel();
	private JPanel itemInteractions = new JPanel();
	private JPanel combatInteractions = new JPanel();
	private JPanel pathInteractions = new JPanel();
	// Display Has Text areas
	private JTextPane mapDisplay = new JTextPane();
	private JTextArea console = new JTextArea();
	private JTextArea playerStats = new JTextArea(3, 3);
	private JTextArea enemyStats = new JTextArea(3, 3);
	// Display Has Text Buttons
	private JButton save = new JButton("Save");
	private JButton load = new JButton("Load");
	private JButton left = new JButton("Left");
	private JButton right = new JButton("Right");
	private JButton straight = new JButton("Straight");
	private JButton attack = new JButton("Attack");
	private JButton defend = new JButton("Defend");
	private JButton pickUp = new JButton("Pick Up");
	private JButton discard = new JButton("Discard");
	// Display has mapAccess
	private Map mapAccess;

	Display(Map map)
	{
		mapAccess = map;
		// Set main layouts
		getContentPane().setLayout(new BorderLayout());
		actions.setLayout(new GridLayout());
		displays.setLayout(new FlowLayout());
		systemInteractions.setLayout(new GridLayout(2, 1, 0, 0));
		itemInteractions.setLayout(new GridLayout(2, 1, 0, 0));
		combatInteractions.setLayout(new GridLayout(2, 1, 0, 0));
		pathInteractions.setLayout(new GridLayout(3, 1, 0, 0));
		// Init Text Area values
		mapDisplay.setEditable(false);
		mapDisplay.setSize(300, 300);
		playerStats.setSize(75, 300);
		enemyStats.setSize(75, 300);
		console.setSize(450, 15);
		console.setLineWrap(true);
		console.setEditable(false);
		console.setAlignmentX(CENTER_ALIGNMENT);
		mapDisplay.setAlignmentX(CENTER_ALIGNMENT);
		playerStats.setAlignmentX(CENTER_ALIGNMENT);
		enemyStats.setAlignmentX(CENTER_ALIGNMENT);
		playerStats.setEditable(false);
		playerStats.setLineWrap(true);
		enemyStats.setEditable(false);
		enemyStats.setLineWrap(true);
		playerStats.append("PLAYER");
		enemyStats.append("ENEMY");
		// Add relevant components to panels
		displays.add(playerStats);
		displays.add(mapDisplay);
		displays.add(enemyStats);
		displays.add(console);
		systemInteractions.add(save);
		systemInteractions.add(load);
		itemInteractions.add(pickUp);
		itemInteractions.add(discard);
		combatInteractions.add(attack);
		combatInteractions.add(defend);
		pathInteractions.add(left);
		pathInteractions.add(straight);
		pathInteractions.add(right);
		actions.add(systemInteractions);
		actions.add(combatInteractions);
		actions.add(itemInteractions);
		actions.add(pathInteractions);
		// Add panels to main screen
		getContentPane().add(actions, BorderLayout.SOUTH);
		getContentPane().add(displays, BorderLayout.CENTER);
		// Tie action listener to buttons
		save.addActionListener(this);
		load.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		straight.addActionListener(this);
		attack.addActionListener(this);
		defend.addActionListener(this);
		pickUp.addActionListener(this);
		discard.addActionListener(this);
	}

	@Override
	/**
	 * Logic control function for the buttons on the GUI
	 * 
	 * @param Action event in question
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == save)
		{
			try
			{
				mapAccess.saveGame();
				console.append("Saved!");
			}
			catch (IOException e1)
			{
				console.append("Save Failed...");
			}
		}
		else if (e.getSource() == load)
		{
			mapAccess.loadGame();
		}
		else if (e.getSource() == left)
		{
			mapAccess.moveLeft();

		}
		else if (e.getSource() == right)
		{
			mapAccess.moveRight();
		}
		else if (e.getSource() == straight)
		{
			mapAccess.moveStraight();
		}
		else if (e.getSource() == attack)
		{
			mapAccess.attack();
		}
		else if (e.getSource() == defend)
		{
			mapAccess.defend();
		}
		else if (e.getSource() == pickUp)
		{
			mapAccess.pickUp();
		}
		else if (e.getSource() == discard)
		{
			mapAccess.discard();
		}
	}

	/**
	 * Function for setting text to the map TextArea
	 * 
	 * @param newText
	 */
	public void updateMap(String newText)
	{
		mapDisplay.setText(newText);
	}

	/**
	 * Function for setting text to the Console Area
	 * 
	 * @param newText
	 */
	public void updateConsole(String newText)
	{
		console.setText(newText);
	}

	/**
	 * Function for setting text to the Player Stat Area
	 * 
	 * @param newHealth
	 */
	public void updatePlayer(int newHealth, boolean defended)
	{
		if (defended)
		{
			playerStats.setText("Player" + "\n" + "Health:   " + "\n"
					+ newHealth + "\n" + "Defended!");
		}
		else
		{
			playerStats
					.setText("Player" + "\n" + "Health:   " + "\n" + newHealth);
		}
	}

	/**
	 * Function for setting text to the Enemy Stat Area
	 * 
	 * @param newHealth
	 */
	public void updateEnemy(int newHealth, boolean defended)
	{
		if (defended)
		{
			enemyStats.setText("Enemy" + "\n" + "Health:   " + "\n" + newHealth
					+ "\n" + "Defended!");
		}
		else
		{
			enemyStats
					.setText("Enemy" + "\n" + "Health:   " + "\n" + newHealth);
		}
	}
}