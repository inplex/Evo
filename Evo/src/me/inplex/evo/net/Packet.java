package me.inplex.evo.net;

public interface Packet {
	
	
		
		String data = null;
		
		String ip = null;
		int x = 0;
		int y = 0;
		
		public String toString();
		
		public String getIp();
		public int getX();
		public int getY();
		
	

	
}
