package me.inplex.evo.entity;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;

public class Tree implements RenderObject {

	private int x;
	private int y;
	private BufferedImage image;

	public Tree(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		double rnd = Math.random();
		if (rnd > 0.5)
			this.image = ImageIO.read(new File(Game.getRes("tree1.png")));
		else
			this.image = ImageIO.read(new File(Game.getRes("tree2.png")));
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	public void update() {
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
