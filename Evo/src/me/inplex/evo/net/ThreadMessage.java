package me.inplex.evo.net;

public class ThreadMessage extends Thread {

	public void run() {
		while (true) {
			try {
				System.out.println("-- Message --");
				System.out.println("Connections: " + Net.otherPlayers.size());
				for (OtherPlayer c : Net.otherPlayers) {
					System.out.println("> IP: " + c.getIp() + " | X: " + c.getX() + " | Y: " + c.getX());
				}
				System.out.println("-------------");
				sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
