package me.inplex.evo.net;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import me.inplex.evo.Game;

public class Net {

	public static Socket socket;
	public static final int defaultPort = 1337;
	public static Set<OtherPlayer> otherPlayers = new HashSet<OtherPlayer>();

	public static void connectToServer() {
		try {
			System.out.println("Connecting to " + Game.serverIp + " with port " + defaultPort);
			socket = new Socket((Game.serverIp == null || Game.serverIp == "") ? "localhost" : Game.serverIp, defaultPort);
			System.out.println("Connected!");
			
			Thread receive = new ThreadReceive();
			Thread send = new ThreadSend();
			Thread message = new ThreadMessage();
			receive.start();
			send.start();
			message.start();
			
		} catch (Exception e) {
			System.out.println("Could not connect to Server");
		}
	}
}