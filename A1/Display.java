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

	// used to calculate real FPS
    private long timeStart;
    private double realfps;
    
    static Color Basic = new Color(210, 210, 210);
    static Color Deep = new Color(35, 35, 35);
	
	// constructor
	public Display(int fps, int speed){
		super();
		this.fps = fps;
		this.speed = speed;
		
		realfps = (double) fps;
		
		game = new Game(fps, speed);
		foods = new ArrayList<Integer>();
		snake = new ArrayList<Integer>();
		
		gameThread = new Timer( 1000/fps , this);
		addKeyListener(this); // add Event Handler to this Panel.
		setFocusable(false);
	};
	
	// Draw method paints the current JPanel
	public void paintComponent(Graphics g){
		super.paintComponent(g); // removes outed graphics
		
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Deep);
        g2.fillRect(0, 0, 800, 580);
		
	    String fps = String.format("FPS: %d", (int)realfps);
	    String speed= String.format("Speed: %d", this.speed);
	    String score = String.format("Score: %d", this.score);

	    // printing texts
	    g2.setColor(Color.white);
	    g2.setFont(new Font("Arial", Font.PLAIN, 18));
	    g2.drawString(fps, 650, 550);
	    g2.drawString(speed, 50, 550);
	    g2.drawString(score, 350, 550);
		
		//paint snake
		for (Integer i : snake){
			int x = Game.decodeX(i.intValue());
			int y = Game.decodeY(i.intValue());
					
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.white);
			g2.fillRect(40+x*20, 40+y*20, 20, 20);
			g2.setColor(Deep);
			g2.drawRect(40+x*20, 40+y*20, 20, 20);
		}
		
		//Trim head
		if (game.started()){
			int x = Game.decodeX(game.getHead());
			int y = Game.decodeY(game.getHead());
			g2.setColor(Basic);
			g2.fillRect(40+x*20, 40+y*20, 20, 20);
			g2.setColor(Deep);
			g2.drawRect(40+x*20, 40+y*20, 20, 20);
		}
		
		//paint food
		for (Integer i : foods){
			int x = Game.decodeX(i.intValue());
			int y = Game.decodeY(i.intValue());
					
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(40+x*20, 40+y*20, 20, 20);
			// trims
			g2.setColor(Color.RED);
			g2.drawLine(40+x*20+10, 43+y*20, 40+x*20+10, 37+y*20+20);
			g2.drawLine(40+x*20+3 , 40+y*20+10, 40+x*20+17, 40+y*20+10);
		}
	
		// Draw the borderline
		g2.setStroke(new BasicStroke(1));
	    g2.setColor(Color.white);
	    g2.drawRect(20, 20, 760, 480);
	    g2.setColor(Color.white);
	    g2.drawRect(40, 40, 720, 440);
	}
	
	public void update(){
		//Update game status
		foods = game.getFoods();
		snake = game.getSnake();
		score = game.getScore();
		repaint();

		if (game.gameStop()){
			gameThread.stop();
		}
	}
	
	// Main Game loop
	public void actionPerformed(ActionEvent e){
		timeStart = System.currentTimeMillis();
		game.gameRun();
		update();
		
		realfps = 1000.0/(System.currentTimeMillis() - timeStart + 1000.0/fps); // realFPS calculation
	}
	
	// Keyboard Event
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
	
		if (key == KeyEvent.VK_S){
			gameThread.start();
		}else if (key == KeyEvent.VK_P){
			game.togglePause();
		}else if (key == KeyEvent.VK_E) {
            gameThread.stop(); // exist current game thread
            game.clean(); // clean the board
            update(); // clear the screen
            game.start();
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
