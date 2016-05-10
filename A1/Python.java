import java.util.*;

public class Python {
	
	ArrayList<Segment> snake;
	Segment head; // the current head of the python
	Segment tail; // useful!!
	Map<Integer, Character> turning; // track the movements and corresponding coordinates
	Game game;
	
	public Python(Game g, Game.Pair h, Game.Pair t){
		game = g;
		head = new Segment(h, 'r', false);
		
		// create tails if h != t
		if (h != t){
			tail = new Segment(t, 'r', true);
		}else{
			head.isTail = true;
			tail = head;
		}
		
		snake = new ArrayList<Segment>();
		turning = new HashMap<Integer, Character>();
		snake.add(head); // add a head
		snake.add(tail);
	}
	
	public void eat(Game.Pair p){
		char dir = head.getDirection();
		head = new Segment(p, dir, false);
		snake.add(head);
		game.addScore(10);
		
		//increase total food threshold after 50 scores
		if (game.getScore() % 50 == 0){
			game.newFood();
		}
		// back up a food
		game.newFood();
	}
	
	// return the coordinates of the snake
	public ArrayList<Integer> getSnake(){
		ArrayList<Integer> cord = new ArrayList<Integer>();
		
		for (Segment i : snake){
			cord.add(i.getPair().keyGen());
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
        if (!opposite(d, head.getDirection()) && !turning.containsKey(head.getPair().keyGen())) {
            turning.put(head.getPair().keyGen(), new Character(d));
            head.direction = d;
        }
	}

	// detect if the head is out of boundary
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
