package me.inplex.evo.entity;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;
import me.inplex.evo.extra.Util;
import me.inplex.evo.graphics.Direction;

public class Zombie implements RenderObject {

	private int x;
	private int y;
	private double speed = 2;
	private int health;
	private static int spawnHealth = 50;
	private static double spawnRate = 0.001;
	private static double spawnRateSpeed = 0.0001;
	private static Set<Zombie> zombies = new HashSet<Zombie>();
	private Direction direction = Direction.LEFT;
	private BufferedImage image;

	public Zombie(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		this.health = spawnHealth;
		double rnd = Math.random();
		if (rnd > 0.5)
			this.image = ImageIO.read(new File(Game.getRes("zombie1.png")));
		else
			this.image = ImageIO.read(new File(Game.getRes("zombie2.png")));
	}

	public static void spawnZombies() throws IOException {
		Random ran = new Random();
		double r = 0 + (1 - 0) * ran.nextDouble();

		if (r < Zombie.spawnRate) {
			int rX = new Random().nextInt(Game.game.map.getWidth() * 20 - 1) + 1;
			int rY = new Random().nextInt(Game.game.map.getHeight() * 20 - 1) + 1;
			Zombie.zombies.add(new Zombie(rX, rY));
		}
		Zombie.spawnRate += Zombie.spawnRateSpeed;
	}

	public static void moveZombies() {
		try {

			for (Zombie z : zombies) {
				if (z.health <= 0) {
					zombies.remove(z);
					z = null;
					Game.game.player.setKills(Game.game.player.getKills()+1);
				}
			}

			if (Game.game.ticks % 100 == 0) {
				spawnHealth += 2;
			}

			for (Bullet b : Bullet.bullets) {
				for (Zombie z : zombies) {
					if (Util.collidesR(new Point(z.getX(), z.getY()), 20, new Point(b.getX(), b.getY()), 10)) {
						z.health--;
						z.move(b.getDirection());
					}
				}
			}

			for (Zombie z : zombies) {
				if (z.getX() > Game.game.player.getX())
					z.move(Direction.LEFT);
				if (z.getX() < Game.game.player.getX())
					z.move(Direction.RIGHT);
				if (z.getY() < Game.game.player.getY())
					z.move(Direction.DOWN);
				if (z.getY() > Game.game.player.getY())
					z.move(Direction.UP);
			}

			for (Zombie z : zombies) {
				if (Util.collidesR(new Point(z.getX(), z.getY()), 20, new Point(Game.game.player.getX(), Game.game.player.getY()), 20)) {
					Game.game.player.setHealth(Game.game.player.getHealth()-1);
				}
			}

		} catch (ConcurrentModificationException e) {
		}

	}

	public void render(Graphics g) {
		switch (direction) {
		case LEFT:
			g.drawImage(Util.horizontalFlip(image), x, y, null);
			break;
		case RIGHT:
			g.drawImage(image, x, y, null);
			break;
		case UP:
			g.drawImage(Util.verticalFlip(Util.rotateImage(image, 90)), x, y, null);
			break;
		case DOWN:
			g.drawImage(Util.rotateImage(image, 90), x, y, null);
			break;
		}
	}

	public void update() {
	}

	public void move(Direction d) {
		final boolean dOut = Util.playerOut();
		final int dX = x;
		final int dY = y;
		switch (d) {
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
		if (!dOut && Util.playerOut()) {
			x = dX;
			y = dY;
		}
		direction = d;
	}

	public void shoot() throws IOException {
		Bullet.bullets.add(new Bullet(x, y, direction));
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public static Set<Zombie> getZombies() {
		return zombies;
	}

	public static void setZombies(Set<Zombie> zombies) {
		Zombie.zombies = zombies;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
