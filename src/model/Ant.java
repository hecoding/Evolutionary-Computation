package model;

public class Ant {
	public Direction direction;
	public Position pos;
	public int steps;
	public int bitsEaten;
	
	public Ant() {
		this.direction = Direction.right;
		this.pos = new Position(0,0);
		this.steps = 0;
		this.bitsEaten = 0;
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
		
		this.steps++;
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
		
		this.steps++;
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
		
		this.steps++;
	}
	
	public void eat() {
		this.bitsEaten++;
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public void setPosition(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public Position getForwardPosition() {
		Position ret = new Position(this.pos.x, this.pos.y);
		
		switch(this.direction) {
		case down:
			ret.y++;
			break;
		case left:
			ret.x--;
			break;
		case right:
			ret.x++;
			break;
		case up:
			ret.y--;
			break;
		}
		
		return ret;
	}
	
	public int getNumberOfSteps() {
		return this.steps;
	}
	
	public int getNumberOfBitsEaten() {
		return this.bitsEaten;
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
