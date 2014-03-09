package me.inplex.evo.entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;
import me.inplex.evo.extra.Util;
import me.inplex.evo.graphics.Direction;

public class Bullet implements RenderObject {

	public static Set<Bullet> bullets = new HashSet<Bullet>();

	private int x;
	private int y;
	private Direction direction;
	private double speed = 3;
	private BufferedImage image;

	public Bullet(int x, int y, Direction direction) throws IOException {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.image = ImageIO.read(new File(Game.getRes("bullet.png")));
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public void update() {
	}

	public void move() {
		switch (direction) {
		case LEFT:
			x -= speed;
			break;
		case RIGHT:
			x += speed;
			break;
		case UP:
			y -= speed;
			break;
		case DOWN:
			y += speed;
			break;
		}
	}

	public static void moveBullets() {
		try {
			for (Bullet b : bullets) {
				b.move();
				b.speed += 0.01;
				if (!Util.collidesR(new Point(b.x, b.y), 10, 10, new Point(0, 0), Game.game.map.getWidth() * 20, Game.game.map.getHeight() * 20)) {
					bullets.remove(b);
					b = null;
				}
			}
		} catch (ConcurrentModificationException e) {

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

	public static Set<Bullet> getBullets() {
		return bullets;
	}

	public static void setBullets(Set<Bullet> bullets) {
		Bullet.bullets = bullets;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
