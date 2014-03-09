package me.inplex.evo.net;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.inplex.evo.Game;
import me.inplex.evo.entity.RenderObject;
import me.inplex.evo.extra.Util;
import me.inplex.evo.graphics.Direction;

public class OtherPlayer implements RenderObject {

	private int x;
	private int y;
	private Direction direction;
	private String ip;
	private BufferedImage image;

	public OtherPlayer(int x, int y, Direction dir, String ip) throws IOException {
		this.x = x;
		this.y = y;
		this.ip = ip;
		this.direction = dir;
		this.image = ImageIO.read(new File(Game.getRes("player1.png")));
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
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction dir) {
		this.direction = dir;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void update() {
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
