package dodgingFish;

/**
 * This class stores the high score
 */
public class Scores {
	private static int highScore = 0; // Stores user's high score
	
	/**
	 * The method to set user's high score
	 */
	public static void setHighScore(int h) {
		highScore = h;
	}
	
	
	/**
	 * The method to get user's high score
	 * @return high score
	 */
	public static int getHighScore() {
		return highScore;
	}
}
