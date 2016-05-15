package model;

public class Ant {
	public Direction direction;
	public Position pos;
	
	public Ant() {
		this.direction = Direction.right;
		this.pos = new Position(0,0);
	}
	
	public enum Direction {
		up, down, right, left
	}
	
	public class Position {
		public int x;
		public int y;
		
		public Position (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
