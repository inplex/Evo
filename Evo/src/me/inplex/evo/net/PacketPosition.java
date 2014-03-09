package me.inplex.evo.net;

import me.inplex.evo.graphics.Direction;

public class PacketPosition implements Packet {
	
	private String data;
	
	private String ip;
	private int x;
	private int y;
	private Direction dir;
	
	public PacketPosition(String data) {
		this.data = data;
		unpackData();
	}
	
	public PacketPosition(String ip, int x, int y, Direction dir) {
		this.ip = ip;
		this.x = x;
		this.y = y;
		this.dir = dir;
		packData();
	}
	
	private void packData() {
		StringBuilder sb = new StringBuilder();
		sb.append(ip);
		sb.append("#");
		sb.append(x);
		sb.append("#");
		sb.append(y);
		sb.append("#");
		sb.append(Direction.getValue(dir));
		this.data = sb.toString();
	}
	
	private void unpackData() {
		String[] s = data.split("#");
		this.ip = s[0];
		this.x = Integer.valueOf(s[1]);
		this.y = Integer.valueOf(s[2]);
		this.dir = Direction.valueOf(Integer.valueOf(s[3]));
	}
	
	public String toString() {
		return data;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Direction getDirection() {
		return dir;
	}
	
}
