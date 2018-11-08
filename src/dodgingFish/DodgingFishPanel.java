package dodgingFish;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class initiates the graphics, creates components, and contains listener method
 */
public class DodgingFishPanel {
	
	//constants
	private final int WIDTH = 1000;//panel width 
	private final int HEIGHT = 500;//panel height
	private final int DELAY = 1;
	
	//instance variables
	private JFrame frame;
	private JLabel label;
	private DrawPanel panel;//a JPanel for drawing scenes
	private Timer t;
	
	/**
	 * Constructor: Calls a helper method
	 */
	public DodgingFishPanel() {
		restart();
	}
	
	/**
	 * Creates GUI components
	 */
	private void createComponents() {
		label = new JLabel("");
		panel = new DrawPanel(WIDTH, HEIGHT);

		/*
		 * Creates key listener
		 */
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
		        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
		        	panel.fishJump();
		        }
		        
		        if(panel.isGameOver() && (e.getKeyCode()==KeyEvent.VK_SPACE)) {
		        	restart();
		        }
		        
		        if(panel.isGameOver() && (e.getKeyCode()==KeyEvent.VK_ESCAPE)) {
		        	System.exit(1);
		        }
			}


			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

		});

			
		panel.add(label);
		frame.add(panel);
	}
	
	/*
	 * Method that starts/restarts the game
	 */
	public void restart() {
		frame = new JFrame();
		frame.setTitle("Dodging Fish");
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createComponents();
		frame.pack();
		frame.setVisible(true);
		t = new Timer(DELAY, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.animate();//make all fish swim
				panel.repaint();//repaint the scene
				
				if(panel.isGameOver()) {
					t.stop();
				}
				
			}});
		t.start();
		
	}
}
