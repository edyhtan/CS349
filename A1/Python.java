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
        if (!opposite(d, head.getDirection())) {
            turning.put(head.getPair(), new Character(d));
            head.direction = d;
        }
	}

	// detect if the head is out of boundry
	public boolean hitBoundary(int x, int y){
		return (head.getX() >= x) || (head.getX() < 0) || (head.getY() >= y)  || (head.getY() < 0);
	}

    public char headDirection(){
        return head.getDirection();
    }

    public Game.Pair head(){
        return head.cord;
    }

    // determine two character representation of direction is opposite
    public boolean opposite(char i, char j){
        return (i == 'l' && j == 'r') || (i == 'r' && j == 'l') ||
                (i == 'u' && j == 'd') ||  (i == 'd' && j == 'u');
    }

}
