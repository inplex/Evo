package me.inplex.evo.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

public class ThreadReceive extends Thread {

	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(Net.socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				String msg = in.readLine();
				if (msg != null) {
					PacketPosition p = new PacketPosition(msg);
					OtherPlayer pl = null;
					for (OtherPlayer o : Net.otherPlayers) {
						if (p.getIp().equals(o.getIp())) {
							pl = o;
							break;
						}
					}
					if (pl == null) {
						Net.otherPlayers.add(new OtherPlayer(p.getX(), p.getY(),p.getDirection(), p.getIp()));
					} else {
						pl.setX(p.getX());
						pl.setY(p.getY());
						pl.setDirection(p.getDirection());
					}
				}
				sleep(1);
			} catch (SocketException se) {
				System.out.println("Server down!");
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
