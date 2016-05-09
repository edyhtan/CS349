import java.util.*;


public class Game {
	
	private int framerate;
	private int speed;
	private int score = 0;
	final int maxWidth = 36; // max unit width
	final int maxHeight = 22; // max unit height (10 pixels per unit)
	private HashSet<Pair> used; // track any occupied units
	private HashSet<Pair> foods;
	private Python snake;
	private boolean gameOver = false;
	private int occurence = 0;
	
	Game(int fps, int s){
		framerate = fps;
		speed = s;
		snake = new Python(this, new Game.Pair(4, 4));
		
		used = new HashSet<Pair>();
		foods = new HashSet<Pair>();
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
		
		used.add(newpair);
		foods.add(newpair);
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
	public ArrayList<Pair> getFoods(){
		return new ArrayList<Pair>(foods);
	}
	
	public ArrayList<Pair> getSnake(){
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
		if ((occurence % (framerate/5)) == 0){
			snake.updateSnake();
		}
		occurence++;
	}
	
	// Good Game, Well Played
	public void gameOver(){
		gameOver = true;
	}
	
	// Pair class 
	class Pair {
		private int left;
		private int right;
		private int hash = 0;
		
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

		// Functions Override for Hash methods
		@Override
		public int hashCode(){
			hash++;
			return hash;
		}
		
		@Override
		public boolean equals (Object obj) {
			Pair p = (Pair) obj;
			return p.left() == left && p.right() == right;
		}
	} // end of inner class Pair

}
