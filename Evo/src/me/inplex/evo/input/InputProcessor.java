package me.inplex.evo.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Set;

import me.inplex.evo.Game;
import me.inplex.evo.graphics.Direction;

public class InputProcessor {

	private int MouseX;
	private int MouseY;

	public InputProcessor() {
		MouseX = 0;
		MouseY = 0;
	}

	public void mouseClicked(int x, int y) {

	}

	public void checkInput() throws IOException {
		Set<Integer> keys = Game.game.listener.getKeysDownInt();
		if (keys.contains(KeyEvent.VK_A)) {
			Game.game.player.move(Direction.LEFT);
		}
		if (keys.contains(KeyEvent.VK_D)) {
			Game.game.player.move(Direction.RIGHT);
		}
		if (keys.contains(KeyEvent.VK_W)) {
			Game.game.player.move(Direction.UP);
		}
		if (keys.contains(KeyEvent.VK_S)) {
			Game.game.player.move(Direction.DOWN);
		}
		if (keys.contains(KeyEvent.VK_SPACE)) {
			Game.game.player.shoot();
		}

		if (keys.contains(KeyEvent.VK_LEFT)) {
			Game.game.translation.x += 2;
		}
		if (keys.contains(KeyEvent.VK_RIGHT)) {
			Game.game.translation.x -= 2;
		}
		if (keys.contains(KeyEvent.VK_UP)) {
			Game.game.translation.y += 2;
		}
		if (keys.contains(KeyEvent.VK_DOWN)) {
			Game.game.translation.y -= 2;
		}

		if (keys.contains(KeyEvent.VK_ESCAPE)) {
			Game.game.stop();
		}

		Game.game.player.setMoving(keys.contains(KeyEvent.VK_W) || keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_S) || keys.contains(KeyEvent.VK_D));

		int xScreen = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int yScreen = (int) MouseInfo.getPointerInfo().getLocation().getY();

		MouseX = (int) (xScreen - Game.game.getLocationOnScreen().getX());
		MouseY = (int) (yScreen - Game.game.getLocationOnScreen().getY());

	}

	public int getMouseX() {
		return MouseX;
	}

	public int getMouseY() {
		return MouseY;
	}

	public Point getMousePoint() {
		return new Point(MouseX, MouseY);
	}
}
