package me.inplex.evo.extra;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import me.inplex.evo.Game;

public class Util {

	public static boolean playerOut() {

		Rectangle w = new Rectangle(0, 0, Game.game.map.getWidth() * 20, Game.game.map.getHeight() * 20);
		Rectangle[] p = { new Rectangle(Game.game.player.getX() + 20, Game.game.player.getY() + 20, 20, 20), new Rectangle(Game.game.player.getX() - 20, Game.game.player.getY() - 20, 20, 20), new Rectangle(Game.game.player.getX() + 20, Game.game.player.getY() - 20, 20, 20), new Rectangle(Game.game.player.getX() - 20, Game.game.player.getY() + 20, 20, 20) };

		for (int i = 0; i < p.length; i++) {
			if (!p[i].intersects(w)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isInMap(Point p) {

		Rectangle r1 = new Rectangle(p.x, p.y, 1, 1);
		Rectangle r2 = new Rectangle(0, 0, Game.game.map.getWidth() * 20, Game.game.map.getHeight() * 20);

		if (r1.intersects(r2)) {
			return true;
		}

		return false;
	}

	public static boolean collidesR(Point obj1, int obj1_width, Point obj2, int obj2_width) {
		Rectangle r1 = new Rectangle(obj1.x, obj1.y, obj1_width, obj1_width);
		Rectangle r2 = new Rectangle(obj2.x, obj2.y, obj2_width, obj2_width);

		if (r1.intersects(r2)) {
			return true;
		}

		return false;
	}

	public static boolean collidesR(Point obj1, int obj1_width, int obj1_height, Point obj2, int obj2_width, int obj2_height) {
		Rectangle r1 = new Rectangle(obj1.x, obj1.y, obj1_width, obj1_height);
		Rectangle r2 = new Rectangle(obj2.x, obj2.y, obj2_width, obj2_height);

		if (r1.intersects(r2)) {
			return true;
		}

		return false;
	}
	
	public static boolean collides(Point obj1, int obj1_width, int obj1_height, Point obj2, int obj2_width, int obj2_height) {
		boolean ret = false;
		if (!(obj1.x < obj2.x || obj1.x > obj2.x + obj2_width || obj1.y < obj2.y || obj1.y > obj2.y + obj2_height)) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public static BufferedImage horizontalFlip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	public static BufferedImage verticalFlip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return dimg;
	}

	public static BufferedImage rotateImage(BufferedImage src, double degrees) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance(Math.toRadians(degrees), src.getWidth() / 2, src.getHeight() / 2);
		BufferedImage rotatedImage = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(src, 0, 0, null);
		return rotatedImage;
	}
	
	public static String getStackTrace(Throwable t) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    t.printStackTrace(printWriter);
	    return result.toString();
	}
	
	public static OS getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0)
			return OS.WINDOWS;
		if (os.indexOf("mac") >= 0)
			return OS.MAC;
		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0)
			return OS.WINDOWS;
		if (os.indexOf("sunos") >= 0)
			return OS.SOLARIS;
		return OS.UNKNOWN;
	}

}
