import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Display extends JPanel implements KeyListener, ActionListener {
	
	//variables and objects
	private int fps;
	private int speed;
	private int score = 0;
	private Game game;
	
	private ArrayList<Integer> foods;
	private ArrayList<Integer> snake;
	private Timer gameThread;
	
	// constructor
	public Display(int fps, int speed){
		super();
		this.fps = fps;
		this.speed = speed;
		
		game = new Game(fps, speed);
		foods = new ArrayList<Integer>();
		snake = new ArrayList<Integer>();
		
		gameThread = new Timer(1000/fps, this);
		addKeyListener(this); // add Event Handler to this Panel.
		setFocusable(false);
	};
	
	// Draw method paints the current JPanel
	public void paintComponent(Graphics g){
		super.paintComponent(g); // removes outed graphics
		
		Graphics2D g2 = (Graphics2D) g;

		//Paint Game Border and Game Informations
	    String fps = String.format("FPS: %d", this.fps);
	    String speed= String.format("Speed: %d", this.speed);
	    String score = String.format("Score: %d", this.score);

	    // printing texts
	    g2.setFont(new Font("Arial", Font.PLAIN, 18));
	    g2.drawString(fps, 650, 550);
	    g2.drawString(speed, 50, 550);
	    g2.drawString(score, 350, 550);
	    
	    // Draw the borderline
	    g2.setColor(Color.gray);
	    g2.drawRect(20, 20, 760, 480);
	    g2.setColor(Color.gray);
	    g2.drawRect(40, 40, 720, 440);
	    
		//paint food
		for (Integer i : foods){
			int x = Game.decodeX(i.intValue());
			int y = Game.decodeY(i.intValue());
			
			g2.setColor(Color.black);
			g2.fillRect(40+x*20, 40+y*20, 20, 20);
			g2.setColor(Color.lightGray);
			g2.drawRect(40+x*20, 40+y*20, 20, 20);
		}
		
		//paint snake
		for (Integer i : snake){
			int x = Game.decodeX(i.intValue());
			int y = Game.decodeY(i.intValue());
					
			g2.setColor(Color.green);
			g2.fillRect(40+x*20, 40+y*20, 20, 20);
			g2.setColor(Color.lightGray);
			g2.drawRect(40+x*20, 40+y*20, 20, 20);
		}
	}
	
	public void update(){
		//Update game status
		foods = game.getFoods();
		snake = game.getSnake();
		repaint();

		if (game.gameStop()){
			gameThread.stop();
		}
	}
	
	// Main Game loop
	public void actionPerformed(ActionEvent e){
		game.gameRun();
		update();
	}
	
	// Keyboard Event
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
	
		if (key == KeyEvent.VK_S){
			gameThread.start();
		}else if (key == KeyEvent.VK_E){
			game.gameOver();
		}else if (key == KeyEvent.VK_UP){
			game.snakeMove('u');
		}else if (key == KeyEvent.VK_DOWN){
			game.snakeMove('d');
		}else if (key == KeyEvent.VK_LEFT){
			game.snakeMove('l');
		}else if (key == KeyEvent.VK_RIGHT){
			game.snakeMove('r');
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
