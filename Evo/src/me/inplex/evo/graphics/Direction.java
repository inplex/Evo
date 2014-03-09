package me.inplex.evo.graphics;

public enum Direction {
	LEFT,
	RIGHT,
	UP,
	DOWN;
	
	public static Direction valueOf(int i) {
		return Direction.values()[i];
	}
	
	public static int getValue(Direction d) {
		return d.ordinal();
	}
}