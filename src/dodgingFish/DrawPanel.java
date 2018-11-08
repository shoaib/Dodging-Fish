package dodgingFish;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class draws the panel and adds everything to the panel
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel{
	
	private boolean gameOver; //status of game
	private int width; //width of the panel
	private int height; //height of the panel
	private Fish[] enemy; //an array to store enemy fishes
	private Fish fish; //player fish	
	private Fish current; //current fish you are playing
	private int currentScore = 0; // stores current score
	
	/**
	 * Constructor: Initialized instance variables and objects
	 */
	public DrawPanel(int w, int h)
	{
		gameOver = false;
		width = w;
		height = h;
		enemy = new Fish[6];
		initFish();
		current = fish;//make the first fish playable.
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Sets initial properties for all the fishes
	 */
	private void initFish() {
		fish = new Fish(width, height, "Clownfish", 10, 50, 20);
		enemy[0] = new Fish(width, height, "yellowtang", 25, 50, 20);
		enemy[1] = new Fish(width, height, "Shark", 25, 80, 20);
		enemy[2] = new Fish(width, height, "GreatWhite", 20, 80, 20);
		enemy[3] = new Fish(width, height, "Octopus", 15, 40, 20);
		enemy[4] = new Fish(width, height, "Orca", 10, 80, 20);
		enemy[5] = new Fish(width, height, "SeaTurtle", 5, 50, 20);	
	}

	/**
	 * This method paints all the components
	 */
	public void paintComponent(Graphics g)
	{
		//draws background image
		try {
			g.drawImage(ImageIO.read(new File("OceanFloor.jpg")), 0, 0, width, height,null);
		} catch (IOException e) {
			System.err.println("Cound not find background image file.");
		}
		
		//if the game is over, draw ending
		if(gameOver)
		{
			drawEnding(g);
			return; //skip rest of the code
		}
		
		//otherwise, draw fish
		current.draw(g);
		for (int i = 0; i < enemy.length-1; i++)
			enemy[i].draw(g); //draw all enemy
		
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + currentScore, 50, 50);
        g.drawString("High Score: " + Scores.getHighScore(), 620, 50);

	}

	/**
	 * Allows user to make the fish jump
	 */
	public void fishJump() {
		current.jump();
		repaint();
	}

	/**
	 * This method animates all the creatures
	 */
	public void animate() {
		currentScore++;
		//make enemies swim 
		for (int i = 0; i < enemy.length; i++)
		{
			enemy[i].swim();
		}
		
		//if current fish swims out, let next fish in

		current.swim();
		
		//if there is a collision, game over
		if(checkCollision() || current.isOutOfBoundary()) 
			gameOver = true;
	}

	/**
	 * This method checks for collision, and returns true if any object has collided with the player fish
	 */
	private boolean checkCollision() {
		
		//check current fish with every enemy
		//if current fish is overlapped with other fish, collide
		for (int i = 0; i < enemy.length; i++)
		{
			if(current.collides(enemy[i]))
				return true;
		}
		//otherwise no collision.
		return false;
	}


	/**
	 * Checks whether the game is over or not
	 * @return true if game is over
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Draws game over frame
	 */
	public void drawEnding(Graphics g) {
		if(currentScore > Scores.getHighScore())
			Scores.setHighScore(currentScore);
		g.setFont(new Font("Serif", Font.BOLD, 48));
		g.setColor(Color.WHITE);
		g.drawString("Game Over. Your Score: " + currentScore, width/5+33, height/2);
		g.drawString("Press 'Space' to play again or 'ESC' to exit.", width/16+3, 300);

	}

	/**
	 * sets game over to new value
	 * @param b new boolean value
	 */
	public void setGameOver(boolean b) {
		gameOver = b;
		
	}
}
