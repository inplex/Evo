package me.inplex.evo.net;

import java.io.IOException;
import java.io.PrintWriter;

import me.inplex.evo.Game;

public class ThreadSend extends Thread {

	public void run() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(Net.socket.getOutputStream(), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				out.println(new PacketPosition(Net.socket.getInetAddress().getHostAddress(),Game.game.player.getX(),Game.game.player.getY(),Game.game.player.getDirection()).toString());
				sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}