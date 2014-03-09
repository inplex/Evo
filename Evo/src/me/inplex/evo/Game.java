package me.inplex.evo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import me.inplex.evo.entity.Bullet;
import me.inplex.evo.entity.Grass;
import me.inplex.evo.entity.Player;
import me.inplex.evo.entity.Zombie;
import me.inplex.evo.extra.OS;
import me.inplex.evo.extra.Util;
import me.inplex.evo.graphics.Map;
import me.inplex.evo.graphics.RenderEngine;
import me.inplex.evo.input.InputListener;
import me.inplex.evo.input.InputProcessor;
import me.inplex.evo.net.Net;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static Game game;
	public static String title = "Evo";
	public int width = 800;
	public int height = 600;
	public int ticks;
	public static String name;
	public static String serverIp;
	
	public RenderEngine render;
	public InputListener listener;
	public InputProcessor input;
	public Map map;
	public Player player;

	public Point scale = new Point(3, 3);
	public Point translation;

	public Thread thread;
	public JFrame frame;
	private boolean running = false;

	public Game() throws IOException {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		listener = new InputListener();
		input = new InputProcessor();
		render = new RenderEngine();
		player = new Player(width / 2, height / 2);
		map = new Map(width / 10, height / 10);
		translation = new Point(-player.getX() + 100, -player.getY() + 100);
		render.addRenderObject(player);
	}

	public static void main(String[] args) {
		try {
			System.out.println("Starting ..");
			long time = System.nanoTime();
			LoadingScreen.create();
			if (args.length == 1) {
				name = args[0];
			} else if (args.length == 2 && args[1].contains(".")) {
				name = args[0];
				serverIp = args[1];
			} else {
				name = "Player";
			}
			if (serverIp == null)
				serverIp = "localhost";
			System.out.println("Detected OS: " + Util.getOS().toString().toLowerCase());
			game = new Game();
			game.frame.setResizable(false);
			game.frame.setUndecorated(true);
			game.frame.setTitle(title + " - Loading . .");
			game.frame.add(game);
			game.frame.pack();
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLocationRelativeTo(null);
			game.frame.setFocusable(true);
			game.frame.setBackground(new Color(new Grass(0, 0).getImage().getRGB(1, 1)));
			game.frame.setIconImage(ImageIO.read(new File(getRes("player1.png"))));
			game.addKeyListener(game.listener);
			game.addMouseListener(game.listener);
			game.requestFocus();
			game.map.loadMap();
			game.frame.setVisible(true);
			game.start();
			game.frame.setTitle(title);
			LoadingScreen.destroy();
			time = System.nanoTime() - time;
			System.out.println("Started! Took " + ((double) time / 1000000000) + " seconds.");
			System.out.println("Good Luck, " + name + " !");
			Net.connectToServer();
		} catch (Exception e) {
			e.printStackTrace();
			LoadingScreen.createErrorWindow(e);
		}
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {
			while (running) {
				ticks++;
				update();
				render();
				thread.sleep(1000/60);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoadingScreen.createErrorWindow(e);
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, title);
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		System.exit(0);
	}

	public void update() throws IOException {
		input.checkInput();
		Bullet.moveBullets();
		Zombie.spawnZombies();
		Zombie.moveZombies();
		if (player.isMoving())
			translation = new Point(-player.getX() + 100, -player.getY() + 100);
		
	}
	
	

	public void render() throws IOException {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics graphics = bs.getDrawGraphics();
		render.setGraphics(graphics);
		graphics.setColor(new Color(new Grass(0, 0).getImage().getRGB(1, 1)));
		graphics.fillRect(-1000, -1000, 2000, 2000);
		if (ticks >= 50) {
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics2d.scale(scale.x, scale.y);
			graphics2d.translate(translation.x, translation.y);
		}
		render.render();
		graphics.dispose();
		bs.show();
	}

	public static String getRes(String path) throws FileNotFoundException {
		if (new File(path).exists()) {
			return new File(path).getPath();
		} else if (new File("res/" + path).exists()) {
			return new File("res/" + path).getPath();
		} else if (new File(Game.class.getClass().getClassLoader().getResource(path).getPath()).exists()) {
			return Game.class.getClass().getClassLoader().getResource(path).getPath();
		} else if (new File(Game.class.getClass().getClassLoader().getResource("/res/" + path).getPath()).exists()) {
			return Game.class.getClass().getClassLoader().getResource("/res/" + path).getPath();
		} else if (new File(Game.class.getResource("/" + path).getPath()).exists()) {
			return Game.class.getResource("/" + path).getPath();
		} else if (new File(Game.class.getResource("/res/" + path).getPath()).exists()) {
			return Game.class.getResource("/res/" + path).getPath();
		} else {
			throw new FileNotFoundException(path);
		}
	}

	public static void saveScore(String name, int score) {
		if (Util.getOS() == OS.WINDOWS) {
			try {
				String path = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Desktop/Evo_Scores.txt";
				
				File f = new File(path);
				if (!f.exists()) {
					f.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
					bw.write("-- Evo Scores --");
					bw.newLine();
					bw.newLine();
					bw.flush();
					bw.close();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
				bw.write(name + " : " + score + " kills");
				bw.newLine();
				bw.flush();
				bw.close();
				System.out.println("Saved score to " + f.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Could not save Score: OS(" + Util.getOS().toString().toLowerCase() + ") not supported");
		}
	}

}