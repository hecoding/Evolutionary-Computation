package model;

public class Ant {
	public Direction direction;
	public Position pos;
	
	public Ant() {
		this.direction = Direction.right;
		this.pos = new Position(0,0);
	}
	
	public void turnLeft() {
		switch(this.direction) {
		case down:
			this.direction = Direction.right;
			break;
		case left:
			this.direction = Direction.down;
			break;
		case right:
			this.direction = Direction.up;
			break;
		case up:
			this.direction = Direction.left;
			break;
		}
	}
	
	public void turnRight() {
		switch(this.direction) {
		case down:
			this.direction = Direction.left;
			break;
		case left:
			this.direction = Direction.up;
			break;
		case right:
			this.direction = Direction.down;
			break;
		case up:
			this.direction = Direction.right;
			break;
		}
	}
	
	public void moveForward() {
		switch(this.direction) {
		case down:
			this.pos.y++;
			break;
		case left:
			this.pos.x--;
			break;
		case right:
			this.pos.x++;
			break;
		case up:
			this.pos.y--;
			break;
		}
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public void setPosition(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
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
