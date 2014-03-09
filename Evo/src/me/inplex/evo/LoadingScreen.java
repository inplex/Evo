package me.inplex.evo;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import me.inplex.evo.extra.Util;

public class LoadingScreen {

	private static JFrame f;
	private static JLabel l;
	private static int width = 200;
	private static int height = 100;

	private static JFrame fErr;
	private static JTextArea lErr;

	public static void create() {
		try {
			f = new JFrame();
			f.setTitle(Game.title);
			f.setSize(width, height);
			f.setResizable(false);
			f.setLocationRelativeTo(null);
			f.setFocusable(true);
			f.setUndecorated(true);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setAlwaysOnTop(true);
			f.setBackground(Color.YELLOW);
			l = new JLabel();
			l.setIcon(new ImageIcon(Game.getRes("loading.png")));
			f.add(l);
			f.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			f.dispose();
		}
	}

	public static void destroy() {
		f.dispose();
	}

	public static void createErrorWindow(Exception e) {
		try {
			fErr = new JFrame(Game.title + " - ERROR");
			fErr.setTitle(Game.title);
			fErr.setSize(width, height);
			fErr.setResizable(true);
			fErr.setLocationRelativeTo(null);
			fErr.setFocusable(true);
			fErr.setUndecorated(false);
			fErr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fErr.setAlwaysOnTop(true);
			lErr = new JTextArea();
			lErr.setEditable(false);
			lErr.append("-- ERROR --\n");
			lErr.append(Util.getStackTrace(e));
			fErr.add(lErr);
			fErr.setVisible(true);
			fErr.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Game.game.stop();
				}
			});
		} catch (Exception ex) {
			System.out.println("FATAL ERROR!");
			fErr.dispose();
			Game.game.stop();
		}
	}

}