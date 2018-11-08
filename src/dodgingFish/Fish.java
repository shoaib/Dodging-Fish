package dodgingFish;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Stores all the information on sea creatures
 */
public class Fish{
	
	//constants
	private final int SINK_DOWN = 10;
	private final int TOP_SPACE = 100;
	private final int JUMP_HEIGHT = 50;//moving distance along y-axis
	
	//instance variables
	private int width, height;//size of the panel
	private int x;//x-coordinate
	private int y;//y-coordinate
	private int dx;//moving distance along x-axis
	private String name;//name of this creature
	private int size;//height of this creature
	private int length;//length of this creature
	private int speed;//swimming speed
	private Image img;
	
	/**
	 * constructor
	 * @param w panel width
	 * @param h panel height
	 * @param id name of the creature
	 * @param c the number of pixel changes
	 */
	public Fish(int w, int h, String id, int c, int l, int s)
	{
		width = w;
		height = h;
		name = id;
		length = l;
		size = s;
		speed = c;

		//If the current creature is a player fish
		if(!isEnemy())
		{
			x = 200;//place it at the left end	
			dx = 0;//no forward movement
			y = height/2;
		}
		else //If it's an enemy, place it at right hand side
		{
			x = width - length;
			dx = -speed;//move to left speed pixels.
			//set the fish at random height
			y = (int)(Math.random() * (height-TOP_SPACE))+ TOP_SPACE;
		}

	}

	/**
	 * Draws the current creature
	 * @param g
	 */
	public void draw(Graphics g)
	{	
		try {
			g.drawImage(ImageIO.read(new File(name + ".png")), x, y, length, size, null);
		} catch (IOException e) {
			System.err.println(name +".png not found");
		}
	}

	/**
	 * Allows the fish to jump up
	 */
	public void jump()
	{
		//if(y <= JUMP_HEIGHT || y >= height - JUMP_HEIGHT) return;
		y -= JUMP_HEIGHT;
	}
	
	public boolean isOutOfBoundary() {
		if(y<=0 || y >=height)
			return true;
		
		return false;
	}

	/**
	 * Makes the fish swim
	 */
	public void swim()
	{
		x += dx;

		if(!isEnemy()){
			y += SINK_DOWN;
		}
		
		if (x <= -length || x >= width)
		{
			if(isEnemy())
				y = (int)(Math.random() * (height-size));
			else y = (int)(Math.random() * (height-2*size));
		}

		if (!isEnemy() && x >= width + speed)
		{
			x = 0;
		}
		else if (isEnemy() && x <= -length)
		{
			x = width;
		}			

	}

	/**
	 * If the current creature is an enemy
	 * @return true if not a clown fish
	 */
	private boolean isEnemy() {
		return !(name.equals("Clownfish")
				/*|| name.equalsIgnoreCase("yellowtang")*/);
	}

	/**
	 * Gets current x-coordinate
	 * @return x-coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets current swimming speed
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * is moving out the window
	 * @param x current x-coordinate
	 * @return true if moving out the window
	 */
	public boolean moveOut(int x) {
		return x>= width;
	}

	/**
	 * sets x-coordinate
	 * @param i new x
	 */
	public void setX(int i) {
		x = i;		
	}

	/**
	 * two fishes are equal if they have the same name
	 * @param other another fish
	 * @return true if they are the same fish.
	 */
	public boolean equals(Object other)
	{
		return name.equals(((Fish)other).toString());
	}

	/**
	 * returns the name of the fish
	 */
	public String toString()
	{
		return name;
	}

	/**
	 * Does the current fish overlap with the other fish
	 * @param other another fish
	 * @return true if overlaps.
	 */
	public boolean collides(Fish other) {
		int x1 = x + length;
		int y1 = y + size;
		return (x >= other.x && x <= other.x + other.length
				&& y >= other.y && y <= other.y + other.size)
				||(x1 >= other.x && x1 <= other.x + other.length
						&& y1 >= other.y && y1 <= other.y + other.size);
	}
}
