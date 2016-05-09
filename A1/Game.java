import java.util.*;

public class Game {
	
	private int framerate;
	private int speed;
	private int score = 0;
	final int maxWidth = 36; // max unit width
	final int maxHeight = 22; // max unit height (10 pixels per unit)
	private HashSet<Integer> used; // track any occupied units
	private HashSet<Integer> foods;
	private Python snake;
	private boolean gameOver = false;
	private int occurence = 0;
	
	Game(int fps, int s){
		framerate = fps;
		speed = s;
		snake = new Python(this, new Game.Pair(4, 4), new Game.Pair(3, 4));
		
		used = new HashSet<Integer>();
		foods = new HashSet<Integer>();
		newFood();
	}

	//add one new food to the food map
	public void newFood(){
		Pair newpair;
		
		// Create new food and make check if it's a occupied unit
		do {
			Random rand = new Random();
			int x = rand.nextInt(maxWidth);
			int y = rand.nextInt(maxHeight);

			newpair = new Pair(x,y);
		}while (used.contains(newpair)); // Find another two points

        used.add(newpair.keyGen());
		foods.add(newpair.keyGen());
		System.out.printf("New Food: %d, %d\n", newpair.left(), newpair.right());
	}
	
	// remove the food
	public void removeFood( Pair p ){
		if (foods.contains(p)){
			used.remove(p);
			foods.remove(p);
		}
	}
	
	// Return The list of foods as an ArrayList
	public ArrayList<Integer> getFoods(){
		return new ArrayList<Integer>(foods);
	}
	
	public ArrayList<Integer> getSnake(){
		return snake.getSnake();
	}
	
	public void snakeMove(char a){
		snake.newDirection(a);
	}
	
	//add s to score
	public void addScore(int s){
		score += s;
	}
	
	//return score
	public int getScore(){
		return score;
	}
	
	// a single cycle of game loop execution
	public void gameRun(){

        ArrayList<Integer> snakes = snake.getSnake();
        
        Integer next = new Integer(snake.head().keyGen());
        char dir = snake.head.direction;
        
        switch (dir){
        case 'l':
        	next = next - 1;
        	break;
        case 'r':
        	next = next + 1;
        	break;
        case 'u':
        	next = next - 100;
        	break;
        case 'd':
        	next = next + 100;
        	break;
        default:
        	break;
        }
        
        // react on correct frame
        if ((occurence % (framerate/1)) == 0) {
        	if (foods.contains(next)){
        		snake.eat(new Pair(decodeX(next), decodeY(next)));
        		foods.remove(next);
        	}else{
        		snake.updateSnake();
        	}
        }

        // update data strucutres
        used.clear();
        used.addAll(snake.getSnake());
        used.addAll(foods);

		occurence++;

        // detect if the snake hits the wall
        if (snake.hitBoundary(maxWidth, maxHeight)){
            gameOver();
        }
	}
	
	// Good Game, Well Played
	public void gameOver(){
		gameOver = true;
	}
	
	// Decode the Pair's key into its X/Y components
	public static int decodeX(int key){ return key%100; }		
	public static int decodeY(int key){ return key/100; }
	
	// Detect if the game has stopped
    public boolean gameStop() { return gameOver; }
	
	// Pair class 
	class Pair {
		private int left;
		private int right;

        Pair (Pair p) {
            this.left = p.left();
            this.right = p.right();
        }

		Pair(int l, int r){
			left = l;
			right = r;
		}
		
		// return left value
		public int left() { 
			return left; 
		}
		
		//return right value
		public int right() { 
			return right; 
		}
		
		public void change(int dx, int dy){
			left += dx;
			right += dy;
		}
		
		// Generates a unique integer key for the pair
		public int keyGen(){
			return left + 100*right;
		}
		
		@Override
		public boolean equals (Object obj) {
			Pair p = (Pair) obj;
			return (p.left() == left) && (p.right() == right);
		}
		
		@Override
		public int hashCode() {
			int hash = left + maxHeight*right;
			return hash;
		}

        @Override
        public String toString(){
            return String.format("(%d, %d)", left, right);
        }
	} // end of inner class Pair

}
