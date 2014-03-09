package me.inplex.evo.entity;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;
import me.inplex.evo.extra.Util;
import me.inplex.evo.graphics.Direction;

public class Player implements RenderObject {

	private int x;
	private int y;
	private double speed = 2.5;
	private final static double normalSpeed = 2.5;
	private int health = 100;
	private boolean dead = false;
	private boolean dDead = false;
	private boolean moving = false;
	private Direction direction = Direction.LEFT;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image;

	private int kills;

	public Player(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		this.image1 = ImageIO.read(new File(Game.getRes("player1.png")));
		this.image2 = ImageIO.read(new File(Game.getRes("player2.png")));
		this.image = this.image1;
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

	public void move(Direction d) {
		if (!dead) {
			final boolean dOut = Util.playerOut();
			boolean _dHitTree = false;
			for (RenderObject r : Game.game.render.getMap()) {
				if ((r instanceof Tree || r instanceof Water) && r != null) {
					if (Util.collidesR(new Point(x, y), 20, new Point(r.getX(), r.getY()), 20)) {
						_dHitTree = true;
						break;
					} else {
						_dHitTree = false;
					}
				}
			}
			final boolean dHitTree = _dHitTree;
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

			boolean hitTree = false;
			boolean hitWater = false;
			for (RenderObject r : Game.game.render.getMap()) {
				if (r instanceof Tree) {
					if (Util.collidesR(new Point(x, y), 20, new Point(r.getX(), r.getY()), 20)) {
						hitTree = true;
						break;
					} else {
						hitTree = false;
					}
				}
				if (r instanceof Water) {
					if (Util.collidesR(new Point(x, y), 20, new Point(r.getX(), r.getY()), 20)) {
						hitWater = true;
						break;
					} else {
						hitWater = false;
					}
				}
				if (r instanceof Heart) {
					if (Util.collidesR(new Point(x, y), 20, new Point(r.getX(), r.getY()), 20)) {
						health += 10;
						Game.game.render.removeFromMap(r);
						r = null;
						break;
					}
				}
			}

			if ((!dHitTree && hitTree) || (!dOut && Util.playerOut())) {
				x = dX;
				y = dY;
			}

			if (hitWater) {
				speed = normalSpeed / 2;
			} else {
				speed = normalSpeed;
			}

			direction = d;
		}
	}

	public void update() {
		if (health <= 0) {
			dead = true;
			if (!dDead) {
				Game.saveScore(Game.name, kills);
			}
		}

		if (Game.game.ticks % 20 == 0 && !dead && moving) {
			if (image == image1) {
				image = image2;
			} else {
				image = image1;
			}
		}
		dDead = dead;
	}

	public void shoot() throws IOException {
		if (!dead)
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

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
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

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

}
