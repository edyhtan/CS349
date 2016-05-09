import java.util.*;

public class Python {
	
	ArrayList<Segment> snake;
	Segment head; // the current head of the python
	Segment tail; // useful!!
	Map<Game.Pair, Character> turning; // track the movements and corresponding coordinates
	Game game;
	
	public Python(Game g, Game.Pair p){
		game = g;
		head = new Segment(p, 'r', true);
		snake = new ArrayList<Segment>();
		turning = new HashMap<Game.Pair, Character>();
		snake.add(head);
	}
	
	public void eat(Game.Pair p){
		char dir = head.getDirection();
		head = new Segment(p, dir, false);
		tail = head;
		snake.add(head);
	}
	
	// return the coordinates of the snake
	public ArrayList<Game.Pair> getSnake(){
		ArrayList<Game.Pair> cord = new ArrayList<Game.Pair>();
		
		for (Segment i : snake){
			cord.add(i.getPair());
		}
		return cord;
	}
	
	// The snake will make one movement based on its current body structure by 1 unit
	public void updateSnake(){
		for (Segment i : snake){
			i.move(turning); // move by itself!
		}
	}
	
	// Create new directions for the head
	public void newDirection(char d){
		turning.put(head.getPair(), new Character(d));
		head.direction = d;
	}
}
