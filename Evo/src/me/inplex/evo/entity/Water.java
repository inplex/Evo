package me.inplex.evo.entity;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;

public class Water implements RenderObject {

	private int x;
	private int y;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image;

	public Water(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		this.image1 = ImageIO.read(new File(Game.getRes("water1.png")));
		this.image2 = ImageIO.read(new File(Game.getRes("water2.png")));
		this.image = image1;
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	public void update() {
		if (Game.game.ticks % 25 == 0) {
			if (image == image1) {
				image = image2;
			} else {
				image = image1;
			}
		}
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
