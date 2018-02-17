package model;

public enum Direction {
	NORTH,
	NORTHEAST,
	EAST,
	SOUTHEAST,
	SOUTH,
	SOUTHWEST,
	WEST,
	NORTHWEST;
	
	public Direction rotateLeft() {
		return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
	}
	public Direction rotateRight() {
		return Direction.values()[(this.ordinal() + Direction.values().length - 1) % Direction.values().length];
	}
}
