package me.inplex.evo.entity;


import java.awt.Graphics;

public interface RenderObject {
	
	int x = 0;
	int y = 0;
	
	public void render(Graphics g);
	public void update();
	
	public void setX(int x);
	public void setY(int y);
	public int getX();
	public int getY();
	
}
