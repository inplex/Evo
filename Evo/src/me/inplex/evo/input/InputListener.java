package me.inplex.evo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import me.inplex.evo.Game;

public class InputListener implements KeyListener, MouseListener {

	public InputListener() {
	}

	public static Set<Character> keyDownChar = new HashSet<Character>();
	public static Set<Integer> keyDownInt = new HashSet<Integer>();

	public Set<Character> getKeysDownChar() {
		return keyDownChar;
	}
	public Set<Integer> getKeysDownInt() {
		return keyDownInt;
	}

	/*
	 * KEYBOARD
	 */

	public void keyPressed(KeyEvent e) {
		keyDownChar.add(e.getKeyChar());
		keyDownInt.add(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		keyDownChar.remove(e.getKeyChar());
		keyDownInt.remove(e.getKeyCode());
	}
	
	public void keyTyped(KeyEvent e) {

	}

	/*
	 * MOUSE
	 */

	public void mouseClicked(MouseEvent e) {
		Game.game.input.mouseClicked(e.getX(), e.getY());
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
